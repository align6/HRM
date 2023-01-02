package com.ihrm.domain.attendance.entity;

import com.ihrm.domain.system.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "atte_archive_monthly_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 考勤归档信息详情表
 */
public class ArchiveMonthlyInfo implements Serializable {
    private static final long serialVersionUID = -4436852636195175768L;

    @Id
    private String id; //id

    private String userId; //用户id

    private String atteArchiveMonthlyId; //归档id

    private String name; //姓名

    private String workNumber; //工号

    private String mobile; //手机号

    private String department; //部门

    private String yearLeaveDays; //年假天数

    private String leaveDays; //事假天数

    private String sickLeaveDays; //病假天数

    private String longSickLeaveDays; //长期病假天数

    private String marriageLeaveDays; //婚假天数

    private String funeralLeaveDays; //丧假天数

    private String maternityLeaveDays; //产假天数

    private String rewardMaternityLeaveDays; //奖励产假天数

    private String paternityLeaveDays; //陪产假天数

    private String homeLeaveDays; //探亲假天数

    private String accidentalLeaveDays; //工伤假天数

    private String dayOffLeaveDays; //调休天数

    private String doctorOffLeaveDays; //产检假天数

    private String abortionLeaveDays; //流产假天数

    private String normalDays; //正常天数

    private String outgoingDays; //外出天数

    private String onBusinessDays; //出差天数

    private String laterTimes; //迟到次数

    private String earlyTimes; //早退次数

    private Integer signedTimes; //迟到次数

    private String hoursPerDays; //日均时长（自然日）

    private String hoursPerWorkDay; //日均时长(工作日)

    private String hoursPerRestDay; //日均时长（休息日)

    private String clockRate; //打卡率

    private String absenceDays; //旷工天数

    private Integer isFullAttendance; //是否全勤 0全勤 1非全勤

    private String actualAtteOfficialDays; //实际出勤天数（正式）

    private String workingDays; //应出勤工作日

    private String salaryStandards; //计薪标准

    private String salaryAdjustmentDays; //计薪天数调整

    private String workHour; //工作时长

    private String salaryOfficialDays; //计薪天数（正式）

    private String archiveDate; //归档日期

    public ArchiveMonthlyInfo(User user){
        this.userId = user.getId();
        this.name = user.getUsername();
        this.workNumber = user.getWorkNumber();
        this.department = user.getDepartmentName();
        this.mobile = user.getMobile();
    }

    public void setStatisticData(Map map){
        this.normalDays = (String) map.get("at1").toString();
        this.absenceDays = (String) map.get("at2").toString();
        this.laterTimes = (String) map.get("at3").toString();
        this.earlyTimes = (String) map.get("at4").toString();
        this.leaveDays = (String) map.get("at8").toString();
        this.dayOffLeaveDays = (String) map.get("at17").toString();
    }
}
