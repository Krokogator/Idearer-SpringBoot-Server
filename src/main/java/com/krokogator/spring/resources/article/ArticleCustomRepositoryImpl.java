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
    public Page<Article> getArticlesByAdvancedQuery(Long userId, String categoryName, ArticleStatus status, Pageable page, ArticleSort sort) {

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

        if (status != null) {
            predicates.add(cb.equal(root.get(Article_.status), status));
        }

        query.where(cb.and(predicates.toArray(new Predicate[]{})));


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

        //Paging
        TypedQuery<Article> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Page result = new PageImpl(typedQuery.getResultList(), page, countByCriteriaQuery(cb, query, userId, categoryName, status));

        return result;
    }

    private Long countByCriteriaQuery(CriteriaBuilder cb, CriteriaQuery cqEntity, Long userId, String categoryName, ArticleStatus status) {

        CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
        Root<Article> root = cqCount.from(cqEntity.getResultType());
        cqCount.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if (userId != null) {
            predicates.add(cb.equal(root.join("user").get("id"), userId));
        }

        if (categoryName != null) {
            predicates.add(cb.equal(root.join("category").get("name"), categoryName));
        }

        if (status != null) {
            predicates.add(cb.equal(root.get(Article_.status), status));
        }

        cqCount.where(cb.and(predicates.toArray(new Predicate[]{})));
        return em.createQuery(cqCount).getSingleResult();
    }
}
