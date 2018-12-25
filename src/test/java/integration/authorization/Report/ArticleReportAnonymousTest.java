package integration.authorization.Report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithAnonymousUser
public class ArticleReportAnonymousTest extends ArticleReportAuthorizationTest {

    @Test
    public void givenAnonymous_getReportsByArticleId_thenUnauthorized() throws Exception {
        getReportsByArticleId(1).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    public void givenAnonymous_getReportedArticlesIds_thenUnauthorized() throws Exception {
        getReportedArticles().andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    public void givenAnonymous_createReport_thenUnauthorized() throws Exception {
        createReport(1).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    public void givenAnonymous_deleteReport_thenUnauthorized() throws Exception {
        deleteReport(1).andExpect(
                status().isUnauthorized()
        );
    }

}
