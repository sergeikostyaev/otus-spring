package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Worm;
import ru.otus.spring.services.LifecycleGateway;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButterflyService implements ru.otus.spring.services.ButterflyService {
    private static final List<String> COLOURS = List.of("Blue", "Red", "Yellow");

    private final LifecycleGateway gateway;

    @Override
    public void start() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        pool.execute(() -> {
            Collection<Worm> worms = COLOURS.stream().map(Worm::new).collect(Collectors.toList());
            log.info("Worms with colours {} are going to become butterflies", worms.stream().map(Worm::getItemName).collect(Collectors.joining(",")));
            Collection<Butterfly> butterflies = gateway.process(worms);
            log.info("Worms with colours {} became butterflies", butterflies.stream().map(Butterfly::getName).collect(Collectors.joining(", ")));
        });
    }
}
