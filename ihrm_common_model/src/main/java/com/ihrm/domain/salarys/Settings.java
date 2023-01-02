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
@Table(name = "sa_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 工资-企业设置信息
 */
public class Settings implements Serializable {
    private static final long serialVersionUID = -9103385001481808773L;

    @Id
    private String companyId; //企业id

    private Integer socialSecurityType; //对应社保自然月

    private String subsidyName; //津贴方案名称

    private String subsidyRemark; //津贴备注

    private Integer transportationSubsidyScheme; //交通补贴计算类型

    private BigDecimal transportationSubsidyAmount; //交通补贴金额

    private Integer communicationSubsidyScheme; //通讯补贴计算类型

    private BigDecimal communicationSubsidyAmount; //通讯补贴金额

    private Integer lunchAllowanceScheme; //午餐补贴计算类型

    private BigDecimal lunchAllowanceAmount; //午餐补贴金额

    private Integer housingSubsidyScheme; //住房补助计算类型

    private BigDecimal housingSubsidyAmount; //住房补助金额

    private Integer taxCalculationType; //计税方式
}
