package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_extra_duty_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 加班规则
 */
public class ExtraDutyRule implements Serializable {
    private static final long serialVersionUID = 3801212761222501264L;

    @Id
    private String id; //id

    private String extraDutyConfigId; //加班配置id

    private String companyId; //公司id

    private String departmentId; //部门id

    private String rule; //规则内容

    private String ruleStartTime; //规则生效每日开始时间

    private String ruleEndTime; //则生效每日结束时间

    private Integer isTimeOff; //是否调休 0不调休 1调休

    private Integer isEnable; //是否可用 0开启 1关闭
}
