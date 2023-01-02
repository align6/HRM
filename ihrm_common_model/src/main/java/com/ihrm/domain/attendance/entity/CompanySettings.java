package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_company_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 考勤-企业设置信息
 */
public class CompanySettings implements Serializable {
    private static final long serialVersionUID = -792632456755520842L;

    @Id
    private String companyId; //公司id

    private Integer isSettings; //是否设置 0为未设置，1为已设置

    private String dataMonth; //当前显示报表月份
}
