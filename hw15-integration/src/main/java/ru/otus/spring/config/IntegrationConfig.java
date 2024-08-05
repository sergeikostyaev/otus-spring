package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.services.LifecycleService;

@Configuration
public class IntegrationConfig {

	@Bean
	public MessageChannelSpec<?, ?> wormChannel() {
		return MessageChannels.queue(5);
	}

	@Bean
	public MessageChannelSpec<?, ?> butterflyChannel() {
		return MessageChannels.publishSubscribe();
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerSpec poller() {
		return Pollers.fixedRate(10).maxMessagesPerPoll(1);
	}

	@Bean
	public IntegrationFlow cafeFlow(LifecycleService lifecycleService) {
		return IntegrationFlow.from(wormChannel())
				.split()
				.handle(lifecycleService, "transform")
				.<Butterfly, Butterfly>transform(f -> new Butterfly(f.getName()))
				.aggregate()
				.channel(butterflyChannel())
				.get();
	}
}
