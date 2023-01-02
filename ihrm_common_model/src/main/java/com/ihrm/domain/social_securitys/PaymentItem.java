package com.ihrm.domain.social_securitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 社保-缴费项目
 */
@Entity
@Table(name = "ss_payment_Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentItem implements Serializable {
    private static final long serialVersionUID = -6418388548506446799L;

    @Id
    private String id; //id

    private String name; //缴费项目名称

    /**
     * 企业是否缴纳开关，0为禁用，1为启用
     */
    private Boolean switchCompany;

    private BigDecimal scaleCompany; //企业比例

    /**
     * 个人是否缴纳开关，0为禁用，1为启用
     */
    private Boolean switchPersonal;

    private BigDecimal scalePersonal; //个人比例
}
