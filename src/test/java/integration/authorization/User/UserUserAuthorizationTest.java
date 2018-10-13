package integration.authorization.User;

import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.SecureUser;
import com.krokogator.spring.resources.user.dto.PatchUserDTO;
import com.krokogator.spring.resources.user.dto.PostUserDTO;
import integration.authorization.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserUserAuthorizationTest extends UserAuthorizationTest {

    private final String endpoint = "/users";

    @Test
    @WithMockUser(roles = "USER")
    public void givenAnonymous_whenGetUser_thenOk() throws Exception {
        super.getUser().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenAnonymous_whenGetAllUsers_thenOk() throws Exception {
        super.getUser().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenAnonymous_whenRegisterUser_thenCreated() throws Exception {
        super.registerUser().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenAnonymous_whenPatchDifferentUser_thenForbidden() throws Exception {
        super.patchUser().andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenAnonymous_whenLogin_thenOk() throws Exception {
        super.loginUser().andExpect(
                status().isOk()
        );
    }
}
