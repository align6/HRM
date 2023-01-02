package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "atte_deduction_dict")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 扣款字典表
 */
public class DeductionDict implements Serializable {
    private static final long serialVersionUID = 5409326316447970235L;

    @Id
    private String id; //id

    private String companyId; //公司id

    private String departmentId; //部门id

    private String dedTypeCode; ////扣款类型编码

    private String periodLowerLimit; //时间段下限

    private String periodUpperLimit; //时间段上限

    private String timesLowerLimit; //次数下限

    private String timesUpperLimit; //次数上限

    private String absenceTimesUpperLimit; //旷工次数上限

    private BigDecimal dedAmountLowerLimit; //扣款金额下限

    private BigDecimal dedAmountUpperLimit; //扣款金额上限

    private String absenceDays; //旷工天数

    private String isAbsenteeism; //是否旷工 0不旷工 1旷工

    private String fineSalaryMultiples; //罚款工资倍数

    private Integer isEnable; //是否可用 0开启 1关闭
}
