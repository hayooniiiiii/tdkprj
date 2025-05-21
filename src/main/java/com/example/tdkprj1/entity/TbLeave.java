package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tb_leave")
public class TbLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnDefault("0")
    @Column(name = "leave_iwol", nullable = false)
    private Integer leaveIwol;

    @ColumnDefault("0")
    @Column(name = "leave_usemonth", nullable = false)
    private Integer leaveUsemonth;

    @ColumnDefault("0")
    @Column(name = "leave_month", nullable = false)
    private Integer leaveMonth;

    @ColumnDefault("0")
    @Column(name = "leave_year", nullable = false)
    private Integer leaveYear;

    @ColumnDefault("0")
    @Column(name = "leave_useday", nullable = false)
    private Integer leaveUseday;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private TbUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private TbDocument document;

}