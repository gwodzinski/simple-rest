package com.wodzinski.simplerest;

import com.wodzinski.simplerest.dto.NewQuoteDTO;
import com.wodzinski.simplerest.dto.QuoteDTO;
import com.wodzinski.simplerest.model.AuthorEntity;
import com.wodzinski.simplerest.model.QuoteEntity;
import com.wodzinski.simplerest.repository.AuthorRepository;
import com.wodzinski.simplerest.repository.QuoteRepository;
import com.wodzinski.simplerest.service.IQuoteService;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class RestAppTests {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    IQuoteService quoteService;

    @BeforeEach
    public void setUp() {
        quoteRepository.deleteAll();
        authorRepository.deleteAll();

        createTestData();
    }

    private void createTestData() {
        AuthorEntity author = AuthorEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        authorRepository.save(author);

        QuoteEntity quoteEntity = QuoteEntity.builder()
                .content("_some text_")
                .author(author)
                .build();
        quoteRepository.save(quoteEntity);

        QuoteEntity quoteEntity2 = QuoteEntity.builder()
                .content("_another text_")
                .author(author)
                .build();
        quoteRepository.save(quoteEntity2);
    }

    @AfterEach
    public void cleanup() {
        quoteRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnAllQuotes() {
        List<QuoteDTO> quoteEntityList = quoteService.getAllQuotes();
        assertNotNull(quoteEntityList);
        assertSame(2, quoteEntityList.size());
    }

    @Test
    void shouldSaveNewQuote() {
        assertEquals(2, quoteService.getAllQuotes().size());

        quoteService.saveQuote(createQuoteDTO());

        assertEquals(3, quoteService.getAllQuotes().size());

    }

    @Test
    void shouldChangeQuote() {
        NewQuoteDTO newQuoteDTO = createQuoteDTO();
        QuoteEntity quoteEntity = quoteService.saveQuote(newQuoteDTO);
        String content = quoteRepository.findById(quoteEntity.getId()).get().getContent();

        newQuoteDTO.setQuote("new quote: lorum ipsum");
        quoteEntity = quoteService.saveQuote(quoteEntity.getId(), newQuoteDTO);

        assertNotEquals(content, quoteEntity.getContent());
    }

    @Test
    void shouldSaveQuoteContent() {
        QuoteEntity quoteEntity = quoteService.saveQuote(createQuoteDTO());
        String content = quoteRepository.findById(quoteEntity.getId()).get().getContent();

        quoteEntity = quoteService.updateQuoteContent(quoteEntity.getId(), "new quote: lorum ipsum");

        assertNotEquals(content, quoteEntity.getContent());
    }

    @Test
    void shouldReturnQuoteDTO() {
        assertNotEquals(0, quoteRepository.findAll().size());
        long id = quoteRepository.findAll().get(0).getId();

        Optional<QuoteDTO> quoteDTO = quoteService.getQuote(id);
        assertTrue(quoteDTO.isPresent());
    }

    @Test
    void shouldDeleteEntity() throws NotFoundException {
        assertEquals(2, quoteRepository.findAll().size());
        long id = quoteRepository.findAll().get(0).getId();

        quoteService.deleteQuote(id);
        assertEquals(1, quoteRepository.findAll().size());
    }

    @Test
    void shouldReturnErrorWhenQuoteIsNotExist() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            quoteService.deleteQuote(100L);
        });

        String expectedMessage = "Quote not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private NewQuoteDTO createQuoteDTO() {
        return NewQuoteDTO.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .quote("lorem ipsum")
                .build();
    }

}
