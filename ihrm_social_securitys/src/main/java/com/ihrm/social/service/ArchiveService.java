package com.ihrm.social.service;

import com.alibaba.fastjson.JSON;
import com.ihrm.common.entity.Result;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.employee.UserCompanyPersonal;
import com.ihrm.domain.social_securitys.Archive;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import com.ihrm.domain.social_securitys.CityPaymentItem;
import com.ihrm.domain.social_securitys.UserSocialSecurity;
import com.ihrm.social.client.EmployeeFeignClient;
import com.ihrm.social.dao.ArchiveDao;
import com.ihrm.social.dao.ArchiveDetailDao;
import com.ihrm.social.dao.UserSocialSecurityDao;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArchiveService {
    @Autowired
    private ArchiveDao archiveDao;

    @Autowired
    private ArchiveDetailDao archiveDetailDao;

    @Autowired
    private UserSocialSecurityDao userSocialSecurityDao;

    @Autowired
    private EmployeeFeignClient employeeFeignClient;

    @Autowired
    private UserSocialSecurityService userSocialSecurityService;

    @Autowired
    private PaymentItemService paymentItemService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据企业id和年月查询归档历史
     */
    public Archive findArchive(String companyId, String yearsMonth) {
        return archiveDao.findByCompanyIdAndAndYearsMonth(companyId, yearsMonth);
    }

    /**
     * 根据归档id查询详细
     */
    public List<ArchiveDetail> findAllDetailsByArchiveId(String id) {
        return archiveDetailDao.findByArchiveId(id);
    }

    /**
     * 对未归档数据进行归档
     */
    public List<ArchiveDetail> getReports(String companyId, String yearsMonth) {
        Page<Map> userSocialSecurityItemPage = userSocialSecurityDao.findPage(companyId,null);
        List<ArchiveDetail> detailList = new ArrayList<>();
        for (Map map : userSocialSecurityItemPage){
            ArchiveDetail detail = new ArchiveDetail();
            detail.setUserId((String) map.get("userId"));
            detail.setUsername((String) map.get("username"));
            detail.setMobile((String) map.get("mobile"));
            detail.setDepartmentName((String) map.get("departmentName"));
            detail.setEntryTime((Date) map.get("entryTime"));
            //获取个人信息
            Result personalResult = employeeFeignClient.findPersonalInfo(detail.getUserId());
            if (personalResult.isSuccess()){
                UserCompanyPersonal userCompanyPersonal = JSON.parseObject(JSON.toJSONString(personalResult.getData()),UserCompanyPersonal.class);
                detail.setUserCompanyPersonal(userCompanyPersonal);
            }
            getSocialSecurity(detail,yearsMonth);
            detailList.add(detail);
        }
        return detailList;
    }

    /**
     * 对社保和公积金数据进行计算
     */
    public void getSocialSecurity(ArchiveDetail detail,String yearsMonth){
        UserSocialSecurity userSocialSecurity = userSocialSecurityService.findById(detail.getUserId());
        if (userSocialSecurity == null){
            return;
        }
        BigDecimal socialSecurityCompanyPay = new BigDecimal(0);
        BigDecimal socialSecurityPersonalPay = new BigDecimal(0);
        List<CityPaymentItem> cityPaymentItemList = paymentItemService.findAllByCityId(userSocialSecurity.getProvidentFundCityId());
        for (CityPaymentItem cityPaymentItem : cityPaymentItemList){
            //计算公司社保金额
            if (cityPaymentItem.getSwitchCompany()){
                BigDecimal socialSecurityCompany = cityPaymentItem.getScaleCompany().multiply(userSocialSecurity.getSocialSecurityBase());
                BigDecimal divideCompany = socialSecurityCompany.divide(new BigDecimal(100));
                socialSecurityCompanyPay = socialSecurityCompanyPay.add(divideCompany);
            }
            //计算个人社保金额
            if (cityPaymentItem.getSwitchPersonal()){
                BigDecimal socialSecurityPersonal = cityPaymentItem.getScalePersonal().multiply(userSocialSecurity.getSocialSecurityBase());
                BigDecimal dividePersonal = socialSecurityPersonal.divide(new BigDecimal(100));
                socialSecurityPersonalPay = socialSecurityPersonalPay.add(dividePersonal);
            }
        }
        detail.setSocialSecurity(socialSecurityCompanyPay.add(socialSecurityPersonalPay));
        detail.setSocialSecurityEnterprise(socialSecurityCompanyPay);
        detail.setSocialSecurityIndividual(socialSecurityPersonalPay);
        detail.setUserSocialSecurity(userSocialSecurity);
        detail.setSocialSecurityMonth(yearsMonth);
        detail.setProvidentFundMonth(yearsMonth);
        detail.setYearsMonth(yearsMonth);
    }

    /**
     * 社保数据进行归档
     */
    public void archive(String yearsMonth, String companyId) {
        /**
         * 对归档进行保存
         */
        //查询当月是否已经归档
        Archive archive = findArchive(companyId,yearsMonth);
        //若不存在，新增历史档案
        if (archive == null){
            archive = new Archive();
            archive.setCompanyId(companyId);
            archive.setYearsMonth(yearsMonth);
            archive.setId(idWorker.nextId()+"");
        }
        //对新增和已存在进行统一操作
        List<ArchiveDetail> archiveDetailList = getReports(companyId,yearsMonth);
        BigDecimal enterprisePayment = new BigDecimal(0);
        BigDecimal personalPayment = new BigDecimal(0);
        //社保金额加公积金金额
        for (ArchiveDetail archiveDetail : archiveDetailList){
            //直接返回null会报错
            BigDecimal providentFundEnterprises = archiveDetail.getProvidentFundEnterprises() == null ? new BigDecimal(0) : archiveDetail.getProvidentFundEnterprises();
            BigDecimal socialSecurityEnterprise = archiveDetail.getSocialSecurityEnterprise() == null ? new BigDecimal(0) : archiveDetail.getSocialSecurityEnterprise();
            BigDecimal providentFundIndividual = archiveDetail.getProvidentFundIndividual() == null ? new BigDecimal(0) : archiveDetail.getProvidentFundIndividual();
            BigDecimal socialSecurityIndividual = archiveDetail.getSocialSecurityIndividual() == null ? new BigDecimal(0) : archiveDetail.getSocialSecurityIndividual();

            enterprisePayment = enterprisePayment.add(providentFundEnterprises).add(socialSecurityEnterprise);
            personalPayment = personalPayment.add(providentFundIndividual).add(socialSecurityIndividual);
        }
        archive.setEnterprisePayment(enterprisePayment);
        archive.setPersonalPayment(personalPayment);
        archive.setTotal(enterprisePayment.add(personalPayment));
        archive.setCreationTime(new Date());
        archiveDao.save(archive);

        /**
         * 对归档明细进行保存
         */
        for (ArchiveDetail archiveDetail : archiveDetailList){
            archiveDetail.setId(idWorker.nextId()+"");
            archiveDetail.setArchiveId(archive.getId());
            archiveDetailDao.save(archiveDetail);
        }
    }

    /**
     * 通过年份查询一整年的历史归档
     */
    public List<Archive> findArchiveByYear(String companyId, String year) {
        return archiveDao.findByCompanyIdAndYearsMonthLike(companyId,year+"%");
    }

    /**
     * 根据用户id和考勤年月查询用户考勤归档明细
     */
    public ArchiveDetail findUserArchiveDetail(String userId, String yearsMonth) {
        return archiveDetailDao.findByUserIdAndYearsMonth(userId,yearsMonth);
    }
}
