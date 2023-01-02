package com.ihrm.domain.salarys;

import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.poi.ExcelAttribute;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Entity
@Table(name = "sa_archive_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 工资-归档详情
 */
public class SalaryArchiveDetail implements Serializable {
    private static final long serialVersionUID = 5868377083446715854L;

    @Id
    private String id; //id

    private String archiveId; //归档id

    private String userId; //用户id

    private String username; //姓名

    private String mobile; //手机号

    private String workNumber; //工号

    private String departmentName; //部门名称

    private Integer inServiceStatus; //在职状态

    /**
     * 社保相关
     */
    private BigDecimal providentFundIndividual; //公积金个人

    private BigDecimal socialSecurityIndividual; //社保个人

    private BigDecimal socialSecurity; //社保扣款+个人社保扣款

    private BigDecimal totalProvidentFundIndividual; //公积金扣款+个人公积金扣款

    private BigDecimal socialSecurityEnterprise; //社保企业

    private BigDecimal providentFundEnterprises; //公积金企业

    private BigDecimal socialSecurityProvidentFundEnterprises; //公积金社保企业

    private BigDecimal taxToProvidentFund; //公积金需纳税额

    /**
     * 考勤相关
     */
    private BigDecimal officialSalaryDays; //计薪天数

    private String attendanceDeductionMonthly; //考勤扣款

    private BigDecimal salaryStandard; //计薪标准

    /**
     * 薪资相关
     */
    private BigDecimal currentSalaryTotalBase; //最新工资基数合计 基本工资+岗位工资

    private BigDecimal currentBaseSalary; //最新基本工资基数

    private BigDecimal baseSalaryByMonth; //当月基本工资基数

    private String taxCountingMethod; //计税方式

    private BigDecimal baseSalaryToTaxByMonth; //当月纳税基本工资 = 当月基本工资基数

    private BigDecimal salaryBeforeTax; //税前工资合计 （当月基本工资+当月岗位工资）

    private BigDecimal salary; //工资合计 （基本工资+岗位工资+津贴)

    private BigDecimal salaryByTax; //应纳税工资 基本工资 + 岗位工资 + （【开关】津贴）

    private BigDecimal paymentBeforeTax; //税前实发  基本工资 + 岗位工资 + 津贴 - 五险一金

    private BigDecimal tax; //应扣税 （工资 + 【开关】津贴）* 阶梯税率 - 速算扣除数

    private BigDecimal salaryAfterTax; //税后工资合计 税前工资 - 税

    private BigDecimal payment; //实发工资  基本工资+岗位工资 + 津贴 - 五险一金 -税

    private String paymentRemark; //实发工资备注

    private BigDecimal salaryChangeAmount; //调薪金额 当月的


    public BigDecimal getProvidentFundEnterprises() {
        return this.providentFundEnterprises == null ? new BigDecimal(0) : this.providentFundEnterprises;
    }

    public BigDecimal getSocialSecurityEnterprise() {
        return this.socialSecurityEnterprise == null ? new BigDecimal(0) : this.socialSecurityEnterprise;
    }

    public BigDecimal getSocialSecurityIndividual() {
        return this.socialSecurityIndividual == null ? new BigDecimal(0) : this.socialSecurityIndividual;
    }
    public BigDecimal getProvidentFundIndividual() {
        return this.providentFundIndividual == null ? new BigDecimal(0) : this.providentFundIndividual;
    }

    public BigDecimal getSalaryBeforeTax() {
        return this.salaryBeforeTax == null ? new BigDecimal(0) : this.salaryBeforeTax;
    }

    public BigDecimal getEntTotal() {
        return getProvidentFundEnterprises().add(getSocialSecurityEnterprise());
    }

    public BigDecimal getPerTotal() {
        return getSocialSecurityIndividual().add(getProvidentFundIndividual());
    }

    public void setUser(Map map){
        this.username = map.get("username").toString();
        this.departmentName = map.get("departmentName").toString();
        this.mobile = map.get("mobile").toString();
        this.userId = map.get("id").toString();
        this.workNumber = map.get("workNumber").toString();
        this.inServiceStatus = (Integer) map.get("inServiceStatus");
    }

    public void setSocialInfo(ArchiveDetail socialInfo){
        if (socialInfo.getProvidentFundEnterprises() != null){
            this.providentFundEnterprises = socialInfo.getProvidentFundEnterprises();
        }
        if (socialInfo.getProvidentFundIndividual() != null){
            this.providentFundIndividual = socialInfo.getProvidentFundIndividual();
        }
        if (socialInfo.getSocialSecurityEnterprise() != null){
            this.socialSecurityEnterprise = socialInfo.getSocialSecurityEnterprise();
        }
        if (socialInfo.getSocialSecurityIndividual() != null){
            this.socialSecurityIndividual = socialInfo.getSocialSecurityIndividual();
        }
        if (socialInfo.getProvidentFundEnterprises() != null && socialInfo.getSocialSecurityEnterprise() != null){
            this.socialSecurityProvidentFundEnterprises = this.providentFundEnterprises.add(this.socialSecurityEnterprise);
        }
    }

    public void setAtteInfo(ArchiveMonthlyInfo atteInfo){
        if (atteInfo.getSalaryOfficialDays() != null){
            this.officialSalaryDays = new BigDecimal(atteInfo.getSalaryOfficialDays());
        }
    }

    public void setUserSalary(UserSalary userSalary) {
        if(userSalary != null) {
            this.currentSalaryTotalBase = userSalary.getCurrentBasicSalary().add(userSalary.getCurrentPostWage());
            this.currentBaseSalary = userSalary.getCurrentBasicSalary();
            this.baseSalaryByMonth = userSalary.getCurrentBasicSalary();
        }else{
            this.currentSalaryTotalBase = BigDecimal.ZERO;
            this.currentBaseSalary =BigDecimal.ZERO;
            this.baseSalaryByMonth = BigDecimal.ZERO;
        }
    }

    // 计算工资
    public void calSalary(Settings settings) {
        //计算福利津贴
        BigDecimal money = BigDecimal.ZERO;
        if(settings.getCommunicationSubsidyScheme() == 3) {
            money = money.add(settings.getCommunicationSubsidyAmount());
        }else{
            money = money.add(settings.getCommunicationSubsidyAmount().multiply(this.officialSalaryDays));
        }
        if(settings.getHousingSubsidyScheme() ==3) {
            money = money.add(settings.getHousingSubsidyAmount());
        }else{
            money = money.add(settings.getHousingSubsidyAmount().multiply(this.officialSalaryDays));
        }
        if(settings.getLunchAllowanceScheme() ==3) {
            money = money.add(settings.getLunchAllowanceAmount());
        }else{
            money = money.add(settings.getLunchAllowanceAmount().multiply(this.officialSalaryDays));
        }
        if(settings.getTransportationSubsidyScheme() ==3) {
            money = money.add(settings.getTransportationSubsidyAmount());
        }else{
            money = money.add(settings.getTransportationSubsidyAmount().multiply(this.officialSalaryDays));
        }
        //津贴
        this.salaryChangeAmount = money;
        //计算考勤扣款
        BigDecimal attendanceMoney = this.currentSalaryTotalBase;
        if(officialSalaryDays.compareTo(new BigDecimal(21.75))<=0) {
            attendanceMoney = this.currentSalaryTotalBase.
                    divide(new BigDecimal(21.75),2,ROUND_HALF_UP).
                    multiply(this.officialSalaryDays);
            this.attendanceDeductionMonthly = this.currentSalaryTotalBase.subtract(attendanceMoney).toString();
        }else{
            this.attendanceDeductionMonthly = "0";
        }


        this.currentSalaryTotalBase = attendanceMoney.add(money);

        //计算应纳税工资 (岗位工资 + 基本工资 + 补助 - 缴纳公积金和社保)
        if (this.providentFundIndividual != null && this.socialSecurityIndividual != null){
            this.salaryByTax = this.currentSalaryTotalBase.subtract(this.providentFundIndividual).subtract(this.socialSecurityIndividual);
            this.salaryByTax = this.salaryByTax.compareTo(BigDecimal.ZERO)>=0?this.salaryByTax:BigDecimal.ZERO;
            //计算税(扣除员工社保和员工公积金部门)
            this.tax = getTax(salaryByTax);
            //计算实发工资
            this.payment = attendanceMoney.subtract(tax);
            this.payment = this.payment.compareTo(BigDecimal.ZERO)>=0?this.payment:BigDecimal.ZERO;
        }
    }

    private BigDecimal getTax(BigDecimal salary) {
        //salary * 阶梯税率 - 速算扣除数
        BigDecimal easyNum = new BigDecimal(0);
        BigDecimal stageNum = new BigDecimal(0.03);
        if(salary.doubleValue() <=3500){
            return BigDecimal.ZERO;
        } else if (salary.doubleValue() > 1500) {
            easyNum = new BigDecimal(105);
            stageNum = new BigDecimal(0.1);
        } else if (salary.doubleValue() > 4500) {
            easyNum = new BigDecimal(555);
            stageNum = new BigDecimal(0.2);
        } else if (salary.doubleValue() > 9000) {
            easyNum = new BigDecimal(1005);
            stageNum = new BigDecimal(0.25);
        } else if (salary.doubleValue() > 35000) {
            easyNum = new BigDecimal(2755);
            stageNum = new BigDecimal(0.3);
        } else if (salary.doubleValue() > 55000) {
            easyNum = new BigDecimal(5505);
            stageNum = new BigDecimal(0.35);
        } else if (salary.doubleValue() > 55000) {
            easyNum = new BigDecimal(5505);
            stageNum = new BigDecimal(0.35);
        } else if (salary.doubleValue() > 80000) {
            easyNum = new BigDecimal(13505);
            stageNum = new BigDecimal(0.45);
        }
        salary = salary.multiply(stageNum);
        salary = salary.subtract(easyNum);
        return salary;
    }
}
