package com.ihrm.domain.salarys.vo;

import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.salarys.Settings;
import com.ihrm.domain.salarys.UserSalary;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import com.ihrm.domain.social_securitys.UserSocialSecurity;
import com.ihrm.domain.system.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserSalaryVo {
    /**
     * 用户
     */
    private String username;

    private Date entryTime;

    private Integer inServiceStatus;

    /**
     * 工资
     */
    private BigDecimal latestSalary; //最新工资

    private BigDecimal basicSalaryForTheMonth; //当月基本工资

    private BigDecimal postWageForTheMonth; //当月岗位工资

    /**
     * 补助
     */
    private BigDecimal transportationSubsidyAmount; //交通补助

    private BigDecimal lunchAllowanceAmount; //午餐补助

    private BigDecimal communicationSubsidyAmount; //通讯补助

    private BigDecimal housingSubsidyAmount; //住房补助

    /**
     * 社保
     */
    private BigDecimal socialSecurityBase; //社保基数

    private BigDecimal providentFundBase; //公积金基数

    private BigDecimal socialSecurityCompanyBase; //企业社保缴纳

    private BigDecimal providentFundCompanyBase; //企业公积金缴纳

    private BigDecimal socialSecurityPersonalBase; //个人社保缴纳

    private BigDecimal providentFundPersonalBase; //个人公积金缴纳

    /**
     * 考勤
     */
    private String actualAtteOfficialDays; //实际出勤天数

    private String officialSalaryDays; //计薪天数


    public void setUser(User user){
        this.username = user.getUsername();
        this.entryTime = user.getEntryTime();
        this.inServiceStatus = user.getInServiceStatus();
    }

    public void setUserSalary(UserSalary userSalary){
        this.basicSalaryForTheMonth = userSalary.getCurrentBasicSalary();
        this.postWageForTheMonth = userSalary.getCurrentPostWage();
        this.latestSalary = this.basicSalaryForTheMonth.add(this.postWageForTheMonth);
    }

    public void setSettings(Settings settings){
        this.transportationSubsidyAmount = settings.getTransportationSubsidyAmount();
        this.lunchAllowanceAmount = settings.getLunchAllowanceAmount();
        this.communicationSubsidyAmount = settings.getCommunicationSubsidyAmount();
        this.housingSubsidyAmount = settings.getHousingSubsidyAmount();
    }

    public void setArchiveDetail(ArchiveDetail archiveDetail){
        this.socialSecurityBase = archiveDetail.getSocialSecurityBase();
        this.providentFundBase = archiveDetail.getProvidentFundBase();
        this.socialSecurityPersonalBase =  archiveDetail.getSocialSecurityIndividual();
        this.providentFundPersonalBase = archiveDetail.getProvidentFundIndividual();
        this.socialSecurityCompanyBase =  archiveDetail.getSocialSecurityEnterprise();
        this.providentFundCompanyBase = archiveDetail.getProvidentFundEnterprises();
    }

    public void setArchiveMonthlyInfo(ArchiveMonthlyInfo archiveMonthlyInfo){
        this.actualAtteOfficialDays = archiveMonthlyInfo.getActualAtteOfficialDays();
        this.officialSalaryDays = archiveMonthlyInfo.getSalaryOfficialDays();
    }
}
