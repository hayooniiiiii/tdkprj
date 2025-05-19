package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbLeave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<TbLeave, Long> {
}