package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krokogator.spring.MyNotesServerApp;
import integration.authorization.AuthorizationTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthorizationTest.class
})
@SpringBootTest(
        classes = MyNotesServerApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
//Rollback transactions after each test
@Transactional
public class IntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
