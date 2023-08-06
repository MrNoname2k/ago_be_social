package org.api.payload.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportTotalUser {
    @JsonProperty("month")
    private int month;

    @JsonProperty("total")
    private Long total;
}
