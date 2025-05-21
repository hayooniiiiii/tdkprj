package com.example.tdkprj1.service;

import com.example.tdkprj1.entity.TbLeave;
import com.example.tdkprj1.repository.LeaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;

    public Optional<TbLeave> findByuserId(long userId) {
        return leaveRepository.findByuserId(userId);
    }
}
