package integration.authorization.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserUserAuthorizationTest extends UserAuthorizationTest {

    private final String endpoint = "/users";

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenGetUser_thenOk() throws Exception {
        super.getUser().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenGetAllUsers_thenOk() throws Exception {
        super.getUser().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenRegisterUser_thenCreated() throws Exception {
        super.registerUser().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenPatchDifferentUser_thenForbidden() throws Exception {
        super.patchUser(1).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenPatchCurrentUser_thenNoContent() throws Exception {
        super.patchUser(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenLogin_thenOk() throws Exception {
        super.loginUser().andExpect(
                status().isOk()
        );
    }
}
