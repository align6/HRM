package com.ihrm.atte.service;

import com.ihrm.atte.dao.AttendanceConfigDao;
import com.ihrm.atte.dao.AttendanceDao;
import com.ihrm.atte.dao.UserDao;
import com.ihrm.common.poi.ExcelImportUtil;
import com.ihrm.common.utils.DateUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.attendance.entity.Attendance;
import com.ihrm.domain.attendance.entity.AttendanceConfig;
import com.ihrm.domain.attendance.vo.AtteUploadVo;
import com.ihrm.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class ExcelImportService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AttendanceConfigDao attendanceConfigDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Value("${atte.holidays}")
    private String holidays;

    @Value("${atte.wordingDays}")
    private String specialDays;

    /**
     * 处理考勤数据的文件上传
     */
    public void importAttendanceExcel(MultipartFile file, String companyId) throws Exception {
        //将导入的excel文件解析为vo的list集合
        List<AtteUploadVo> list = new ExcelImportUtil<AtteUploadVo>(AtteUploadVo.class).readExcel(file.getInputStream(), 1, 0);
        //循环list集合
        for (AtteUploadVo atteUploadVo : list){
            //根据上传的手机号码查询用户
            User user = userDao.findByMobile(atteUploadVo.getMobile());
            //构造考勤对象
            Attendance attendance = new Attendance(atteUploadVo,user);
            //判断是否休假
            if (holidays.contains(atteUploadVo.getAtteDate()) || DateUtils.isWeekend(atteUploadVo.getAtteDate()) || specialDays.contains(atteUploadVo.getAtteDate())){
                attendance.setAdtStatus(23); //休息
            } else {
                //判断是否迟到、早退
                //查询当前员工部门的上下班时间
                AttendanceConfig attendanceConfig = attendanceConfigDao.findByCompanyIdAndDepartmentId(companyId,user.getDepartmentId());
                //比较上班时间是否晚于规定上班时间
                if (!DateUtils.comparingDate(attendanceConfig.getMorningStartTime(),atteUploadVo.getInTime())){
                    attendance.setAdtStatus(3); //迟到
                }
                //比较下班时间是否早于规定下班时间
                else if (DateUtils.comparingDate(attendanceConfig.getAfternoonEndTime(),atteUploadVo.getOutTime())){
                    attendance.setAdtStatus(4); //早退
                }
                else {
                    attendance.setAdtStatus(1); //正常
                }
            }
            Attendance isExist = attendanceDao.findByUserIdAndDay(user.getId(),atteUploadVo.getAtteDate());
            if (isExist == null){
                attendance.setId(idWorker.nextId()+"");
                attendanceDao.save(attendance);
            }
        }
    }
}
