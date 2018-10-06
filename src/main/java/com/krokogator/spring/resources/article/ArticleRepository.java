package com.krokogator.spring.resources.article;

import com.krokogator.spring.resources.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ArticleRepository extends CrudRepository<Article, Long>, JpaSpecificationExecutor<Category> {
    Article getById(Long id);

    Page<Article> findAll(Pageable pageable);

    List<Article> findAll();

    /** FIND by (author AND category) OR (author) OR (category) OR findAll*/
    Page<Article> findAllByOrderByCreatedDesc(Pageable pageable);
    Page<Article> findAllByCategoryTitleIgnoreCaseOrderByCreatedDesc(String categoryTitle, Pageable pageable);
    Page<Article> findAllByUserUsernameOrderByCreatedDesc(String userName, Pageable pageable);
    Page<Article> findAllByUserUsernameAndCategoryTitleIgnoreCaseOrderByCreatedDesc(String userName, String categoryTitle, Pageable pageable);
}
