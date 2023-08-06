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
public class ReportFile {
    @JsonProperty("fileToday")
    private Long fileToday;

    @JsonProperty("fileTotal")
    private Long fileTotal;

    @JsonProperty("fileThisMonth")
    private Long fileThisMonth;

    @JsonProperty("fileLastMonth")
    private Long fileLastMonth;
}
