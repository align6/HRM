package com.ihrm.domain.salarys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "sa_user_salary")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 工资-员工薪资表
 */
public class UserSalary implements Serializable {
    private static final long serialVersionUID = 1558275543401735723L;

    @Id
    private String userId; //用户id

    private BigDecimal currentBasicSalary; //当前基本工资

    private BigDecimal currentPostWage; //当前岗位工资

    private BigDecimal fixedBasicSalary; //定薪基本工资

    private BigDecimal fixedPostWage; //定薪岗位工资

    private BigDecimal correctionOfBasicWages; //转正基本工资

    private BigDecimal turnToPostWages; //转正岗位工资

    public BigDecimal getCurrentBasicSalary() {
    	return this.currentBasicSalary==null?BigDecimal.ZERO:this.currentBasicSalary;
    }

	public BigDecimal getCurrentPostWage() {
		return this.currentPostWage==null?BigDecimal.ZERO:this.currentPostWage;
	}


}
