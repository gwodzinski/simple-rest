package com.wodzinski.simplerest.controller;

import com.wodzinski.simplerest.dto.NewQuoteDTO;
import com.wodzinski.simplerest.dto.QuoteDTO;
import com.wodzinski.simplerest.mapper.QuoteMapper;
import com.wodzinski.simplerest.service.IQuoteService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class QuoteController {

    private final IQuoteService quoteService;

    public QuoteController(IQuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(value = "/api/quotes")
    @ResponseBody
    public ResponseEntity<List<QuoteDTO>> getQuotes() {
        return ResponseEntity.ok(quoteService.getAllQuotes());
    }

    @GetMapping(value = "/api/quotes/{id}")
    @ResponseBody
    public ResponseEntity getQuote(@Valid @PathVariable Long id) {
        Optional<QuoteDTO> quoteResponse = quoteService.getQuote(id);
        if (!quoteResponse.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested quote not found");

        return ResponseEntity.ok(quoteResponse.get());
    }

    @PostMapping(value = "/api/quotes/")
    @ResponseBody
    public ResponseEntity saveQuote(@Valid @RequestBody NewQuoteDTO newQuoteDTO) {
        return ResponseEntity.ok(QuoteMapper.mapQuoteEntityToQuoteDTO(quoteService.saveQuote(newQuoteDTO)));
    }

    @PutMapping(value = "/api/quotes/{id}")
    @ResponseBody
    public ResponseEntity changeQuote(@Valid @PathVariable Long id, @Valid @RequestBody NewQuoteDTO newQuoteDTO) {
        if(!quoteService.getQuote(id).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quote not found");

        return ResponseEntity.ok(QuoteMapper.mapQuoteEntityToQuoteDTO(quoteService.saveQuote(id, newQuoteDTO)));
    }

    @PatchMapping(value = "/api/quotes/{id}", consumes = "text/plain")
    @ResponseBody
    public ResponseEntity updateQuoteContent(@Valid @PathVariable Long id, @Valid @RequestBody String content) {
        if(!quoteService.getQuote(id).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quote not found");

        return ResponseEntity.ok(QuoteMapper.mapQuoteEntityToQuoteDTO(quoteService.updateQuoteContent(id, content)));
    }

    @DeleteMapping(value = "/api/quotes/{id}")
    @ResponseBody
    public ResponseEntity deleteQuote(@Valid @PathVariable Long id) throws NotFoundException {
        quoteService.deleteQuote(id);
        return ResponseEntity.ok().build();
    }

}
