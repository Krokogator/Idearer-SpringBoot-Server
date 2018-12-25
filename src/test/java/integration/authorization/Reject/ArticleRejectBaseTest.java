package integration.authorization.Reject;

import com.krokogator.spring.resources.reject.article.dto.PostArticleRejectDTO;
import integration.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleRejectAnonymousAuthorizationTest.class,
        ArticleRejectUserAuthorizationTest.class,
        ArticleRejectAdminAuthorizationTest.class

})
public abstract class ArticleRejectBaseTest extends IntegrationTest {

    public ResultActions rejectArticle(int articleId) throws Exception {
        PostArticleRejectDTO dto = new PostArticleRejectDTO();
        dto.description = "Test description";

        return mockMvc.perform(
                post("/articles/" + articleId + "/rejects/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        );
    }

    public ResultActions getRejectsByArticleId(int articleId) throws Exception {
        return mockMvc.perform(
                get("/articles/" + articleId + "/rejects/")
        );
    }
}
