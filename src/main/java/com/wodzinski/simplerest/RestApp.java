package com.wodzinski.simplerest;

import com.wodzinski.simplerest.repository.AuthorRepository;
import com.wodzinski.simplerest.repository.QuoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@Slf4j
public class RestApp {

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    AuthorRepository authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestApp.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showInfo() throws URISyntaxException {
        log.info("\n\n" + new URI("http://localhost:8080/api/quotes"));
    }

}
