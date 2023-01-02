package com.ihrm.domain.attendance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "atte_deduction_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 扣款类型表
 */
public class DeductionType implements Serializable {
    private static final long serialVersionUID = 5015266330155572861L;

    @Id
    private String code; //扣款类型编码

    private String description; //扣款类型编码说明
}
