package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbBuseomember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuseomemberRepository extends JpaRepository<TbBuseomember, Long> {
}