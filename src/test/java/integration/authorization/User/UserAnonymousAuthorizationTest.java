package integration.authorization.User;

import com.krokogator.spring.resources.user.dto.PatchUserDTO;
import com.krokogator.spring.resources.user.dto.PostUserDTO;
import integration.authorization.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.annotation.WebInitParam;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserAnonymousAuthorizationTest extends IntegrationTest {

    private final String endpoint = "/users";

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetUser_thenOk() throws Exception {
        mockMvc.perform(
                get(endpoint + "/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetAllUsers_thenOk() throws Exception {
        mockMvc.perform(
                get(endpoint))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenRegisterUser_thenCreated() throws Exception {
        PostUserDTO postUserDTO = new PostUserDTO();
        postUserDTO.username = "User5395203";
        postUserDTO.email = "Email21025912@gmail.com";
        postUserDTO.password = "adwawd124055";
        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postUserDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenPatchUser_thenUnauthorized() throws Exception {
        PatchUserDTO patchUserDTO = new PatchUserDTO();
        patchUserDTO.email = "email2414215@gmail.com";
        mockMvc.perform(
                patch(endpoint + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patchUserDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenLogin_thenUnauthorized() throws Exception {
        mockMvc.perform(
                post(endpoint + "/login"))
                .andExpect(status().isUnauthorized());
    }
}
