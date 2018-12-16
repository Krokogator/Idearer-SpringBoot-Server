package integration.authorization.Article;

import com.krokogator.spring.resources.IdReferenceDTO;
import com.krokogator.spring.resources.article.ArticleStatus;
import com.krokogator.spring.resources.article.dto.PatchArticleDTO;
import com.krokogator.spring.resources.article.dto.PostArticleDTO;
import integration.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleAnonymousAuthorizationTest.class,
        ArticleUserAuthorizationTest.class,
        ArticleAdminAuthorizationTest.class
})
public class ArticleAuthorizationTest extends IntegrationTest {

    private final String endpoint = "/articles";

    public ResultActions getArticle() throws Exception {
        return mockMvc.perform(
                get(endpoint + "/1"));
    }

    public ResultActions getAllArticles() throws Exception {
        return mockMvc.perform(
                get(endpoint));
    }

    public ResultActions createArticle() throws Exception {
        PostArticleDTO postArticleDTO = new PostArticleDTO();
        postArticleDTO.title = "Article title";
        postArticleDTO.content = "youtube.com/sample/link";
        IdReferenceDTO categoryReference = new IdReferenceDTO(1L);
        postArticleDTO.setCategory(categoryReference);
        return mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postArticleDTO)));
    }

    public ResultActions patchArticleTitle(int id) throws Exception {
        PatchArticleDTO patchArticleDTO = new PatchArticleDTO();
        patchArticleDTO.title = "Modified article title";
        return mockMvc.perform(
                patch(endpoint + "/" +id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patchArticleDTO)));
    }

    public ResultActions patchArticleStatusToACCEPTED(int id) throws Exception {
        PatchArticleDTO patchArticleDTO = new PatchArticleDTO();
        patchArticleDTO.status = ArticleStatus.ACCEPTED;
        return mockMvc.perform(
                patch(endpoint + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patchArticleDTO))
        );
    }



    public ResultActions deleteArticle(int id) throws Exception {
        return mockMvc.perform(
                delete(endpoint + "/" + id));
    }
}