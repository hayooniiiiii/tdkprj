package com.example.tdkprj1.model;

import com.example.tdkprj1.entity.TbUser;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link TbUser}
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    Long id;
    String userLoginid;
    String userPassword;
    String userName;
    LocalDate userIpsa;
    String userRole;
    Long buseoId;
}