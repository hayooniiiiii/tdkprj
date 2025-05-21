package com.example.tdkprj1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationDto {

    private Long id;

    private Long documentId;

    @JsonProperty("startDate")
    private LocalDate vacationStartday;

    @JsonProperty("endDate")
    private LocalDate vacationEndday;

    @JsonProperty("startTime")
    private LocalTime vacationStarttime;

    @JsonProperty("endTime")
    private LocalTime vacationEndtime;

    @JsonProperty("usageDays")
    private BigDecimal vacationUse;

    @JsonProperty("note")
    private String vacationContent;

    @JsonProperty("vacationType")
    private String vacationType;
}
