package com.ihrm.atte.service;

import com.ihrm.atte.dao.AttendanceDao;
import com.ihrm.atte.dao.CompanySettingsDao;
import com.ihrm.atte.dao.UserDao;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.utils.DateUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.attendance.entity.Attendance;
import com.ihrm.domain.attendance.entity.CompanySettings;
import com.ihrm.domain.attendance.vo.AtteItemVo;
import com.ihrm.domain.system.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {

    @Autowired
    private CompanySettingsDao companySettingsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 获取用户的考勤数据
     *      1.考勤月
     *      2.分页
     *      3.查询
     */
    public Map getAtteData(String companyId, int page, int size) throws ParseException {
        //获得考勤月
        CompanySettings settings= companySettingsDao.findById(companyId).get();
        String dataMonth = settings.getDataMonth();
        //分页查询用户
        Page<User> userPage = userDao.findPage(companyId,PageRequest.of(page-1,size));
        //获取每个用户的考勤情况
        List<AtteItemVo> list = new ArrayList<>();
        for (User user : userPage.getContent()){
            AtteItemVo vo = new AtteItemVo();
            BeanUtils.copyProperties(user,vo);
            List<Attendance> attendanceRecord = new ArrayList<>();
            //获取当前月所有的天数
            String[] days = DateUtils.getDaysByYearMonth(dataMonth);
            //循环每天查询考勤记录
            for (String day : days){
                Attendance attendance = attendanceDao.findByUserIdAndDay(user.getId(),day);
                if (attendance == null){
                    attendance = new Attendance(user);
                    attendance.setAdtStatus(21); //不存在考勤记录
                    attendance.setDay(day);
                    attendance.setId(idWorker.nextId()+"");
                }
                System.out.println(user);
                System.out.println(attendance);
                attendanceRecord.add(attendance);
            }
            //封装到attendanceRecord
            vo.setAttendanceRecord(attendanceRecord);
            list.add(vo);
        }
        Map map = new HashMap();
        //分页对象
        PageResult result = new PageResult(userPage.getTotalElements(),list);
        map.put("data",result);
        //待处理的考勤数量
        map.put("tobeTaskCount",0);
        //当前考勤月份
        map.put("monthOfReport",Integer.parseInt(dataMonth.substring(4)));
        return map;
    }

    /**
     * 更新考勤数据
     */
    public void updateAtteData(Attendance attendance, String companyId) {
        Attendance temp = attendanceDao.findByUserIdAndDay(attendance.getId(), attendance.getDay());
        if (temp == null){
            attendance.setId(idWorker.nextId()+"");
        }else {
            attendance.setId(temp.getId());
        }
        attendance.setCompanyId(companyId);
        attendanceDao.save(attendance);
    }
}
