package com.example.tdkprj1.controller;

import com.example.tdkprj1.model.UserDto;
import com.example.tdkprj1.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/join")
    public ResponseEntity<String> join(@RequestBody UserDto dto) {
        accountService.join(dto);
        return ResponseEntity.ok("회원가입 완료");
    }


}
