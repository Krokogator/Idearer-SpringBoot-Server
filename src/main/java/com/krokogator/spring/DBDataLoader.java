package com.krokogator.spring;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.category.CategoryRepository;
import com.krokogator.spring.resources.comment.Comment;
import com.krokogator.spring.resources.comment.CommentRepository;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
    private BCryptPasswordEncoder PASSWORD_ENCODER;

    @Autowired
    Environment env;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoType;

    public void run(ApplicationArguments args) {
        if (isDatabaseCreated()) {

            /** DATA ONLY FOR DEVELOPMENT */

            //Admin id:1  User id:2
            User user1 = new User();
            user1.setUsername("admin");
            user1.setPassword(PASSWORD_ENCODER.encode("admin"));
            user1.setEmail("admin@gmail.com");
            user1.setRole("ADMIN");
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("user");
            user2.setPassword(PASSWORD_ENCODER.encode("user"));
            user2.setEmail("user@gmail.com");
            user2.setRole("USER");
            userRepository.save(user2);

            User user3 = new User();
            user3.setUsername("other");
            user3.setPassword(PASSWORD_ENCODER.encode("other"));
            user3.setEmail("other@gmail.com");
            user3.setRole("OTHERUSER");
            userRepository.save(user3);

            //Food id:1 Games id:2
            categoryRepository.save((new Category("Food")));
            categoryRepository.save((new Category("Games")));
            categoryRepository.save((new Category("Other")));
            categoryRepository.save((new Category("Technology")));

            //Ramen
            articleRepository.save(new Article("Ramen", "B8y3SSmz4sg", new User(1L), new Category(1L)));
            commentRepository.save(new Comment("Looks amazing!", new Article(1L), null, new User(1L)));
            commentRepository.save(new Comment("I didn't like the last part :|", new Article(1L), new Comment(1L), new User(2L)));

            //Overwatch
            articleRepository.save(new Article("Doomfist proplays", "2pNCQdGvaKU", new User(2L), new Category(2L)));
            commentRepository.save(new Comment("Wowowowow", new Article(2L), null, new User(2L)));
            commentRepository.save(new Comment("What a player!!", new Article(2L), null, new User(1L)));

            //OtherArticle
            articleRepository.save(new Article("Doomfist proplays", "2pNCQdGvaKU", new User(3L), new Category(3L)));
            commentRepository.save(new Comment("Wowowowow", new Article(3L), null, new User(3L)));
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
}