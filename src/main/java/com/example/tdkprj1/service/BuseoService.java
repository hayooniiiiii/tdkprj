package com.example.tdkprj1.service;

import com.example.tdkprj1.entity.TbBuseo;
import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.UserDto;
import com.example.tdkprj1.repository.BuseoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BuseoService {

    private final BuseoRepository buseoRepository;

}
