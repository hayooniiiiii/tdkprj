package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_buseo")
public class TbBuseo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "buseo_name", nullable = false, length = 50)
    private String buseoName;

    @Column(name = "buseo_auth", nullable = false)
    private Long buseoAuth;

}