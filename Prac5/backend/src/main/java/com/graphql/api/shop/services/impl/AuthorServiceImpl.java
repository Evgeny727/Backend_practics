package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.entities.Author;
import com.graphql.api.shop.repositories.AuthorRepository;
import com.graphql.api.shop.services.AuthorService;
import com.graphql.api.shop.services.utils.UpdateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author updateById(Long id, Author author) {
        Author lastAuthor = findById(id);
        if (lastAuthor == null) {
            return null;
        }

        UpdateFields.updateField(author, lastAuthor);
        return save(lastAuthor);
    }

    @Override
    public List<Author> saveAll(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
