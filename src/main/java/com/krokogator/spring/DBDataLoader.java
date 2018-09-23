package com.krokogator.spring;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.category.CategoryRepository;
import com.krokogator.spring.resources.comment.Comment;
import com.krokogator.spring.resources.comment.CommentRepository;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.UserService;
import com.krokogator.spring.resources.user.dto.PostPatchUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoType;

    public void run(ApplicationArguments args) {
        if (isDatabaseCreated()) {

            /** DATA ONLY FOR DEVELOPMENT */

            //Admin id:1  User id:2
            PostPatchUserDTO user1 = new PostPatchUserDTO();
            user1.username = "admin";
            user1.password = "admin";
            user1.email = "admin@gmail.com";
            //user1.setRoles(new String[]{"ROLE_ADMIN", "ROLE_USER"});
            userService.saveAdmin(user1);

            PostPatchUserDTO user2 = new PostPatchUserDTO();
            user2.username = "user";
            user2.password = "user";
            user2.email = "user@gmail.com";
            //user2.setRoles(new String[]{"ROLE_USER"});
            userService.saveUser(user2);

            //Jedzenie id:1 Gry id:2
            categoryRepository.save((new Category("Food")));
            categoryRepository.save((new Category("Games")));

            //Ramen
            articleRepository.save(new Article("Ramen", "B8y3SSmz4sg", new User(1L), new Category(1L)));
            commentRepository.save(new Comment("Looks amazing!", new Article(1L), null, new User(1L)));
            commentRepository.save(new Comment("I didn't like the last part :|", new Article(1L), new Comment(1L), new User(2L)));

            //Overwatch
            articleRepository.save(new Article("Doomfist proplays", "2pNCQdGvaKU", new User(2L), new Category(2L)));
            commentRepository.save(new Comment("Wowowowow", new Article(2L), null, new User(2L)));
            commentRepository.save(new Comment("What a player!!", new Article(2L), null, new User(1L)));
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