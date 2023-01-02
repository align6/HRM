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
@Table(name = "sa_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 工资-归档表
 */
public class SalaryArchive implements Serializable {

    @Id
    private String id;//id

    private String companyId; //企业id

    private String yearsMonth; //归档年月

    private Date creationTime; //创建时间

    private BigDecimal artificialCost; //人工成本

    private BigDecimal grossSalary; //税前工资

    private BigDecimal fiveInsurances; //五险一金
}
