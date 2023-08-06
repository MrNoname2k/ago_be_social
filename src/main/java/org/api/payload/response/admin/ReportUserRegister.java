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
public class ReportUserRegister {
    @JsonProperty("userToday")
    private Long userToday;

    @JsonProperty("userTotal")
    private Long userTotal;

    @JsonProperty("userThisMonth")
    private Long userThisMonth;

    @JsonProperty("userLastMonth")
    private Long userLastMonth;
}
