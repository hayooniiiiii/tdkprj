package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    

    @Column(name = "document_open", nullable = false)
    private Long documentOpen;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private TbUser user;

}