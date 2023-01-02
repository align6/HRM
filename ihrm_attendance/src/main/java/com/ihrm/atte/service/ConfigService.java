package com.ihrm.atte.service;

import com.ihrm.atte.dao.*;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.attendance.entity.*;
import com.ihrm.domain.attendance.vo.ExtraDutyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService {

    @Autowired
    private AttendanceConfigDao attendanceConfigDao;

    @Autowired
    private LeaveConfigDao leaveConfigDao;

    @Autowired
    private DeductionDictDao deductionDictDao;

    @Autowired
    private ExtraDutyConfigDao extraDutyConfigDao;

    @Autowired
    private ExtraDutyRuleDao extraDutyRuleDao;

    @Autowired
    private DayOffConfigDao dayOffConfigDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存或更新考勤设置
     */
    public void saveAtteConfig(AttendanceConfig attendanceConfig) {
        //查询是否存在对应的考勤记录
        AttendanceConfig temp = attendanceConfigDao.findByCompanyIdAndDepartmentId(attendanceConfig.getCompanyId(), attendanceConfig.getDepartmentId());
        //存在，更新
        if (temp != null){
            attendanceConfig.setId(temp.getId());
        }else {
            //不存在，设置id，保存
            attendanceConfig.setId(idWorker.nextId()+"");
        }
        attendanceConfigDao.save(attendanceConfig);
    }

    /**
     * 查询考勤设置
     */
    public AttendanceConfig getAtteConfig(String companyId, String departmentId) {
        return attendanceConfigDao.findByCompanyIdAndDepartmentId(companyId,departmentId);
    }

    /**
     * 保存或更新请假设置
     */
    public void saveLeaveConfig(LeaveConfig leaveConfig) {
        LeaveConfig temp = leaveConfigDao.findByCompanyIdAndDepartmentIdAndLeaveType(leaveConfig.getCompanyId(), leaveConfig.getDepartmentId(), leaveConfig.getLeaveType());
        if (temp != null){
            leaveConfig.setId(temp.getId());
        }else {
            leaveConfig.setId(idWorker.nextId()+"");
        }
        leaveConfigDao.save(leaveConfig);
    }

    /**
     * 查询单个请假设置
     */
    public LeaveConfig findOneLeaveConfig(String companyId, String departmentId, String leaveType) {
        return leaveConfigDao.findByCompanyIdAndDepartmentIdAndLeaveType(companyId, departmentId, leaveType);
    }

    /**
     * 查询请假设置
     */
    public List<LeaveConfig> getLeaveConfig(String companyId, String departmentId) {
        return leaveConfigDao.findByCompanyIdAndDepartmentId(companyId, departmentId);
    }

    /**
     * 保存或更新扣款设置
     */
    public void saveDeductionConfig(DeductionDict deductionDict) {
        DeductionDict temp = deductionDictDao.findByCompanyIdAndDepartmentIdAndDedTypeCode(deductionDict.getCompanyId(), deductionDict.getDepartmentId(), deductionDict.getDedTypeCode());
        if (temp != null){
            deductionDict.setId(temp.getId());
        }else {
            deductionDict.setId(idWorker.nextId()+"");
        }
        deductionDictDao.save(deductionDict);
    }

    /**
     * 查询单个扣款设置
     */
    public DeductionDict findOneDeductionConfig(String companyId, String departmentId, String dedTypeCode) {
        return deductionDictDao.findByCompanyIdAndDepartmentIdAndDedTypeCode(companyId, departmentId, dedTypeCode);
    }

    /**
     * 获取扣款设置
     */
    public List<DeductionDict> getDeductionConfig(String companyId, String departmentId) {
        return deductionDictDao.findByCompanyIdAndDepartmentId(companyId, departmentId);
    }

    /**
     * 保存或更新加班设置
     */
    public void saveExtraDutyConfig(ExtraDutyVo extraDutyVo) {
        //保存加班配置表
        ExtraDutyConfig extraDutyConfig = extraDutyConfigDao.findByCompanyIdAndDepartmentId(extraDutyVo.getCompanyId(), extraDutyVo.getDepartmentId());
        ExtraDutyConfig configTemp = new ExtraDutyConfig();
        if (extraDutyConfig != null){
            configTemp.setId(extraDutyConfig.getId());
        }else {
            configTemp.setId(idWorker.nextId()+"");
        }
        configTemp.setCompanyId(extraDutyVo.getCompanyId());
        configTemp.setDepartmentId(extraDutyVo.getDepartmentId());
        configTemp.setWorkHoursDay(extraDutyVo.getWorkHoursDay());
        configTemp.setIsClock(extraDutyVo.getIsClock());
        configTemp.setIsCompensation(extraDutyVo.getIsCompensation());
        extraDutyConfigDao.save(configTemp);

        //保存加班规则表
        List<ExtraDutyRule> extraDutyRuleList = extraDutyRuleDao.findByCompanyIdAndDepartmentId(extraDutyVo.getCompanyId(), extraDutyVo.getDepartmentId());
        int index = 0;
        for (ExtraDutyRule extraDutyRule : extraDutyRuleList){
            ExtraDutyRule ruleTemp = new ExtraDutyRule();
            List<ExtraDutyRule> ruleListTemp = extraDutyVo.getRules();
            if (extraDutyRule != null){
                ruleTemp.setId(extraDutyRule.getId());
            }else {
                ruleTemp.setId(idWorker.nextId()+"");
            }
            ruleTemp.setExtraDutyConfigId(configTemp.getId());
            ruleTemp.setCompanyId(extraDutyVo.getCompanyId());
            ruleTemp.setDepartmentId(extraDutyVo.getDepartmentId());
            ruleTemp.setRule(ruleListTemp.get(index).getRule());
            ruleTemp.setRuleStartTime(ruleListTemp.get(index).getRuleStartTime());
            ruleTemp.setRuleEndTime(ruleListTemp.get(index).getRuleEndTime());
            ruleTemp.setIsEnable(ruleListTemp.get(index).getIsEnable());
            ruleTemp.setIsTimeOff(ruleListTemp.get(index).getIsTimeOff());
            index++;
            extraDutyRuleDao.save(ruleTemp);
        }

        //保存调休配置表
        DayOffConfig dayOffConfig = dayOffConfigDao.findByCompanyIdAndDepartmentId(extraDutyVo.getCompanyId(), extraDutyVo.getDepartmentId());
        DayOffConfig offTemp = new DayOffConfig();
        if (dayOffConfig != null){
            offTemp.setId(dayOffConfig.getId());
        }else {
            offTemp.setId(idWorker.nextId()+"");
        }
        offTemp.setCompanyId(extraDutyVo.getCompanyId());
        offTemp.setDepartmentId(extraDutyVo.getDepartmentId());
        offTemp.setLatestEffectDate(extraDutyVo.getLatestEffectDate());
        offTemp.setUnit(extraDutyVo.getUnit());
        dayOffConfigDao.save(offTemp);
    }

    /**
     * 获取加班设置
     */
    public Map getExtraDutyConfig(String companyId, String departmentId) {
        Map extraDutyMap = new HashMap();
        DayOffConfig dayOffConfig = dayOffConfigDao.findByCompanyIdAndDepartmentId(companyId, departmentId);
        extraDutyMap.put("dayOffConfigs",dayOffConfig);
        ExtraDutyConfig extraDutyConfig = extraDutyConfigDao.findByCompanyIdAndDepartmentId(companyId, departmentId);
        extraDutyMap.put("extraDutyConfig",extraDutyConfig);
        List<ExtraDutyRule> extraDutyRuleList = extraDutyRuleDao.findByCompanyIdAndDepartmentId(companyId, departmentId);
        extraDutyMap.put("extraDutyRuleList",extraDutyRuleList);
        return extraDutyMap;
    }

}
