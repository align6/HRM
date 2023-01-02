package com.ihrm.domain.attendance.vo;

import com.ihrm.domain.attendance.entity.ExtraDutyRule;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ExtraDutyVo implements Serializable {
    private static final long serialVersionUID = -2271748380109240172L;

    private String companyId; //公司id

    @NotBlank(message = "部门ID不能为空")
    private String departmentId; //部门id

    private String workHoursDay; //每日标准工作时长，单位小时

    @NotNull(message = "是否打卡不能为空")
    private Integer isClock; //是否打卡 0开启 1关闭

    @NotNull(message = "是否开启加班补偿不能为空")
    private String isCompensation; //是否开启加班补偿 0开启 1关闭

    private String latestEffectDate; //调休最后有效日期

    @NotBlank(message = "调休单位不能为空(天最小0.5)")
    private String unit; //调休单位

    List<ExtraDutyRule> rules; //配置规则
}
