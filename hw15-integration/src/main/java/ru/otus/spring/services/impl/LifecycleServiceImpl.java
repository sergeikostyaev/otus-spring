package ru.otus.spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Worm;
import ru.otus.spring.services.LifecycleService;

@Service
@Slf4j
public class LifecycleServiceImpl implements LifecycleService {

	@Override
	public Butterfly transform(Worm worm) {
		log.info("Worm with colour {} started transforming", worm.getItemName());
		delay();
		log.info("Worm with colour {} transformed into a butterfly", worm.getItemName());
		return new Butterfly(worm.getItemName());
	}

	private static void delay() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
