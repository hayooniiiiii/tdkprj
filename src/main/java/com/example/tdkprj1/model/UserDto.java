package com.example.tdkprj1.model;

import com.example.tdkprj1.entity.TbUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link TbUser}
 */
@Getter
@Setter
@Data
@Value
public class UserDto {
    Long id;
    String userLoginid;
    String userPassword;
    String userName;
    LocalDate userIpsa;
    String userRole;
    Long buseoId;
}