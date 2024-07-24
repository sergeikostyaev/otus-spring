package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.bot.TelegramBot;
import ru.otus.spring.dto.WebsiteRqDto;
import ru.otus.spring.service.WebsiteService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class WebsiteServiceImpl implements WebsiteService {

    private final TelegramBot telegramBot;


    @Override
    public void addMessage(WebsiteRqDto request) {
        telegramBot.addMessage(mapWebsiteRequest(request));
    }

    private String mapWebsiteRequest(WebsiteRqDto request) {
        String date = LocalDate.ofInstant(Instant.ofEpochSecond(Long.parseLong(request.getDate())), TimeZone
                .getTimeZone("Europe/Moscow").toZoneId()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String time = LocalTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(request.getDate())), TimeZone
                .getTimeZone("Europe/Moscow").toZoneId()).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        StringBuilder result = new StringBuilder("Заказ на \"Сверчок\" от " + date + "\n");
        result.append("Время: " + time + "\n\n");

        String link = "https://sver4ok.ru/";

        result.append("Товар: ");

        request.getOrders().getCart().getCart().entrySet().forEach(entry -> {
            result.append("[" + entry.getValue().getName() + "](" + link + entry.getValue().getId() + ")" +
                    " (Цена: " + entry.getValue().getPrice() + "; Количество: " + entry.getValue().getNum() + "; Артикул: " + entry.getValue().getUid() + ")");
            result.append(", ");
        });

        result.replace(result.length() - 2, result.length(), "");
        result.append("\n\n");
        result.append("Адрес: " + request.getStreet());
        result.append("\n\n");

        result.append("Конечная цена: " + request.getSum());


        return result.toString();
    }
}
