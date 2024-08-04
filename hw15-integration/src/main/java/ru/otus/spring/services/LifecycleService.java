package ru.otus.spring.services;

import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Worm;

public interface LifecycleService {

	Butterfly transform(Worm worm);
}
