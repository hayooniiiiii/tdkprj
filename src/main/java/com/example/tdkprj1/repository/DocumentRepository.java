package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<TbDocument, Long> {
    List<TbDocument> findByUserId(Long userId);
}