package com.ctos.dummy.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "aisle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "aisleId", scope = Long.class)
public class Aisle {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aisleId;

    @Column(nullable = false)
    private String isleName;
    
    @ManyToOne
    @JoinColumn(name = "library_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "library-aisle")
    private Library library;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "aisle_book",
        joinColumns = @JoinColumn(name = "aisle_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@JsonManagedReference(value = "aisle-book")
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        books.add(book);
        book.getAisles().add(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.getAisles().remove(this);
    }

}
