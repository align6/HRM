package com.ihrm.domain.social_securitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 社保归档表
 */
@Entity
@Table(name = "ss_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Archive implements Serializable {
    private static final long serialVersionUID = -957794155070906364L;

    @Id
    private String id; //id

    private String companyId; //企业id

    private String yearsMonth; //年月

    private Date creationTime; //创建时间

    private BigDecimal enterprisePayment; //企业缴费

    private BigDecimal personalPayment; //个人缴费

    private BigDecimal total; //合计

    public Archive(String companyId, String yearsMonth){
        this.companyId = companyId;
        this.yearsMonth = yearsMonth;
    }
}
