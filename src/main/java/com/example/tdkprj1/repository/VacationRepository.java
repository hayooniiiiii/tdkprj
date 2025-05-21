package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbVacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<TbVacation, Long> {
    List<TbVacation> findByDocumentId(Long documentId);
}