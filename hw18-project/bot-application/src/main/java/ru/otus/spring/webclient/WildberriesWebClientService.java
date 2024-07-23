package ru.otus.spring.webclient;

import ru.otus.spring.dto.WildberriesRsDto;

import java.time.LocalDate;
import java.util.List;

public interface WildberriesWebClientService {
    public List<WildberriesRsDto> call();

    public List<WildberriesRsDto> call(LocalDate period);


}
