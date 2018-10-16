package integration.authorization.User;

import com.krokogator.spring.resources.user.dto.PatchUserDTO;
import com.krokogator.spring.resources.user.dto.PostUserDTO;
import integration.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserAnonymousAuthorizationTest.class,
        UserUserAuthorizationTest.class,
        UserAdminAuthorizationTest.class
})
public class UserAuthorizationTest extends IntegrationTest {

    private final String endpoint = "/users";

    public ResultActions getUser() throws Exception {
        return mockMvc.perform(
                get(endpoint + "/2"));
    }

    public ResultActions getAllUsers() throws Exception {
        return mockMvc.perform(
                get(endpoint));
    }

    public ResultActions registerUser() throws Exception {
        PostUserDTO postUserDTO = new PostUserDTO();
        postUserDTO.username = "User5395203";
        postUserDTO.email = "Email21025912@gmail.com";
        postUserDTO.password = "adwawd124055";
        return mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postUserDTO)));
    }

    public ResultActions patchUser(int id) throws Exception {
        PatchUserDTO patchUserDTO = new PatchUserDTO();
        patchUserDTO.email = "email2414215@gmail.com";
        return mockMvc.perform(
                patch(endpoint + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patchUserDTO)));
    }

    public ResultActions loginUser() throws Exception {
        return mockMvc.perform(
                post(endpoint + "/login"));
    }
}
