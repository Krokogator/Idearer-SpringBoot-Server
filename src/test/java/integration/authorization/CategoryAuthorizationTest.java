package integration.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krokogator.spring.resources.category.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CategoryAuthorizationTest extends AuthorizationTests {

    private final String endpoint = "/categories";

    // ANONYMOUS

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetCategories_thenOk() throws Exception {
        mockMvc.perform(
                get(endpoint))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenPostCategory_thenUnauthorized() throws Exception {
        Category category = new Category();
        category.setName("PostCategory");

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        )
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenUpdateCategory_thenUnauthorized() throws Exception {
        Category category = new Category();
        category.setName("Updated Category");

        mockMvc.perform(
                patch(endpoint + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenDeleteCategory_thenUnauthorized() throws Exception {
        mockMvc.perform(
                delete(endpoint + "/4")
        )
                .andExpect(status().isUnauthorized());
    }

    // USER

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenGetCategories_thenOk() throws Exception {
        mockMvc.perform(
                get(endpoint))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenPostCategory_thenForbidden() throws Exception {
        Category category = new Category();
        category.setName("PostCategory");

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        )
            .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenUpdateCategory_thenForbidden() throws Exception {
        Category category = new Category();
        category.setName("Updated Category");

        mockMvc.perform(
                patch(endpoint + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenDeleteCategory_thenForbidden() throws Exception {
        mockMvc.perform(
                delete(endpoint + "/4")
        )
                .andExpect(status().isForbidden());
    }

    // ADMIN

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenGetCategories_thenOk() throws Exception {
        mockMvc.perform(
                get(endpoint)
        )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenPostCategory_thenCreated() throws Exception {
        Category category = new Category();
        category.setName("PostCategory");

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        )
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenUpdateCategory_thenOk() throws Exception {
        Category category = new Category();
        category.setName("Updated Category");

        mockMvc.perform(
                patch(endpoint + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenDeleteCategory_thenOk() throws Exception {
        mockMvc.perform(
                delete(endpoint + "/4")
        )
                .andExpect(status().isOk());
    }
}
