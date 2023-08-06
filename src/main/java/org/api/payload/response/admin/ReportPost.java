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
public class ReportPost {
    @JsonProperty("postToday")
    private Long postToday;

    @JsonProperty("postTotal")
    private Long postTotal;

    @JsonProperty("postThisMonth")
    private Long postThisMonth;

    @JsonProperty("postLastMonth")
    private Long postLastMonth;
}
