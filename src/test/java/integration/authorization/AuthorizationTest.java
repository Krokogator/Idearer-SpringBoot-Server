package integration.authorization;

import integration.authorization.Category.CategoryAuthorizationTests;
import integration.authorization.User.UserAuthorizationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryAuthorizationTests.class,
        UserAuthorizationTest.class
})
public class AuthorizationTest {

}
