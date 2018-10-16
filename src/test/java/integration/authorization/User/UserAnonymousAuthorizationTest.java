package integration.authorization.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserAnonymousAuthorizationTest extends UserAuthorizationTest {

    private final String endpoint = "/users";

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetUser_thenOk() throws Exception {
        getUser().andExpect(
                status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetAllUsers_thenOk() throws Exception {
        getAllUsers().andExpect(
                status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenRegisterUser_thenCreated() throws Exception {
        registerUser().andExpect(
                status().isCreated());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenPatchUser_thenUnauthorized() throws Exception {
        patchUser(2).andExpect(
                status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenLogin_thenUnauthorized() throws Exception {
        loginUser().andExpect(
                status().isUnauthorized());
    }
}
