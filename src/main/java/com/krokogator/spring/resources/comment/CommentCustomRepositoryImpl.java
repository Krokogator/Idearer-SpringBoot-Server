package com.krokogator.spring.resources.comment;

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
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Comment> getCommentsByAdvancedQuery(Long userId, Long articleId, Long parentCommentId, Pageable page) {
//        Query query = entityManager.createQuery("SELECT c FROM Comment c WHERE " +
//                ":userId is null or c.user.id = :userId " +
//                "AND :articleId is null or c.article.id = :articleId", Comment.class);
//
//        query.setParameter("userId", userId);
//        query.setParameter("articleId", articleId);
//        return query.getResultList();

        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);

        if (userId != null) {
            predicates.add(cb.equal(root.join("user").get("id"), userId));
        }

        if (articleId != null) {
            predicates.add(cb.equal(root.join("article").get("id"), articleId));
        }

        if (parentCommentId != null) {
            if (parentCommentId == 0) {
                predicates.add(cb.isNull(root.get("parentComment")));
            } else if (parentCommentId > 0) {
                predicates.add(cb.equal(root.join("parentComment").get("id"), parentCommentId));
            }
        }
        query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Comment> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize() - page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        return typedQuery.getResultList();
    }
}
