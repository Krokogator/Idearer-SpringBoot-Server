package integration.authorization.Report;

import com.krokogator.spring.resources.report.dto.PostReportDTO;
import integration.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleReportAnonymousTest.class,
        ArticleReportUserTest.class,
        ArticleReportAdminTest.class
})
public abstract class ArticleReportAuthorizationTest extends IntegrationTest {

    public ResultActions getReportsByArticleId(int articleId) throws Exception {
        return mockMvc.perform(
                get("/articles/" + articleId + "/reports/"));
    }

    public ResultActions getReportedArticles() throws Exception {
        return mockMvc.perform(
                get("/articles/reported"));
    }

    public ResultActions createReport(int articleId) throws Exception {
        PostReportDTO postReportDTO = new PostReportDTO();
        postReportDTO.description = "Invalid title";

        return mockMvc.perform(
                post("/articles/" + articleId + "/reports/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReportDTO))
        );

    }

    public ResultActions deleteReport(int id) throws Exception {
        return mockMvc.perform(
                delete("/articles/reports/" + id)
        );
    }

}