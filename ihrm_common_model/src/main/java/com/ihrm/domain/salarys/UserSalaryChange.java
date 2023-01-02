package com.ihrm.domain.salarys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sa_user_salary_change")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 工资-员工薪资变动表
 */
public class UserSalaryChange implements Serializable {
    private static final long serialVersionUID = -1292109712225526521L;

    @Id
    private String id; //id

    private String userId; //用户id

    private BigDecimal currentBasicSalary; //当前基本工资

    private BigDecimal currentPostWage; //当前岗位工资

    private BigDecimal adjustmentOfBasicWages; //调整基本工资

    private BigDecimal adjustPostWages; //调整岗位工资

    private Date effectiveTimeOfPayAdjustment; //调整生效时间

    private String causeOfSalaryAdjustment; //调整原因

    private String enclosure; //附件
}
