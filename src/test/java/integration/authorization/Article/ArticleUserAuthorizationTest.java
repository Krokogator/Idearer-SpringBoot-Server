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
    public void givenUser_whenPatchOwnedArticleStatusOtherThanRejected_thenForbidden() throws Exception {
        patchArticleTitle(2).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenPatchOwnedArticleStatusRejected_thenNoContent() throws Exception {
        patchArticleTitle(12).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenPatchNotOwnedArticle_thenForbidden() throws Exception {
        patchArticleTitle(1).andExpect(
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

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenPatchNotOwnedArticleStatusToACCEPTED_thenForbidden() throws Exception {
        patchArticleStatusToACCEPTED(1).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_whenPatchOwnedArticleStatusToACCEPTED_thenForbidden() throws Exception {
        patchArticleStatusToACCEPTED(2).andExpect(
                status().isForbidden()
        );
    }
    
}
