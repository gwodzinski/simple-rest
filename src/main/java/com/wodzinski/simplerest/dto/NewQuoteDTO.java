package com.wodzinski.simplerest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewQuoteDTO {

    @NotNull(message="Quote cannot be missing or empty")
    @Size(min=10, message="Quote content must not be less than 10 characters")
    String quote;

    @NotNull(message="First name cannot be missing or empty")
    @Size(min=2, message="First name must not be less than 2 characters")
    String firstName;

    @NotNull(message="Last name cannot be missing or empty")
    @Size(min=2, message="Last name must not be less than 2 characters")
    String lastName;
}
