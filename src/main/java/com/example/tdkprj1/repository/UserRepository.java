package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TbUser, Long> {

}