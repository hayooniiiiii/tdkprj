package com.example.tdkprj1.repository;

import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TbUser, Long> {

    Optional<TbUser> findByUserLoginid(String userLoginid);
}