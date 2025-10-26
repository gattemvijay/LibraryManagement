package com.ctos.dummy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctos.dummy.model.Aisle;
import com.ctos.dummy.model.Book;
import com.ctos.dummy.repo.AisleRepository;
import com.ctos.dummy.repo.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AisleService {

	@Autowired
    private AisleRepository aisleRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void addBookToAisle(Integer aisleId, Integer bookId) {
        Aisle aisle = aisleRepository.findById(aisleId)
            .orElseThrow(() -> new EntityNotFoundException("Aisle not found"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        aisle.addBook(book);
        aisleRepository.save(aisle);
    }

    @Transactional
    public void removeBookFromAisle(Integer aisleId, Integer bookId) {
        Aisle aisle = aisleRepository.findById(aisleId)
            .orElseThrow(() -> new EntityNotFoundException("Aisle not found"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        aisle.removeBook(book);
        aisleRepository.save(aisle);
    }
    
}
