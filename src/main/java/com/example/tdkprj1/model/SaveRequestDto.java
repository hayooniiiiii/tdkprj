package com.example.tdkprj1.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveRequestDto {
    private DocumentDto information;           // 문서 정보
    private List<VacationDto> vacationDto;     // 휴가들
    private LeaveDto useYear;
}