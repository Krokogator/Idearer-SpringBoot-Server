package integration.authorization;

import integration.authorization.Article.ArticleAuthorizationTest;
import integration.authorization.Category.CategoryAuthorizationTest;
import integration.authorization.Comment.CommentAuthorizationTest;
import integration.authorization.User.UserAuthorizationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryAuthorizationTest.class,
        UserAuthorizationTest.class,
        ArticleAuthorizationTest.class,
        CommentAuthorizationTest.class
})
public class AuthorizationTest {

}
