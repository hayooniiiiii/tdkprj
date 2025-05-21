package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbBuseo;
import com.example.tdkprj1.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuseoRepository extends JpaRepository<TbBuseo, Long> {
    Optional<TbBuseo> findById(int id);
}