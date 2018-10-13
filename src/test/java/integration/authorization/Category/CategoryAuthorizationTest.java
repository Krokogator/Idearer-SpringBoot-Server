package integration.authorization.Category;

import com.krokogator.spring.resources.category.Category;
import integration.authorization.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryAnonymousTest.class,
        CategoryUserTest.class,
        CategoryAdminTest.class
})
public abstract class CategoryAuthorizationTest extends IntegrationTest {

    private final String endpoint = "/categories";

    public ResultActions getAllCategories() throws Exception {
        return mockMvc.perform(
                get(endpoint));
    }

    public ResultActions createCategory() throws Exception {
        Category category = new Category();
        category.setName("PostCategory");

        return mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        );

    }

    public ResultActions updateCategory() throws Exception {
        Category category = new Category();
        category.setName("Updated Category");

        return mockMvc.perform(
                patch(endpoint + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category))
        );
    }

    public ResultActions deleteCategory() throws Exception {
        return mockMvc.perform(
                delete(endpoint + "/4")
        );
    }


}
