package com.sltc.dait.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
public class Lend {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String isbn;
    String lenderEmail;

    @CreationTimestamp
    Date lendDate;
    Date dueDate;

    @ColumnDefault("0")
    Integer overdueNotifyCount = 0;
}
