package com.ctos.dummy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ctos.dummy.model.Aisle;
import com.ctos.dummy.model.Book;
import com.ctos.dummy.model.Library;
import com.ctos.dummy.repo.AisleRepository;
import com.ctos.dummy.repo.BookRepository;
import com.ctos.dummy.repo.LibraryRepository;

import jakarta.transaction.Transactional;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final AisleRepository aisleRepository;
    private final BookRepository bookRepository;

    public LibraryService(LibraryRepository libraryRepository,
                          AisleRepository aisleRepository,
                          BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.aisleRepository = aisleRepository;
        this.bookRepository = bookRepository;
    }

    // (8) Get all aisles based on library
    public List<Aisle> getAllAislesByLibraryName(String libraryName) {
        return libraryRepository.findByLibraryNameContainingIgnoreCase(libraryName)
                .stream()
                .findFirst()
                .map(aisleRepository::findByLibrary)
                .orElseThrow(() -> new RuntimeException("Library not found: " + libraryName));
    }

    // (9) Save library using Streams
    @Transactional
    public Library saveLibrary(Library library) {
        Optional.ofNullable(library.getAisles())
                .ifPresent(aisles -> aisles.forEach(aisle -> {
                    aisle.setLibrary(library);
                    Optional.ofNullable(aisle.getBooks())
                            .ifPresent(books -> books.forEach(book -> book.getAisles().add(aisle)));
                }));
        return libraryRepository.save(library);
    }

    // (10) Update library using Streams
    @Transactional
    public Library updateLibrary(Integer id, Library updatedLibrary) {
        Library existing = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library not found: " + id));

        existing.setLibraryName(updatedLibrary.getLibraryName());
        existing.getAisles().clear();

        Optional.ofNullable(updatedLibrary.getAisles())
                .ifPresent(aisles -> existing.getAisles().addAll(
                        aisles.stream().map(updatedAisle -> {
                            Aisle aisle = new Aisle();
                            aisle.setIsleName(updatedAisle.getIsleName());
                            aisle.setLibrary(existing);

                            // Build book set using streams
                            Set<Book> books = Optional.ofNullable(updatedAisle.getBooks())
                                    .map(bookList -> bookList.stream()
                                            .map(updatedBook -> {
                                                Book book = new Book();
                                                book.setBookName(updatedBook.getBookName());
                                                book.getAisles().add(aisle);
                                                return book;
                                            })
                                            .collect(Collectors.toSet()))
                                    .orElseGet(HashSet::new);

                            aisle.setBooks(books);
                            return aisle;
                        }).collect(Collectors.toList())
                ));

        return libraryRepository.save(existing);
    }

    // (11) Get all books based on aisle info using Streams
    public Set<Book> getAllBooksByIsleNameAndLibrary(String isleName, String libraryName) {
        return aisleRepository.findByIsleNameIgnoreCaseAndLibrary_LibraryNameIgnoreCase(isleName, libraryName)
                .stream()
                .flatMap(aisle -> aisle.getBooks().stream())
                .collect(Collectors.toSet());
    }
}