package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_buseomember")
public class TbBuseomember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buseo_id", nullable = false)
    private TbBuseo buseo;

    @Column(name = "buseomember_order", nullable = false)
    private Integer buseomemberOrder;

    @Column(name = "buseomember_auth")
    private Integer buseomemberAuth;

}