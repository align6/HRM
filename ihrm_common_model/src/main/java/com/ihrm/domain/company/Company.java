package com.ihrm.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "co_company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable {
    private static final long serialVersionUID = -4564562049298893420L;

    @Id
    private String id;//ID，自动创建

    private String name;//公司名称，不能为空

    private String managerId;//负责人ID，不能为空

    private String legalRepresentative;//法人代表

    private String companyPhone;//公司电话

    private String mailbox;//邮箱

    private Integer state;//状态，自动创建

    private Date createTime;//创建时间，自动创建
}