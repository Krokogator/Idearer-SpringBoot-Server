package com.krokogator.spring.resources.reject.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.article.ArticleService;
import com.krokogator.spring.resources.article.ArticleStatus;
import com.krokogator.spring.resources.reject.Reject;
import com.krokogator.spring.resources.reject.RejectService;
import com.krokogator.spring.resources.reject.article.dto.PostArticleRejectDTO;
import com.krokogator.spring.resources.report.article.ArticleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class ArticleRejectService extends RejectService<Reject> {

    @Autowired
    ArticleRejectRepository articleRejectRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleReportService articleReportService;

    @Transactional
    public ArticleReject reject(Long articleId, PostArticleRejectDTO dto) throws ClientErrorException {
        //Get article (also throws error if no article found)
        Article article = articleService.getArticle(articleId);

        //Validate if article is already rejected
        if (article.getStatus() == ArticleStatus.REJECTED
            /*|| articleRejectRepository.findAllByArticleIdAndStatus(articleId, RejectStatus.NOT_CORRECTED).stream().count() != 0*/)
            throw new ClientErrorException(HttpStatus.CONFLICT, "Article with id: " + articleId + " already rejected!");

        //Reject article
        article.setStatus(ArticleStatus.REJECTED);

        ArticleReject articleReject = new ArticleReject();

        //Set article and description
        articleReject.setDescription(dto.description);
        articleReject.setArticle(new Article(articleId));

        //Update article
        articleRepository.save(article);
        //Delete reports connected with rejected article
        articleReportService.deleteAllByArticleId(articleId);
        //articleReportRepository.deleteAllByArticleId(articleId);
        //Save reject
        save(articleReject);
        return articleReject;
    }

    public List<ArticleReject> getByArticleId(Long articleId) {
        return articleRejectRepository.findAllByArticleId(articleId);
    }
}
