package integration.authorization.Category;

import com.krokogator.spring.resources.category.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CategoryAnonymousTest extends CategoryAuthorizationTest {

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetCategories_thenOk() throws Exception {
        getAllCategories().andExpect(
                status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenPostCategory_thenUnauthorized() throws Exception {
        createCategory().andExpect(
                status().isUnauthorized());

    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenUpdateCategory_thenUnauthorized() throws Exception {
        updateCategory().andExpect(
                status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenDeleteCategory_thenUnauthorized() throws Exception {
        deleteCategory().andExpect(
                status().isUnauthorized());
    }


}
