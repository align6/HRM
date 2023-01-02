package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.poi.ExcelImportUtil;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.domain.system.response.UserSimpleResult;
import com.ihrm.system.client.DepartmentFeignClient;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DepartmentFeignClient departmentFeignClient;

    /**
     * 测试Feign组件
     * 调用系统微服务的/test接口传递部门id,通过feign调用部门微服务中的根据id查询
     */
    @GetMapping("/test/{id}")
    public Result testFeign(@PathVariable String id){
        Result result = departmentFeignClient.findById(id);
        return result;
    }

    /**
     *新增用户
     */
    @PostMapping("/user")
    public Result save(@RequestBody User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 更新用户
     */
    @PutMapping("/user/{id}")
    public Result update(@PathVariable String id, @RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除角色
     */
    @RequiresPermissions(value = "API-USER-DELETE")
    @DeleteMapping(value = "/user/{id}", name = "API-USER-DELETE")
    public Result delete(@PathVariable String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询用户
     */
    @GetMapping("/user/{id}")
    public Result findById(@PathVariable String id){
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    /**
     * 根据用户名查询用户id
     */
    @GetMapping("/user/name")
    public String findIdByName(String username){
        User user = userService.findByName(username);
        return user.getUsername();
    }

    /**
     * 返回简单用户列表: 仅包含id和username
     */
    @GetMapping("/user/simple")
    public Result simple(){
        List<UserSimpleResult> simpleList = new ArrayList<>();
        List<User> users = userService.findAll(companyId);
        for (User user : users){
            simpleList.add(new UserSimpleResult(user.getId(),user.getUsername()));
        }
        return new Result(ResultCode.SUCCESS,simpleList);
    }

    /**
     * 查询所有列表
     */
    @GetMapping("/user")
    public Result findAll(int page, int size, @RequestParam Map map){
        map.put("companyId",companyId);
        Page<User> userPage = userService.findAll(map, page, size);
        PageResult pageResult = new PageResult(userPage.getTotalElements(),userPage.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 分配角色
     */
    @PutMapping("/user/assignRoles")
    public Result assignRoles(@RequestBody Map<String,Object> map){
        //获取被分配的用户id
        String userId = (String) map.get("id");
        //获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //调用service完成角色分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        //1.构造登录令牌 UsernameAndPasswordToken
        try {
            password = new Md5Hash(password,mobile,3).toString(); //密码，盐，加密次数
            UsernamePasswordToken token = new UsernamePasswordToken(mobile,password);
            //2.获取subject
            Subject subject = SecurityUtils.getSubject();
            //3.调用login方法，进入realm完成认证
            subject.login(token);
            //4.获取sessionId
            String sessionId = subject.getSession().getId().toString();
            //5.构造返回结果
            return new Result(ResultCode.SUCCESS,sessionId);
        }catch (Exception e){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }

        /**
         * 通过jwt登录
         */
//        //通过service根据mobile查询用户
//        User user = userService.findByMobile(mobile);
//        //检查user和password
//        if (user == null || !user.getPassword().equals(password)){
//            return new Result(ResultCode.MOBILEORPASSWORDERROR);
//        }else {
//        //api权限字符串
//            StringBuilder sb = new StringBuilder();
//        //获取到所有的可访问api权限
//            for (Role role : user.getRoles()){
//                for (Permission permission : role.getPermissions()){
//                    if (permission.getType() == PermissionConstants.API){
//                        sb.append(permission.getCode()).append(",");
//                    }
//                }
//            }
//        //生成token
//            Map<String,Object> map = new HashMap<>();
//            map.put("apis",sb.toString());
//            map.put("companyId",user.getCompanyId());
//            map.put("companyName",user.getCompanyName());
//            String token = jwtUtils.createJwt(user.getId(),user.getUsername(),map);
//            return new Result(ResultCode.SUCCESS,token);
    }

    /**
     * 用户登录成功后，获取个人信息
     */
    @PostMapping("/profile")
    public Result profile() throws Exception {
        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //subject获取所有的安全数据集合
        PrincipalCollection profileResult = subject.getPrincipals();
        //获取安全数据
        ProfileResult result = (ProfileResult) profileResult.getPrimaryPrincipal();

        return new Result(ResultCode.SUCCESS,result);
//        //获取用户id
//        String userId = claims.getId();
//        //根据用户id查询用户
//        User user = userService.findById(userId);
//        ProfileResult profileResult = null;
//        //根据不同的用户级别获取用户权限
//        if ("user".equals(user.getLevel())){
//            profileResult = new ProfileResult(user);
//        }else {
//            Map map = new HashMap();
//            if ("coAdmin".equals(user.getLevel())){
//                map.put("enVisible","1");
//            }
//            List<Permission> list = permissionService.findAll(map);
//            profileResult = new ProfileResult(user,list);
//        }
    }

    /**
     * 导入excel，添加用户
     *      文件上传
     */
    @PostMapping("/user/import")
    public Result importUser(@RequestParam(name = "file") MultipartFile file) throws Exception {
//        //1.解析excel
//        Workbook wb = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = wb.getSheetAt(0); //参数：从0开始
//        //2.获取用户列表
//        List<User> userList = new ArrayList<>();
//        for (int rowNum=1; rowNum<=sheet.getLastRowNum(); rowNum++){
//            Row row = sheet.getRow(rowNum); //根据索引获取每一行
//            Object[] values = new Object[row.getLastCellNum()];
//            for (int cellNum=1; cellNum<row.getLastCellNum(); cellNum++){
//                Cell cell = row.getCell(cellNum); //根据索引获取每一单元格
//                Object value = getCellValue(cell);
//                values[cellNum] = value;
//            }
//            User user = new User(values);
//            userList.add(user);
//        }

        List<User> list = new ExcelImportUtil(User.class).readExcel(file.getInputStream(), 1,1);

        //3.批量保存用户
        userService.saveAll(list,companyId,companyName);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 判断每一单元格的数据类型，该方法用于上面的import
     */
    public static Object getCellValue(Cell cell){
        //1.获取单元格数据类型
        CellType cellType = cell.getCellType();
        //2.根据数据类型获取数据
        Object value = null;
        switch (cellType){
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    //日期格式
                    value = cell.getDateCellValue();
                }else {
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 图片上传
     */
    @PostMapping("/user/upload/{id}")
    public Result upload(@PathVariable String id, @RequestParam(name = "file")MultipartFile file) throws IOException {
        //1.调用service保存图片(获取图片的访问地址)
        String imgUrl = userService.uploadImage(id, file);
        //2.返回数据
        return new Result(ResultCode.SUCCESS,imgUrl);
    }
}
