package org.api.payload.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse implements Serializable {
    private List<ReportTotalUser> reportUserRegisterByMonth;
    private List<ReportUserAccessByHour> reportUserAccessByHour;
    private ReportPost reportPost;
    private ReportUserRegister reportUserRegister;
    private ReportReaction reportReaction;
    private ReportFile reportFile;
    private List<ReportMessage> reportMessage;
}
