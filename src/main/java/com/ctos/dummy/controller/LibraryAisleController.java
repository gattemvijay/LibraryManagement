package com.ctos.dummy.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctos.dummy.model.Aisle;
import com.ctos.dummy.model.Book;
import com.ctos.dummy.model.Library;
import com.ctos.dummy.service.AisleService;
import com.ctos.dummy.service.LibraryService;

@RestController
@RequestMapping("/api/libraries")
public class LibraryAisleController {
	
	private final LibraryService libraryService;
	
    private final AisleService aisleService;
	
	public LibraryAisleController(LibraryService libraryService,AisleService aisleService) {
        this.libraryService = libraryService;
        this.aisleService = aisleService;
    }
	
	// (13) GET all books from aisle "NATURAL HISTORY" in library "CENTRAL LIBRARY"
    @GetMapping("/central/natural-history/books")
    public ResponseEntity<Set<Book>> getBooksFromSpecificAisleAndLibrary() {
        Set<Book> books = libraryService.getAllBooksByIsleNameAndLibrary("NATURAL HISTORY", "CENTRAL LIBRARY");
        return ResponseEntity.ok(books);
    }

    // (14) POST - save library info
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Library> saveLibrary(@RequestBody Library library) {
    	System.out.println("Received library: " + library);
        Library saved = libraryService.saveLibrary(library);
        return ResponseEntity.ok(saved);
    }

    // (15) PUT - update library info
    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable("id")  Integer id, @RequestBody Library library) {
        Library updated = libraryService.updateLibrary(id, library);
        return ResponseEntity.ok(updated);
    }

    // (16) GET - get all aisles in a library
    @GetMapping("/{libraryName}/aisles")
    public ResponseEntity<List<Aisle>> getAllAislesByLibrary(@PathVariable("libraryName") String libraryName) {
        List<Aisle> aisles = libraryService.getAllAislesByLibraryName(libraryName);
        return ResponseEntity.ok(aisles);
    }


}
