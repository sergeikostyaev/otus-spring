package ru.otus.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.LibraryService;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FunctionalEndpointsConfig {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(LibraryService libraryService) {
        return route()
                .POST("api/func/books",
                        request -> request.bodyToMono(BookDto.class)
                                .doOnNext(libraryService::saveBook).then(ok().build())).build();
    }


}

