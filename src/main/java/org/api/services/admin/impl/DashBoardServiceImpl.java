package org.api.services.admin.impl;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.payload.ResultBean;
import org.api.payload.response.admin.*;
import org.api.repository.*;
import org.api.services.admin.DashBoarService;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class DashBoardServiceImpl implements DashBoarService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PostEntityRepository postEntityRepository;
    @Autowired
    private CommentEntityRepository commentEntityRepository;
    @Autowired
    private LikeEntityRepository likeEntityRepository;
    @Autowired
    private ShareEntityRepository shareEntityRepository;

    @Autowired
    private FileEntityRepository fileEntityRepository;

    @Autowired
    private MessageEntityRepository messageEntityRepository;

    @Override
    public ResultBean totalReport() throws ApiValidateException, Exception {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();
        ReportUserRegister u = getReportUserRegister(day,month,year);
        ReportReaction r = getReportReaction();
        ReportFile f = getReportFile(day,month,year);
        ReportPost p = getReportPost(day,month,year);
        List<ReportTotalUser> l = userEntityRepository.totalUsers(year);
        List<ReportUserAccessByHour> l1 = userEntityRepository.totalEachHour(day,month,year);
        List<ReportMessage> lMsg = getReportMessage(year);
        DashboardResponse response = mapData(l,l1,u,r,f,p,lMsg);
        return new ResultBean(response, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }
    private ReportFile getReportFile(int day, int month, int year){
        int lastMonth;
        if(month - 1 > 0){
            lastMonth = month -1;
        }else {
            lastMonth = 12;
            year -= 1;
        }
        try {
            long fileToday = fileEntityRepository.countFileToday(day, month, year);
            long fileTotal = fileEntityRepository.countAllFile();
            long fileThisMonth = fileEntityRepository.countFileByMonth(month, year);
            long fileLastMonth = fileEntityRepository.countFileByMonth(lastMonth,year);
            ReportFile f = new ReportFile(fileToday,fileTotal,fileThisMonth,fileLastMonth);
            return f;
        }catch (Exception e){
            return null;
        }
    }
    private ReportReaction getReportReaction(){
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();
        int lastMonth;
        if(month - 1 > 0){
            lastMonth = month -1;
        }else {
            lastMonth = 12;
            year -= 1;
        }
        try {
            long cmts = commentEntityRepository.countAllComment();
            long likes = likeEntityRepository.countAllLike();
            long shares = shareEntityRepository.countAllShare();
            long totalReactions = cmts + likes + shares;
            long cmtsToday = commentEntityRepository.countCommentToday(day,month,year);
            long likesToday = likeEntityRepository.countLikeToday(day,month,year);
            long sharesToday = shareEntityRepository.countShareToday(day,month,year);
            long todayReaction = cmtsToday+likesToday+sharesToday;
            long cmtsThisMonth = commentEntityRepository.countCommentByMonth(month,year);
            long likesThisMonth = likeEntityRepository.countLikeByMonth(month,year);
            long sharesThisMonth = shareEntityRepository.countShareByMonth(month,year);
            long thisMonthReaction = cmtsThisMonth+likesThisMonth+sharesThisMonth;
            long cmtsLastMonth = commentEntityRepository.countCommentByMonth(lastMonth,year);
            long likesLastMonth = likeEntityRepository.countLikeByMonth(lastMonth,year);
            long sharesLastMonth = shareEntityRepository.countShareByMonth(lastMonth,year);
            long lastMonthReaction = cmtsLastMonth+likesLastMonth+sharesLastMonth;

            ReportReaction r = new ReportReaction(todayReaction,totalReactions,thisMonthReaction,lastMonthReaction);
            return r;
        }catch (Exception e){
            return null;
        }
    }
    private List<ReportMessage> getReportMessage(int year){
        List<ReportMessage> l = messageEntityRepository.countAllMessage(year);
        return l;
    }
    private ReportUserRegister getReportUserRegister(int day, int month, int year){
        int lastMonth;
        if(month - 1 > 0){
            lastMonth = month -1;
        }else {
            lastMonth = 12;
            year -= 1;
        }
        long userToday = userEntityRepository.countUserToday(day,month,year);
        long userThisMonth = userEntityRepository.countUserByMonth(month,year);
        long userLastMonth = userEntityRepository.countUserByMonth(lastMonth,year);
        long userTotal = userEntityRepository.countAllPost();
        ReportUserRegister u = new ReportUserRegister(userToday,userTotal,userThisMonth,userLastMonth);
        return u;
    }
    private ReportPost getReportPost(int day, int month, int year){
        int lastMonth;
        if(month - 1 > 0){
            lastMonth = month -1;
        }else {
            lastMonth = 12;
            year -= 1;
        }
        long postToday = postEntityRepository.countPostToday(day,month,year);
        long postThisMonth = postEntityRepository.countPostByMonth(month,year);
        long postLastMonth = postEntityRepository.countPostByMonth(lastMonth,year);
        long postTotal = postEntityRepository.countAllPost();
        ReportPost p = new ReportPost(postToday,postTotal,postThisMonth,postLastMonth);
        return p;
    }

    private DashboardResponse mapData(List<ReportTotalUser> rTP,
                                      List<ReportUserAccessByHour> rUAH,
                                      ReportUserRegister u,
                                      ReportReaction r,
                                      ReportFile f,
                                      ReportPost p,
                                      List<ReportMessage> lMsg){
        DashboardResponse response = new DashboardResponse();
        response.setReportUserAccessByHour(rUAH);
        response.setReportUserRegisterByMonth(rTP);
        response.setReportPost(p);
        response.setReportUserRegister(u);
        response.setReportReaction(r);
        response.setReportFile(f);
        response.setReportMessage(lMsg);
        return response;
    }
}
