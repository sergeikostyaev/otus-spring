package ru.otus.spring.webclient.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.spring.configuration.WildberriesClientConfiguration;
import ru.otus.spring.dto.WildberriesRsDto;
import ru.otus.spring.webclient.WildberriesWebClientService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesWebClient implements WildberriesWebClientService {

    private final WildberriesClientConfiguration wildberriesClientConfiguration;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String FLAG = "&flag=1";

    @Override
    public List<WildberriesRsDto> call() {

        WebClient webClient = WebClient.builder().build();

        String uri = wildberriesClientConfiguration.getUri() + LocalDate.now() + FLAG;

        WildberriesRsDto[] result = null;

        try {
            log.info("Wildberries client call with endpoint: {}", uri);

            result = webClient.get()
                    .uri(uri)
                    .header(AUTHORIZATION_HEADER, wildberriesClientConfiguration.getToken())
                    .retrieve()
                    .bodyToMono(WildberriesRsDto[].class)
                    .block();

        } catch (Exception e) {
            log.error("Wildberries client call error: {}", e.getMessage() + " " + e.getCause());
        }
        log.info("Wildberries client call success");

        return Objects.isNull(result) ? null : Arrays.stream(result).toList();
    }

    @Override
    public List<WildberriesRsDto> call(LocalDate period) {

        WebClient webClient = WebClient.builder().build();

        String uri = wildberriesClientConfiguration.getUri() + period;

        WildberriesRsDto[] result = null;

        try {
            log.info("Wildberries client call with endpoint: {}", uri);

            result = webClient.get()
                    .uri(uri)
                    .header(AUTHORIZATION_HEADER, wildberriesClientConfiguration.getToken())
                    .retrieve()
                    .bodyToMono(WildberriesRsDto[].class)
                    .block();

        } catch (Exception e) {
            log.error("Wildberries client call error: {}", e.getMessage() + " " + e.getCause());
        }
        log.info("Wildberries client call success");

        return Objects.isNull(result) ? null : Arrays.stream(result).toList();
    }

}
