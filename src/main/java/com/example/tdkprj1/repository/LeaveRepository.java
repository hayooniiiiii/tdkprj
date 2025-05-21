package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveRepository extends JpaRepository<TbLeave, Long> {
    Optional<TbLeave> findByuserId(long userId);

    Optional<TbLeave> findByUserId(Long id);

    Optional<TbLeave> findByDocumentId(Long documentId);
}