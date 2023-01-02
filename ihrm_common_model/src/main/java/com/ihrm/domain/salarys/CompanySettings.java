package com.ihrm.domain.salarys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sa_company_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 工资-企业设置信息
 */
public class CompanySettings implements Serializable {
    private static final long serialVersionUID = 3587155322852854817L;

    @Id
    private String companyId; //企业id

    private Integer isSettings; //未设置

    private String dataMonth; //当前报表月份
}
