package com.graphql.api.shop.controllers;

import com.graphql.api.shop.models.entities.Author;
import com.graphql.api.shop.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
@Controller
@CrossOrigin
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @MutationMapping
    public Author createAuthor(@Argument Author author) {
        return authorService.save(author);
    }


    @MutationMapping
    public Author updateAuthorById(@Argument Long id, @Argument Author author) {
        return authorService.updateById(id, author);
    }

    @MutationMapping
    public void deleteAuthorById(@Argument Long id) {
        authorService.deleteById(id);
    }

    @QueryMapping
    public Author findAuthorById(@Argument Long id) {
        return authorService.findById(id);
    }

    @QueryMapping
    public List<Author> findAllAuthors() {
        return authorService.findAll();
    }
}
