package integration.authorization.Article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ArticleAnonymousAuthorizationTest extends ArticleAuthorizationTest {

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetArticle_thenOk() throws Exception {
        getArticle().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenGetArticles_thenOk() throws Exception {
        getAllArticles().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenCreateArticle_thenUnauthorized() throws Exception {
        createArticle().andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenpatchArticleTitle_thenUnauthorized() throws Exception {
        patchArticleTitle(1).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenDeleteArticle_thenUnauthorized() throws Exception {
        deleteArticle(1).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenPatchArticleStatusToACCEPTED_thenUnauthorized() throws Exception {
        patchArticleStatusToACCEPTED(1).andExpect(
                status().isUnauthorized()
        );
    }
}
