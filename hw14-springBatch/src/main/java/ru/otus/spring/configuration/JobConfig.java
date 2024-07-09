package ru.otus.spring.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.mongo.Author;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.Genre;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 2;
    public static final String IMPORT_USER_JOB_NAME = "bookMigrationJob";

    private final MongoTemplate mongoTemplate;

    private final EntityManagerFactory entityManagerFactory;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @StepScope
    @Bean
    public JpaPagingItemReader<Book> reader() {
        JpaPagingItemReader<Book> reader = new JpaPagingItemReader<>();

        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT b FROM Book b");
        reader.setName("bookH2Reader");

        return reader;
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookMongo> processor() {
        return book -> {
            log.info("Book is migrated: {}", book);
            return BookMongo.builder().name(book.getName()).author(Author.builder().name(book.getAuthor().getName()).build())
                    .genre(Genre.builder().name(book.getGenre().getName()).build()).build();
        };
    }

    @StepScope
    @Bean
    public MongoItemWriter<BookMongo> writer() {
        MongoItemWriter<BookMongo> writer = new MongoItemWriter<>();

        writer.setTemplate(mongoTemplate);
        writer.setCollection("books");

        return writer;
    }


    @Bean
    public Job importUserJob(Step transformPersonsStep) {
        return new JobBuilder(IMPORT_USER_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformPersonsStep)
                .end()
                .build();
    }

    @Bean
    public Step migrateBooks(ItemReader<Book> reader, MongoItemWriter<BookMongo> writer,
                             ItemProcessor<Book, BookMongo> itemProcessor) {
        return new StepBuilder("transformPersonsStep", jobRepository)
                .<Book, BookMongo>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}
