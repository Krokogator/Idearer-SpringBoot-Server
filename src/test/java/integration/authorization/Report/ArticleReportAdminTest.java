package integration.authorization.Report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
public class ArticleReportAdminTest extends ArticleReportAuthorizationTest {

    @Test
    public void givenAdmin_getReportsByArticleId_thenOk() throws Exception {
        getReportsByArticleId(1).andExpect(
                status().isOk()
        );
    }

    @Test
    public void givenAdmin_getReportedArticlesIds_thenOk() throws Exception {
        getReportedArticles().andExpect(
                status().isOk()
        );
    }

    @Test
    public void givenAdmin_createReport_thenCreated() throws Exception {
        createReport(1).andExpect(
                status().isCreated()
        );
    }

    @Test
    public void givenAdmin_deleteReport_thenNoContent() throws Exception {
        deleteReport(1).andExpect(
                status().isNoContent()
        );
    }


}
