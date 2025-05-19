package com.example.tdkprj1.service;

import com.example.tdkprj1.entity.TbBuseo;
import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.UserDto;
import com.example.tdkprj1.repository.BuseoRepository;
import com.example.tdkprj1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final BuseoRepository buseoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void join(UserDto userDto) {
        // 1. buseo_id로 부서 엔티티 조회
        TbBuseo buseo = buseoRepository.findById(userDto.getBuseoId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서 ID"));

        // 2. TbUser 생성 및 필드 매핑
        TbUser tbUser = new TbUser();
        tbUser.setUserLoginid(userDto.getUserLoginid());
        tbUser.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
        tbUser.setUserName(userDto.getUserName());
        tbUser.setUserIpsa(userDto.getUserIpsa());
        tbUser.setUserRole(userDto.getUserRole());
        tbUser.setBuseo(buseo); // 3. 연관관계 설정

        // 4. 저장
        userRepository.save(tbUser);
    }


}
