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
 * 社保-城市与缴费项目关联表
 */
@Entity
@Table(name = "ss_city_payment_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityPaymentItem implements Serializable {
    private static final long serialVersionUID = -417911401299222825L;

    @Id
    private String id; //id

    private String cityId; //城市id

    private String paymentItemId; //缴费项目id

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

    private String name; //缴费项目名称
}
