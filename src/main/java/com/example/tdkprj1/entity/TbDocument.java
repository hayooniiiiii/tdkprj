package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_document")
public class TbDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "docment_date", nullable = false)
    private LocalDate docmentDate;

    @Column(name = "document_title", nullable = false, length = 50)
    private String documentTitle;

    @Column(name = "document_vacationtype", nullable = false, length = 30)
    private String documentVacationtype;

    @Column(name = "document_vacationstart")
    private LocalDate documentVacationstart;

    @Column(name = "document_vacationend")
    private LocalDate documentVacationend;

    @Column(name = "document_vacationday")
    private Integer documentVacationday;

    @Lob
    @Column(name = "document_vacationdatail")
    private String documentVacationdatail;

    @Column(name = "document_open", nullable = false)
    private Long documentOpen;

}