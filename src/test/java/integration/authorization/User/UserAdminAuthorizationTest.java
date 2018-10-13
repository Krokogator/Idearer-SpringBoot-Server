package integration.authorization.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserAdminAuthorizationTest extends UserAuthorizationTest{

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenGetUser_thenOk() throws Exception {
        super.getUser().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenGetAllUsers_thenOk() throws Exception {
        super.getAllUsers().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenRegisterUser_thenCreated() throws Exception {
        super.registerUser().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenPatchDifferentUser_thenNoContent() throws Exception {
        super.patchUser(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenPatchCurrentUser_thenNoContent() throws Exception {
        super.patchUser(1).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenLogin_thenOk() throws Exception {
        super.loginUser().andExpect(
                status().isOk()
        );
    }

}
