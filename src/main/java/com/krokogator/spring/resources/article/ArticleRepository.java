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
    Page<Article> findAllByCategoryNameIgnoreCaseOrderByCreatedDesc(String categoryId, Pageable pageable);
    Page<Article> findAllByUserIdOrderByCreatedDesc(Long userId, Pageable pageable);
    Page<Article> findAllByUserIdAndCategoryNameIgnoreCaseOrderByCreatedDesc(Long userId, String categoryId, Pageable pageable);
}
