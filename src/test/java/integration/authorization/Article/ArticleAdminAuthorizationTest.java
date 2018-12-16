package integration.authorization.Article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ArticleAdminAuthorizationTest extends ArticleAuthorizationTest {

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenGetArticle_thenOk() throws Exception {
        getArticle().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenGetArticles_thenOk() throws Exception {
        getAllArticles().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenCreateArticle_thenCreated() throws Exception {
        createArticle().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenPatchOwnedArticle_thenNoContent() throws Exception {
        patchArticleTitle(1).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenPatchNotOwnedArticle_thenNoContent() throws Exception {
        patchArticleTitle(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenDeleteOwnedArticle_thenNoContent() throws Exception {
        deleteArticle(1).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenDeleteNotOwnedArticle_thenNoContent() throws Exception {
        deleteArticle(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenPatchOwnedArticleStatusToACCEPTED_thenNoContent() throws Exception {
        patchArticleStatusToACCEPTED(1).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_whenPatchNotOwnedArticleStatusToACCEPTED_thenNoContent() throws Exception {
        patchArticleStatusToACCEPTED(2).andExpect(
                status().isNoContent()
        );
    }
}