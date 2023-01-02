import com.ihrm.company.CompanyApplication;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = CompanyApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testAdd(){
        Company company = companyService.findById("1");
        System.out.println(company);
    }

    @Test
    public void testAdd2(){
        Department department = new Department();
        department.setCompanyId("1");
        department.setDepartmentName("英语");
        departmentService.save(department);
    }

    @Test
    public void testUpdate(){
        Company company = companyService.findById("1");
        company.setName("公寓");
        companyService.update(company);
    }

    @Test
    public void testDelete(){
        companyService.deleteById("1481918699440824320");
    }

    @Test
    public void testFindById(){
        Company company = companyService.findById("1");
        System.out.println(company);
    }

    @Test
    public void testFindAll(){
        List<Company> companys = companyService.findAll();
        for(Company company : companys){
            System.out.println(company);
        }

    }
}
