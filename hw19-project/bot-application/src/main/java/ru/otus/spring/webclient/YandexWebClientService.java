package ru.otus.spring.webclient;

import ru.otus.spring.dto.YandexItemRsDto;
import ru.otus.spring.dto.YandexRsDto;

import java.time.LocalDate;

public interface YandexWebClientService {

    YandexRsDto call();

    YandexRsDto call(LocalDate period);

    YandexItemRsDto call(String itemId);
}
