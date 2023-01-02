import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.RoleService;
import com.ihrm.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    public void testAdd(){
        User user = new User();
        user.setMobile("1");
        user.setCompanyId("555");
        user.setUsername("nainai");
        userService.save(user);
    }

    @Test
    public void testAdd2(){
        Role role = new Role();
        role.setName("主管");
        role.setDescription("无法无天");
        role.setCompanyId("1");
        roleService.save(role);
    }

    @Test
    public void testFindById(){
        User user = userService.findById("1491612686423785472");
        System.out.println(user);
    }
}
