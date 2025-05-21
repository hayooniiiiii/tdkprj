package com.example.tdkprj1.controller;

import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.UserDto;
import com.example.tdkprj1.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor
public class MainController {

    private final JwtService jwtService;

    @GetMapping("/main/profile")
    public ResponseEntity<Map<String, Object>> getProfile(HttpServletRequest request) {
        TbUser tbUser = jwtService.getUserFromJwt(request)
                .orElseThrow(() -> new IllegalArgumentException("Unauthorized"));

        UserDto dto = new UserDto(
                tbUser.getId(),
                tbUser.getUserLoginid(),
                tbUser.getUserPassword(),
                tbUser.getUserName(),
                tbUser.getUserIpsa(),
                tbUser.getUserRole(),
                tbUser.getBuseo().getId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("user", dto);
        response.put("buseoName", tbUser.getBuseo().getBuseoName());

        return ResponseEntity.ok(response);
    }

}
