package integration.authorization.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CategoryUserTest extends CategoryAuthorizationTest {

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenGetCategories_thenOk() throws Exception {
        getAllCategories().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenPostCategory_thenForbidden() throws Exception {
        createCategory().andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenUpdateCategory_thenForbidden() throws Exception {
        updateCategory().andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_whenDeleteCategory_thenForbidden() throws Exception {
        deleteCategory().andExpect(
                status().isForbidden()
        );
    }
}
