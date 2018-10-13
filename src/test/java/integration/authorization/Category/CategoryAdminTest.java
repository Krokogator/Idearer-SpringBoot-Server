package integration.authorization.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CategoryAdminTest extends CategoryAuthorizationTest {

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenGetCategories_thenOk() throws Exception {
        getAllCategories().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenPostCategory_thenCreated() throws Exception {
        createCategory().andExpect(
                status().isCreated()
        );

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenUpdateCategory_thenNoContent() throws Exception {
        updateCategory().andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_whenDeleteCategory_thenNoContent() throws Exception {
        deleteCategory().andExpect(
                status().isNoContent()
        );
    }
}
