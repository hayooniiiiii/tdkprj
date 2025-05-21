package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "tb_vacation")
public class TbVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "document_id", nullable = false)
    private TbDocument document;

    @Column(name = "vacation_startday", nullable = false)
    private LocalDate vacationStartday;

    @Column(name = "vacation_type", nullable = false)
    private String vacationType;

    @Column(name = "vacation_endday", nullable = false)
    private LocalDate vacationEndday;

    @Column(name = "vacation_starttime")
    private LocalTime vacationStarttime;

    @Column(name = "vacation_endtime")
    private LocalTime vacationEndtime;

    @ColumnDefault("0.00")
    @Column(name = "vacation_use", nullable = false, precision = 5, scale = 2)
    private BigDecimal vacationUse;

    @Lob
    @Column(name = "vacation_content")
    private String vacationContent;

}