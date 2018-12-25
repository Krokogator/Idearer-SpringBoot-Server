package integration.authorization.Report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser(roles = "USER", username = "user", password = "user")
public class ArticleReportUserTest extends ArticleReportAuthorizationTest {
    @Test
    public void givenUser_getReportsByArticleId_thenForbidden() throws Exception {
        getReportsByArticleId(1).andExpect(
                status().isForbidden()
        );
    }

    @Test
    public void givenUser_getReportedArticlesIds_thenForbidden() throws Exception {
        getReportedArticles().andExpect(
                status().isForbidden()
        );
    }

    @Test
    public void givenUser_createReport_thenCreated() throws Exception {
        createReport(1).andExpect(
                status().isCreated()
        );
    }

    @Test
    public void givenUser_deleteReport_thenForbidden() throws Exception {
        deleteReport(1).andExpect(
                status().isForbidden()
        );
    }
}
