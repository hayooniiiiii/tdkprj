package com.example.tdkprj1.controller;

import com.example.tdkprj1.entity.TbLeave;
import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.LeaveDto;
import com.example.tdkprj1.service.JwtService;
import com.example.tdkprj1.service.LeaveService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class LeaveController {
    private final JwtService jwtService;
    private final LeaveService leaveService;

    @GetMapping("/document/leave")
    public ResponseEntity<?> getLeave(HttpServletRequest request) {
        // 1. JWT에서 사용자 가져오기
        TbUser tbUser = jwtService.getUserFromJwt(request)
                .orElseThrow(() -> new IllegalArgumentException("Unauthorized"));

        // 2. userId로 leave 조회
        Optional<TbLeave> tbLeave = leaveService.findByuserId(tbUser.getId());

        // 3. LeaveDto로 변환
        LeaveDto leaveDto = null;
        if (tbLeave.isPresent()) {
            TbLeave leave = tbLeave.get();
            leaveDto = new LeaveDto(
                    leave.getId(),
                    leave.getLeaveIwol(),
                    leave.getLeaveUsemonth(),
                    leave.getLeaveMonth(),
                    leave.getLeaveYear(),
                    leave.getLeaveUseday(),
                    leave.getUser().getId(),
                   null


            );
        }

        // 4. 응답 반환
        return ResponseEntity.ok(leaveDto); // leaveDto가 null이면 JSON에 null 리턴
    }
}