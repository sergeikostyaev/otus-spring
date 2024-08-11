package ru.otus.spring.webclient;

import ru.otus.spring.dto.OzonRsDto;

import java.time.LocalDate;

public interface OzonWebClientService {

    OzonRsDto call(LocalDate period);

}
