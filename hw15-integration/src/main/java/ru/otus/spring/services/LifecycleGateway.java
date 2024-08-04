package ru.otus.spring.services;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Worm;

import java.util.Collection;

@MessagingGateway
public interface LifecycleGateway {

	@Gateway(requestChannel = "wormChannel", replyChannel = "butterflyChannel")
	Collection<Butterfly> process(Collection<Worm> worm);
}
