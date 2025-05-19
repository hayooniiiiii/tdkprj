package com.example.tdkprj1.auth;

import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.UserDto;
import com.example.tdkprj1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userLoginid) throws UsernameNotFoundException {
        TbUser user = userRepository.findByUserLoginid(userLoginid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userLoginid: " + userLoginid));

        // ✅ TbUser → UserDto 변환
        UserDto userDto = new UserDto(
                user.getId(),
                user.getUserLoginid(),
                user.getUserPassword(),
                user.getUserName(),
                user.getUserIpsa(),
                user.getUserRole(),
                user.getBuseo() != null ? user.getBuseo().getId() : null
        );

        return new PrincipalDetails(userDto);
    }
}
