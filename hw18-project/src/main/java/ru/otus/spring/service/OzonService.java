package ru.otus.spring.service;

import java.time.LocalDate;
import java.util.concurrent.ArrayBlockingQueue;

public interface OzonService {

    void initOzonProcessing(ArrayBlockingQueue<String> messageQueue);

    Integer countPeriodIncome(LocalDate period);

    Integer getBufferCount();
}
