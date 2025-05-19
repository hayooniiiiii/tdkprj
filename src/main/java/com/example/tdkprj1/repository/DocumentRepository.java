package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<TbDocument, Long> {
}