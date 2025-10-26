package com.ctos.dummy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctos.dummy.model.Library;


@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer>{

	// Search libraries where name like %name%
    List<Library> findByLibraryNameContainingIgnoreCase(String name);
    
}
