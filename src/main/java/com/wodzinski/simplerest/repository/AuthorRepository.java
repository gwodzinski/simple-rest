package com.wodzinski.simplerest.repository;

import com.wodzinski.simplerest.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
