package integration.authorization.Category;

import com.krokogator.spring.resources.category.Category;
import integration.authorization.AuthorizationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CategoryAdminTest extends AuthorizationTests {

    private final String endpoint = "/categories";

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
