package integration.authorization.Article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@RunWith(SpringRunner.class)
public class ArticleUserAuthorizationTest extends ArticleAuthorizationTest {

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenGetArticle_thenOk() throws Exception {
        getArticle().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenGetArticles_thenOk() throws Exception {
        getAllArticles().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenCreateArticle_thenCreated() throws Exception {
        createArticle().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenPatchOwnedArticle_thenNoContent() throws Exception {
        patchArticle(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenPatchNotOwnedArticle_thenForbidden() throws Exception {
        patchArticle(1).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenDeleteOwnedArticle_thenNoContent() throws Exception {
        deleteArticle(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenDeleteNotOwnedArticle_thenForbidden() throws Exception {
        deleteArticle(1).andExpect(
                status().isForbidden()
        );
    }
}
