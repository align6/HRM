package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_day_off_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 调休配置表
 */
public class DayOffConfig implements Serializable {
    private static final long serialVersionUID = -6263615457132337323L;

    @Id
    private String id; //id

    private String companyId; //公司id

    private String departmentId; //部门Id

    private String latestEffectDate; //调休最后有效日期

    private String unit; //调休单位(天最小0.5)
}
