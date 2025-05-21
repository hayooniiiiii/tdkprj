package com.example.tdkprj1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDto {

    private Long id;

    @JsonProperty("monthAccrued")
    private Integer leaveIwol;

    @JsonProperty("monthUsed")
    private Integer leaveUsemonth;

    @JsonProperty("monthlyBalance")
    private Integer leaveMonth;

    @JsonProperty("year")
    private Integer leaveYear;

    @JsonProperty("usedDays")
    private Integer leaveUseday;

    private Long userId;

    private Integer documentId;
}
