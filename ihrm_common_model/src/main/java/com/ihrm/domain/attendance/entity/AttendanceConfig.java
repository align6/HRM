package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_attendance_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 考勤配置表
 */
public class AttendanceConfig implements Serializable {
    private static final long serialVersionUID = -6869380312247517022L;

    @Id
    private String id; //id

    private String companyId; //公司id

    private String departmentId; //部门id

    private String morningStartTime; //上午上班时间

    private String morningEndTime; //上午下班时间

    private String afternoonStartTime; //下午上班时间

    private String afternoonEndTime; //下午下班时间
}
