package ru.otus.spring.bot.impl;

import ru.otus.spring.bot.TelegramBot;
import ru.otus.spring.configuration.BotConfiguration;
import ru.otus.spring.service.OzonService;
import ru.otus.spring.service.WildberriesService;
import ru.otus.spring.service.YandexService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.LocalDate;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {

    private final BotConfiguration botConfiguration;

    private final WildberriesService wildberriesService;

    private final YandexService yandexService;

    private final OzonService ozonService;

    private final ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(100);

    private final CopyOnWriteArrayList<Long> chatList = new CopyOnWriteArrayList<>();

    private static final int MAX_INCOME_COUNT_PERIOD = 30;

    private static final String COUNTING_ERROR_MESSAGE = "Слишком частые вызовы Wildberries Api перегружают систему." +
            " Разрешено делать не более одного запроса в минуту. " +
            "Учтите внутренние запросы для получения информации о заказах. " +
            "Запрос заработка больше 25 дней может не обрабатываться если сервер перегружен. Error: ";


    @PostConstruct
    private void initExecutorService() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Thread(() -> {
            Thread.currentThread().setName("Message processor thread");
            log.info("Registered thread: {}", Thread.currentThread().getName());
            while (true) {
                try {
                    log.info("Sending info with thread: {} to chats: {}", Thread.currentThread().getName(), chatList);
                    String message = messageQueue.take();
                    chatList.forEach(chat -> sendMessage(chat, message));
                } catch (Exception e) {
                    log.error("ERROR: {}", e.getMessage());
                }
            }
        }));
    }

    @PostConstruct
    private void initWildberriesProcessing() {
        wildberriesService.initWildberriesProcessing(messageQueue);
    }

    @PostConstruct
    private void initYandexProcessing() {
        yandexService.initYandexProcessing(messageQueue);
    }

    @PostConstruct
    private void initOzonProcessing() {
        ozonService.initOzonProcessing(messageQueue);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String userMessageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (userMessageText.equals("/start")) {

                if (!chatList.contains(chatId)) {
                    chatList.add(chatId);
                }

                sendMessage(chatId, "Вы зарегистрировали акаунт для получения уведомлений о заказах.");

            } else if (userMessageText.equals("/stop")) {
                sendMessage(chatId, "Вы отключиили аккаунт от получения уведомлений о заказах.");
                chatList.remove(chatId);
            } else if (userMessageText.matches("/w [1-9][0-9]?")) {

                long days = parseDays(userMessageText);

                if (days > MAX_INCOME_COUNT_PERIOD) {
                    sendMessage(chatId, "Слишком большое количество дней.");
                } else {
                    var period = LocalDate.now().minusDays(days);
                    try {
                        sendMessage(chatId, String.valueOf(wildberriesService.countPeriodIncome(period)));
                    } catch (Exception e) {
                        sendMessage(chatId, COUNTING_ERROR_MESSAGE + e.getCause());
                    }
                }
            } else if (userMessageText.matches("/y [1-9][0-9]?")) {

                long days = parseDays(userMessageText);

                if (days > MAX_INCOME_COUNT_PERIOD) {
                    sendMessage(chatId, "Слишком большое количество дней.");
                } else {
                    var period = LocalDate.now().minusDays(days);
                    try {
                        sendMessage(chatId, String.valueOf(yandexService.countPeriodIncome(period)));
                    } catch (Exception e) {
                        sendMessage(chatId, COUNTING_ERROR_MESSAGE + e.getCause());
                    }
                }
            } else if (userMessageText.matches("/o [1-9][0-9]?")) {

                long days = parseDays(userMessageText);

                if (days > MAX_INCOME_COUNT_PERIOD) {
                    sendMessage(chatId, "Слишком большое количество дней.");
                } else {
                    var period = LocalDate.now().minusDays(days);
                    try {
                        sendMessage(chatId, String.valueOf(ozonService.countPeriodIncome(period)));
                    } catch (Exception e) {
                        sendMessage(chatId, COUNTING_ERROR_MESSAGE + e.getCause());
                    }
                }
            } else if (userMessageText.equals("/help")) {
                sendMessage(chatId, getCommands());
            } else if (userMessageText.equals("/metrics")) {
                sendMessage(chatId, getMetrics());
            } else {
                sendMessage(chatId, "Неизвестная команда.");
            }
        }
    }

    private long parseDays(String line) {
        return Long.parseLong(line.substring(line.lastIndexOf(" ") + 1));
    }

    private synchronized void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setParseMode("Markdown");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("ERROR: {}", e.getMessage());
        }
    }

    @Override
    public void addMessage(String message) {
        messageQueue.add(message);
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getToken();
    }

    private String getCommands() {
        return "Доступные команды:\n\n" +
                "/start - начать получать уведомления о заказах" + "\n" +
                "/stop - перестать получать уведомления о заказах" + "\n" +
                "/y 'число' - получить общую сумму заказов на YandexMarket за определенное кол-во дней (пример: /y 10, английская раскладка)" + "\n" +
                "/w 'число' - получить общую сумму заказов на Wildberries за определенное кол-во дней (пример: /w 10, английская раскладка)" + "\n" +
                "/o 'число' - получить общую сумму заказов на Ozon за определенное кол-во дней (пример: /o 10, английская раскладка)" + "\n" +
                "/metrics - получить информацию о состоянии сервера";
    }

    private String getMetrics() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        double memoryUsagePercentage = (double) usedMemory / totalMemory * 100;

        var runtimeMxBean = ManagementFactory.getMemoryMXBean();
        var heap = runtimeMxBean.getHeapMemoryUsage();
        long heapMax = heap.getMax();
        long heapUsed = heap.getUsed();
        double heapUsagePercentage = (double) heapUsed / heapMax * 100;

        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadMxBean.getThreadCount();
        int peakThreadCount = threadMxBean.getPeakThreadCount();
        int daemonThreadCount = threadMxBean.getDaemonThreadCount();

        return "Информация о системе:\n" +
                "---------------------------------------------------------\n" +
                "Занятая память: " + usedMemory / 1024 / 1024 + " МБ (" + memoryUsagePercentage + "%)\n" +
                "Heap:\n" +
                "  -  Занято: " + heapUsed / 1024 / 1024 + " МБ (" + heapUsagePercentage + "%)\n" +
                "  -  Доступно: " + heapMax / 1024 / 1024 + " МБ\n" +
                "  -  Максимум: " + heapMax / 1024 / 1024 + " МБ\n" +
                "Оперативная память: \n" +
                "  -  Занято: " + usedMemory / 1024 / 1024 + " МБ (" + memoryUsagePercentage + "%)\n" +
                "  -  Свободно: " + freeMemory / 1024 / 1024 + " МБ\n" +
                "  -  Всего: " + totalMemory / 1024 / 1024 + " МБ\n" +
                "Потоки: \n" +
                "  -  Активные потоки: " + threadCount + "\n" +
                "  -  Пиковое количество потоков: " + peakThreadCount + "\n" +
                "  -  Daemon-потоков: " + daemonThreadCount + "\n" +
                "Внутренняя информация: \n" +
                "  -  Очередь на вывод пуста: " + messageQueue.isEmpty() + "\n" +
                "  -  Зарегистрировано чатов: " + chatList.size() + "\n" +
                "  -  В буфере Yandex: " + yandexService.getBufferCount() + "\n" +
                "  -  В буфере Ozon: " + ozonService.getBufferCount() + "\n" +
                "  -  В буфере Wildberries: " + wildberriesService.getBufferCount();
    }
}
