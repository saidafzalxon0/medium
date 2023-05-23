package uz.nt.mediumclone.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.nt.mediumclone.dto.ArticlesDto;
import uz.nt.mediumclone.exeption.NotAllowedException;

import java.util.List;

public interface ArticleServices {

    ResponseEntity<ArticlesDto> addArticle(ArticlesDto articlesDto);
    ResponseEntity<Void> deleteArticleById(Integer id);
    ResponseEntity<ArticlesDto> getArticleById(Integer id);
    ResponseEntity<Page<ArticlesDto>> getAllArticles(Integer page, Integer size);
    ResponseEntity<ArticlesDto> editArticle(ArticlesDto articlesDto) throws NotAllowedException;
    ResponseEntity<ArticlesDto> addLike(Integer articleId);
    ResponseEntity<ArticlesDto> deleteLike(Integer articleId);

    ResponseEntity<Page<ArticlesDto>> getArticlesOfUserFeed(Integer page, Integer size);
}
