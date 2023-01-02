package com.ihrm.domain.social_securitys;

import com.ihrm.domain.employee.UserCompanyPersonal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 社保归档明细
 */
@Entity
@Table(name = "ss_archive_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDetail implements Serializable {
    private static final long serialVersionUID = -2806648327042200772L;

    @Id
    private String id; //id

    private String archiveId; //归档id

    private String userId; //用户id

    private String username; //姓名

    private Date entryTime; //入职时间

    private String mobile; //手机号

    private String idNumber; //身份证号

    private String departmentName; //部门

    private String householdRegistrationType; //户籍类型

    private String participatingInTheCity; //参保城市

    private String providentFundCity; //公积金城市

    /**
     * 社保
     */
    private String socialSecurityMonth; //社保月份
    private BigDecimal socialSecurityBase; //社保基数
    private BigDecimal socialSecurityEnterprise; //社保企业
    private BigDecimal socialSecurityIndividual; //社保个人
    private BigDecimal socialSecurity; //社保合计
    private String socialSecurityNotes; //社保备注

    /**
     * 公积金
     */
    private String providentFundMonth; //公积金月份
    private BigDecimal providentFundBase; //公积金基数
    private BigDecimal accumulationFundEnterpriseBase; //公积金企业基数
    private BigDecimal proportionOfProvidentFundEnterprises; //公积金企业比例
    private BigDecimal individualBaseOfProvidentFund; //公积金个人基数
    private BigDecimal personalRatioOfProvidentFund; //公积金个人比例
    private BigDecimal providentFundEnterprises; //公积金企业
    private BigDecimal providentFundIndividual; //公积金个人
    private BigDecimal totalProvidentFund; //公积金合计
    private String providentFundNotes; //公积金备注

    private String yearsMonth; //年月

    public void setUserSocialSecurity(UserSocialSecurity userSocialSecurity){
        this.householdRegistrationType = userSocialSecurity.getHouseholdRegistration();
        this.participatingInTheCity = userSocialSecurity.getParticipatingInTheCity();
        this.providentFundCity = userSocialSecurity.getProvidentFundCity();
        this.socialSecurityNotes = userSocialSecurity.getSocialSecurityNotes();
        this.providentFundNotes = userSocialSecurity.getProvidentFundNotes();
        this.socialSecurityBase = userSocialSecurity.getSocialSecurityBase();
        this.providentFundBase = userSocialSecurity.getProvidentFundBase();
        this.accumulationFundEnterpriseBase = userSocialSecurity.getProvidentFundBase();
        this.proportionOfProvidentFundEnterprises = userSocialSecurity.getEnterpriseProportion();
        this.personalRatioOfProvidentFund = userSocialSecurity.getPersonalProportion();
        this.individualBaseOfProvidentFund = userSocialSecurity.getProvidentFundBase();
        this.providentFundEnterprises = userSocialSecurity.getEnterpriseProvidentFundPayment();
        this.providentFundIndividual = userSocialSecurity.getPersonalProvidentFundPayment();
        this.totalProvidentFund = this.providentFundEnterprises.add(this.providentFundIndividual);
    }

    public void setUserCompanyPersonal(UserCompanyPersonal userCompanyPersonal){
        this.idNumber = userCompanyPersonal.getIdNumber();
    }
}
