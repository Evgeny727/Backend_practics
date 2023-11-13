package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.entities.base.BaseItem;
import com.graphql.api.shop.models.entities.items.Book;
import com.graphql.api.shop.repositories.BookRepository;
import com.graphql.api.shop.services.BookService;
import com.graphql.api.shop.services.ItemService;
import com.graphql.api.shop.services.utils.UpdateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ItemService itemService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ItemService itemService) {
        this.bookRepository = bookRepository;
        this.itemService = itemService;
    }

    @Override
    public Book save(Book book) {
        book.setBaseItem(itemService.save(book.getBaseItem()));
        book = bookRepository.saveAndFlush(book);
        bookRepository.clear();
        return findById(book.getId());
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book updateById(Long id, Book book) {
        Book lastBook = findById(id);
        if (lastBook == null) {
            return null;
        }
        if (book.getBaseItem() != null) {
            book.setBaseItem(itemService.updateById(
                    lastBook.getBaseItem().getId(),
                    book.getBaseItem()
            ));
        }
        UpdateFields.updateField(book, lastBook);
        return save(lastBook);
    }

    @Override
    public List<Book> saveAll(List<Book> books) {
        return books.stream().map(this::save).toList();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        BaseItem item = findById(id).getBaseItem();
        bookRepository.deleteById(id);
        itemService.deleteById(item.getId());
    }
}
