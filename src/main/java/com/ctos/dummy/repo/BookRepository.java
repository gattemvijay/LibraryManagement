package com.ctos.dummy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctos.dummy.model.Book;

@Repository
public interface BookRepository  extends JpaRepository<Book, Integer> {

}
