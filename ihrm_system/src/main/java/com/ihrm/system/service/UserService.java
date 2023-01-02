package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.QiniuUploadUtils;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.client.DepartmentFeignClient;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DepartmentFeignClient departmentFeignClient;

    /**
     * 保存用户
     */
    public void save(User user){
        String id = idWorker.nextId()+"";
        String password = new Md5Hash("123456",user.getMobile(),3).toString();
        user.setLevel("user");
        user.setId(id);
        user.setPassword(password); //设置初始密码
        user.setEnableState(1);
        user.setCreateTime(new Date());
        userDao.save(user);
    }

    /**
     * 批量保存用户
     */
    @Transactional
    public void saveAll(List<User> userList, String companyId, String companyName) {
        for (User user : userList){
            user.setPassword(new Md5Hash("123456",user.getMobile(),3).toString());
            user.setId(idWorker.nextId()+"");
            user.setCompanyId(companyId);
            user.setCompanyName(companyName);
            user.setInServiceStatus(1);
            user.setEnableState(1);
            user.setLevel("user");

            //跨微服务
            Department department = departmentFeignClient.findByCode(user.getDepartmentId(),companyId);
            if (department != null){
                user.setDepartmentId(department.getId());
                user.setDepartmentName(department.getDepartmentName());
            }

            userDao.save(user);
        }
    }

    /**
     * 更新用户
     */
    public void update(User user){
        User temp = userDao.findById(user.getId()).get();
        temp.setUsername(user.getUsername());
        temp.setPassword(user.getPassword());
        temp.setDepartmentId(user.getDepartmentId());
        temp.setDepartmentName(user.getDepartmentName());
        temp.setEmploymentForm(user.getEmploymentForm());
        userDao.save(temp);
    }

    /**
     * 根据id删除用户
     */
    public void deleteById(String id){
        userDao.deleteById(id);
    }

    /**
     * 根据id查询用户
     */
    public User findById(String id){
        return userDao.findById(id).get();
    }

    /**
     * 根据mobile查询用户
     */
    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }

    /**
     * 根据companyId返回所有用户
     */
    public List<User> findAll(String companyId){
        return userDao.findAll(getSpec(companyId));
    }

    /**
     * 查询所有用户
     *  参数：map集合
     *      1.hasDept
     *      2.departmentId
     *      3.companyId
     */
    public Page findAll(Map<String,Object> map, int page, int size){
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的companyId构造查询条件
                if (!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(cb.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                //根据请求的departmentId构造查询条件
                if (!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(cb.equal(root.get("departmentId").as(String.class),(String)map.get("departmentId")));
                }
                //根据请求的hasDept构造查询条件
                if (!StringUtils.isEmpty(map.get("hasDept"))){
                    if ("0".equals((String)map.get("hasDept"))){
                        list.add(cb.isNull(root.get("departmentId")));
                    }else {
                        list.add(cb.isNotNull(root.get("departmentId")));
                    }
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //分页
        Page<User> userPage = userDao.findAll(spec, PageRequest.of(page-1, size));
        return userPage;
    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId, List<String> roleIds) {
        //根据id查询用户
        User user = userDao.findById(userId).get();
        //构造用户的角色集合
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds){
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和角色的关系
        user.setRoles(roles);
        //更新用户
        userDao.save(user);
    }

    /**
     * 完成图片处理
     * @param id  ：用户id
     * @param file  ：用户上传的头像文件
     * @return  ：请求路径
     */
    public String uploadImage(String id, MultipartFile file) throws IOException {
        //1.根据id查询用户
        User user = userDao.findById(id).get();
        //2.使用DataURL的形式存储图片（对图片byte数据进行base64编码）
        String encode = "data:image/png;base64,"+Base64.encode(file.getBytes());
        System.out.println(encode);
        //3.更新用户头像地址
        user.setStaffPhoto(encode);
        userDao.save(user);
        //4.返回图片地址
        return encode;
    }

    /**
     * 上传到七牛云存储
     * @param id
     * @param file
     * @return
     * @throws IOException
     */
//    public String uploadImage(String id, MultipartFile file) throws IOException {
//        //1.根据id查询用户
//        User user = userDao.findById(id).get();
//        //2.将图片上传到七牛云存储，获取到请求路径
//        String imgUrl = new QiniuUploadUtils().upload(user.getId(), file.getBytes()); //上传图片名称
//        //3.更新用户头像地址
//        user.setStaffPhoto(imgUrl);
//        userDao.save(user);
//        //4.返回图片地址
//        return imgUrl;
//    }

    /**
     * 根据用户名查询用户id
     * @param username
     * @return
     */
    public User findByName(String username) {
        return userDao.findByUsername(username);
    }
}
