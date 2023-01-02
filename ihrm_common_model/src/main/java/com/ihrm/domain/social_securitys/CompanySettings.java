package com.ihrm.domain.social_securitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 企业社保的配置表
 */
@Entity
@Table(name = "ss_company_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySettings implements Serializable {
    private static final long serialVersionUID = -2405787805734896988L;

    @Id
    private String companyId; //企业id

    /**
     * 是否设置 0为未设置，1为已设置
     */
    private Integer isSettings;

    private String dataMonth; //当前显示报表月份
}
