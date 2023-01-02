package com.ihrm.domain.attendance.entity;

import com.ihrm.domain.attendance.vo.AtteUploadVo;
import com.ihrm.domain.system.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "atte_attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 考勤表
 */
public class Attendance implements Serializable {
    private static final long serialVersionUID = -6165153781710721796L;

    @Id
    private String id; //id

    private String userId; //用户id

    private String companyId; //公司id

    private String departmentId; //部门id

    /**
     * 1正常 2旷工 3迟到 4早退 5外出 6出差 7年假
     * 8事假 9病假 10婚假 11丧假 12产假 13奖励产假
     * 14陪产假 15探亲假 16工伤假 17调休 18产检假
     * 19流产假 20长期病假 21无记录 22补签
     */
    private Integer adtStatus; //考勤状态

    private Integer jobStatus; //职位状态 1在职 2离职

    private Date adtInTime; //上班考勤时间

    private Date adtOutTime; //下班考勤时间

    private String day; //考勤日期

    public Attendance(AtteUploadVo vo, User user){
        this.adtInTime = vo.getInTime();
        this.adtOutTime = vo.getOutTime();
        this.day = vo.getAtteDate();
        this.userId = user.getId();
        this.companyId = user.getCompanyId();
        this.departmentId = user.getDepartmentId();
        this.jobStatus = user.getInServiceStatus();
    }

    public Attendance(User user){
        this.userId = user.getId();
        this.companyId = user.getCompanyId();
        this.departmentId = user.getDepartmentId();
        this.jobStatus = user.getInServiceStatus();
    }
}
