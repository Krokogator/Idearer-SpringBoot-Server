package com.krokogator.spring.resources.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Page<Article> getArticlesByAdvancedQuery(Long userId, String categoryName, Pageable page, ArticleSort sort) {

        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> query = cb.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);

        // Filtering predicates

        if (userId != null) {
            predicates.add(cb.equal(root.join("user").get("id"), userId));
        }

        if (categoryName != null) {
            predicates.add(cb.equal(root.join("category").get("name"), categoryName));
        }

        query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));


        // Sorting type
        if (sort == null) sort = ArticleSort.DESCENDING_CREATED;
        switch (sort) {
            case ASCENDING_CREATED:
                query.orderBy(cb.asc(root.get("created")));
                break;
            case DESCENDING_CREATED:
                query.orderBy(cb.desc(root.get("created")));
                break;
            case ASCENDING_LIKES:
                query.orderBy(cb.asc(cb.size(root.get(Article_.likes))));
                break;
            case DESCENDING_LIKES:
                query.orderBy(cb.desc(cb.size(root.get(Article_.likes))));
                break;
            case ASCENDING_TITLE:
                query.orderBy(cb.asc(root.get(Article_.title)));
                break;
            case DESCENDING_TITLE:
                query.orderBy(cb.desc(root.get(Article_.title)));
        }

        //Counting

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        countQuery.select(cb.count(countQuery.from(Article.class)));

        //Paging
        TypedQuery<Article> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());
        Page result = new PageImpl(typedQuery.getResultList(), page, em.createQuery(countQuery).getSingleResult());
        return result;
    }
}
