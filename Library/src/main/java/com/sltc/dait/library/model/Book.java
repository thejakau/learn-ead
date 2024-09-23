package com.sltc.dait.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    String isbn;
    String title;
    String author;
}
