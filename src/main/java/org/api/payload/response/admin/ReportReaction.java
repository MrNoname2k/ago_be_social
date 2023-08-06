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
public class ReportReaction {
    @JsonProperty("reactionToday")
    private Long reactionToday;

    @JsonProperty("reactionTotal")
    private Long reactionTotal;

    @JsonProperty("reactionThisMonth")
    private Long reactionThisMonth;

    @JsonProperty("reactionLastMonth")
    private Long reactionLastMonth;
}
