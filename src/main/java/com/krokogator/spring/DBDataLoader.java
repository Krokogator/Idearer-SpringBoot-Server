package com.krokogator.spring;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.article.ArticleStatus;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.category.CategoryRepository;
import com.krokogator.spring.resources.comment.Comment;
import com.krokogator.spring.resources.comment.CommentRepository;
import com.krokogator.spring.resources.reject.article.ArticleReject;
import com.krokogator.spring.resources.reject.article.ArticleRejectRepository;
import com.krokogator.spring.resources.report.article.ArticleReport;
import com.krokogator.spring.resources.report.article.ArticleReportRepository;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.krokogator.spring.resources.article.ArticleStatus.*;

@Component
public class DBDataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleReportRepository articleReportRepository;

    @Autowired
    private ArticleRejectRepository articleRejectRepository;

    @Autowired
    private BCryptPasswordEncoder PASSWORD_ENCODER;

    @Autowired
    Environment env;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoType;

    public void run(ApplicationArguments args) {
        if (isDatabaseCreated()) {

            /** DATA ONLY FOR DEVELOPMENT */

            // Users
            User admin = saveUser("admin", "admin", "admin@gmail.com", "ADMIN");
            User user = saveUser("user", "user", "user@gmail.com", "USER");

            //Categories
            Category c_food = saveCategory("Food");
            Category c_games = saveCategory("Games");
            Category c_ideas = saveCategory("Ideas");
            Category c_technology = saveCategory("Technology");

            //Articles
            //ACCEPTED
            Article a1 = saveArticle("Ramen", "B8y3SSmz4sg", admin, c_food, ACCEPTED);
            Article a2 = saveArticle("Doomfist plays like a pro!", "2pNCQdGvaKU", user, c_games, ACCEPTED);
            Article a3 = saveArticle("Tasty !", "K3EZwmoHVtc", user, c_food, ACCEPTED);
            Article a4 = saveArticle("What a landing...", "VBlIvghQTlI", user, c_technology, ACCEPTED);
            Article a5 = saveArticle("Structural patterns overview", "lPsSL6_7NBg", user, c_technology, ACCEPTED);
            Article a6 = saveArticle("Command pattern!", "9qA5kw8dcSU", user, c_technology, ACCEPTED);
            //PENDING
            Article a7 = saveArticle("Strategy pattern", "v9ejT8FO-7I", admin, c_technology, PENDING);
            Article a8 = saveArticle("Observer pattern", "_BpmfnqjgzQ", admin, c_technology, PENDING);
            Article a9 = saveArticle("Decore your classes!", "GCraGHx6gso", user, c_technology, PENDING);
            Article a10 = saveArticle("I can make you any object!", "EcFVTgRHJLM", user, c_technology, PENDING);
            Article a11 = saveArticle("I can make you many objects at once!", "v-GiuMmsXj4", user, c_technology, PENDING);
            //REJECTED
            Article a12 = saveArticle("Funniest thing ever, lol!", "youtube.com", user, c_ideas, REJECTED);

            //Comments
            Comment c1a1 = saveComment("Looks amazing", a1, null, admin);
            Comment c2c1a2 = saveComment("I didn't like the last part :|", a1, c1a1, user);
            Comment c3a2 = saveComment("Wow, amazing!", a2, null, user);
            Comment c4c3a2 = saveComment("What a player indeed!", a2, c3a2, admin);

            //Article Reports
            ArticleReport aReport1 = saveArticleReport("This video contains violence!", user, a1);
            ArticleReport aReport2 = saveArticleReport("Bad for children :<", user, a1);
            ArticleReport aReport3 = saveArticleReport("Bs video...", user, a1);
            ArticleReport aReport4 = saveArticleReport("Boring video...", user, a5);
            ArticleReport aReport5 = saveArticleReport("Violates user rights.", user, a3);

            //Article Rejects
            ArticleReject aReject = saveArticleReject("Rejected by Admin, invalid video", a12);
        }
    }

    private boolean isDatabaseCreated() {
        switch (ddlAutoType) {
            case "create":
                return true;
            case "create_drop":
                return true;
            default:
                return false;
        }
    }

    private User saveUser(String name, String password, String email, String role) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(PASSWORD_ENCODER.encode(password));
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    private Category saveCategory(String name) {
        return categoryRepository.save(new Category(name));
    }

    private Article saveArticle(String title, String content, User user, Category category, ArticleStatus status) {
        Article article = new Article(title, content, user, category);
        article.setStatus(status);
        return articleRepository.save(article);
    }

    private Comment saveComment(String title, Article article, Comment parentComment, User user) {
        return commentRepository.save(new Comment(title, article, parentComment, user));
    }

    private ArticleReport saveArticleReport(String description, User user, Article article) {
        return articleReportRepository.save(new ArticleReport(description, user, article));
    }

    private ArticleReject saveArticleReject(String description, Article article) {
        ArticleReject articleReject = new ArticleReject();
        articleReject.setDescription(description);
        articleReject.setArticle(article);
        return articleRejectRepository.save(articleReject);
    }
}