package com.ctos.dummy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctos.dummy.model.Aisle;
import com.ctos.dummy.model.Library;

@Repository
public interface AisleRepository extends JpaRepository<Aisle, Integer> {

	List<Aisle> findByLibrary(Library library);
    List<Aisle> findByIsleNameIgnoreCaseAndLibrary_LibraryNameIgnoreCase(String isleName, String libraryName);

}
