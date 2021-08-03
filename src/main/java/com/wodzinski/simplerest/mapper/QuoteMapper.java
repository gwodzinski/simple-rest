package com.wodzinski.simplerest.mapper;

import com.wodzinski.simplerest.dto.NewQuoteDTO;
import com.wodzinski.simplerest.dto.QuoteDTO;
import com.wodzinski.simplerest.model.AuthorEntity;
import com.wodzinski.simplerest.model.QuoteEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QuoteMapper {

    public static QuoteDTO mapQuoteEntityToQuoteDTO(QuoteEntity entity) {
        return QuoteDTO.builder()
                .id(entity.getId())
                .author(entity.getAuthor().getFirstName() + " " + entity.getAuthor().getLastName())
                .content(entity.getContent())
                .build();
    }

    public static QuoteEntity mapQuoteDTOToQuoteEntity(NewQuoteDTO dto) {
        return QuoteEntity.builder()
                .content(dto.getQuote())
                .author(AuthorEntity.builder()
                        .firstName(dto.getFirstName().toUpperCase().trim())
                        .lastName(dto.getLastName().toUpperCase().trim())
                        .build())
                .build();
    }
}
