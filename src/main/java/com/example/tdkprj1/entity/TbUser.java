package com.example.tdkprj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class TbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_loginid", nullable = false, length = 50)
    private String userLoginid;

    @Column(name = "user_password", nullable = false, length = 80)
    private String userPassword;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_ipsa", nullable = false)
    private LocalDate userIpsa;

    @Column(name = "user_role", nullable = false, length = 30)
    private String userRole;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buseo_id", nullable = false)
    private TbBuseo buseo;

}