package com.wodzinski.simplerest.service;

import com.wodzinski.simplerest.dto.NewQuoteDTO;
import com.wodzinski.simplerest.dto.QuoteDTO;
import com.wodzinski.simplerest.model.QuoteEntity;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface IQuoteService {
    QuoteEntity saveQuote(NewQuoteDTO newQuoteDTO);

    QuoteEntity saveQuote(Long id, NewQuoteDTO newQuoteDTO);

    void deleteQuote(Long id) throws NotFoundException;

    QuoteEntity updateQuoteContent(Long id, String content);

    Optional<QuoteDTO> getQuote(Long id);

    List<QuoteDTO> getAllQuotes();
}
