package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_extra_duty_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 加班配置表
 */
public class ExtraDutyConfig implements Serializable {
    private static final long serialVersionUID = 5822920461063909662L;

    @Id
    private String id; //id

    private String companyId; //公司id

    private String departmentId; //部门id

    private String workHoursDay; //每日标准工作时长，单位小时

    private Integer isClock; //是否打卡 0开启 1关闭

    private String isCompensation; //是否开启加班补偿 0开启 1关闭
}
