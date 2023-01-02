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
 * 社保-用户社保信息表
 */
@Entity
@Table(name = "ss_user_social_security")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSocialSecurity implements Serializable {

    private static final long serialVersionUID = 3186433481782390147L;

    @Id
    private String userId; //id

    /**
     * 本月是否缴纳社保 0为不缴纳 1为缴纳
     */
    private Integer enterprisesPaySocialSecurityThisMonth;

    /**
     * 本月是否缴纳公积金 0为不缴纳 1为缴纳
     */
    private Integer enterprisesPayTheProvidentFundThisMonth;

    /**
     * 参保类型  1为首次开户 2为非首次开户
     */
    private Integer socialSecurityType;

    /**
     * 户籍类型 1为本市城镇 2为本市农村 3为外地城镇 4为外地农村
     */
    private Integer householdRegistrationType;

    private BigDecimal socialSecurityBase; //社保基数

    private String socialSecurityNotes; //社保备注

    private BigDecimal providentFundBase; //公积金基数

    private BigDecimal enterpriseProportion; //公积金企业比例

    private BigDecimal personalProportion;  //公积金个人比例

    private BigDecimal enterpriseProvidentFundPayment; //公积金企业缴纳数额

    private BigDecimal personalProvidentFundPayment;  //公积金个人缴纳数额

    private String providentFundNotes; //公积金备注

    private Date lastModifyTime; //最后修改时间

    private Date socialSecuritySwitchUpdateTime; //社保是否缴纳变更时间

    private Date providentFundSwitchUpdateTime;  //公积金是否缴纳变更时间

    private String householdRegistration; //户籍

    private String participatingInTheCityId; //参保城市id

    private String participatingInTheCity; //参保城市

    private String providentFundCityId; //公积金城市id

    private String providentFundCity; //公积金城市
}
