package com.wodzinski.simplerest.service.impl;

import com.wodzinski.simplerest.dto.NewQuoteDTO;
import com.wodzinski.simplerest.dto.QuoteDTO;
import com.wodzinski.simplerest.mapper.QuoteMapper;
import com.wodzinski.simplerest.model.AuthorEntity;
import com.wodzinski.simplerest.model.QuoteEntity;
import com.wodzinski.simplerest.repository.AuthorRepository;
import com.wodzinski.simplerest.repository.QuoteRepository;
import com.wodzinski.simplerest.service.IQuoteService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteService implements IQuoteService {

    private final QuoteRepository quoteRepo;
    private final AuthorRepository authorRepo;

    public QuoteService(QuoteRepository quoteRepo, AuthorRepository authorRepo) {
        this.quoteRepo = quoteRepo;
        this.authorRepo = authorRepo;
    }

    @Override
    @Transactional
    public QuoteEntity saveQuote(NewQuoteDTO newQuoteDTO) {
        QuoteEntity quoteEntity = QuoteMapper.mapQuoteDTOToQuoteEntity(newQuoteDTO);
        quoteEntity.setAuthor(saveAuthor(newQuoteDTO));

        return quoteRepo.save(quoteEntity);
    }

    @Override
    public QuoteEntity saveQuote(Long id, NewQuoteDTO newQuoteDTO) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setContent(newQuoteDTO.getQuote());
        quoteEntity.setId(id);
        quoteEntity.setAuthor(saveAuthor(newQuoteDTO));

        return quoteRepo.save(quoteEntity);
    }

    private AuthorEntity saveAuthor(NewQuoteDTO newQuoteDTO) {
        Optional<AuthorEntity> authorEnt = authorRepo.findByFirstNameAndLastName(newQuoteDTO.getFirstName(), newQuoteDTO.getLastName());
        AuthorEntity author = authorEnt.orElseGet(() -> new AuthorEntity(null, newQuoteDTO.getFirstName(), newQuoteDTO.getLastName()));
        return authorRepo.save(author);
    }

    @Override
    @Transactional
    public void deleteQuote(Long id) throws NotFoundException {
        quoteRepo.delete(getQuoteEntityById(id).orElseThrow(() -> new NotFoundException("Quote not found")));
    }

    @Override
    @Transactional
    public QuoteEntity updateQuoteContent(Long id, String content) {
        QuoteEntity quoteEntity = quoteRepo.getById(id);
        quoteEntity.setContent(content);
        return quoteRepo.save(quoteEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteDTO> getQuote(Long id) {
        QuoteEntity quoteEntity = getQuoteEntityById(id).orElse(null);
        if (quoteEntity == null)
            return Optional.ofNullable(null);

        return Optional.of(QuoteMapper.mapQuoteEntityToQuoteDTO(quoteEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDTO> getAllQuotes() {
        return quoteRepo.findAll()
                .stream()
                .map(QuoteMapper::mapQuoteEntityToQuoteDTO)
                .collect(Collectors.toList());
    }

    private Optional<QuoteEntity> getQuoteEntityById(Long id) {
        return quoteRepo.findById(id);
    }
}
