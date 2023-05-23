package uz.nt.mediumclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.mediumclone.dto.ArticlesDto;
import uz.nt.mediumclone.exeption.NotAllowedException;
import uz.nt.mediumclone.service.ArticleServices;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleResources {

    @Autowired
    private ArticleServices articleServices;


    @PostMapping
    public ResponseEntity<ArticlesDto> addArticle(@RequestBody ArticlesDto articlesDto) {
        return articleServices.addArticle(articlesDto);
    }

    @PatchMapping
    public ResponseEntity<ArticlesDto> editArticle(@RequestBody ArticlesDto articlesDto) throws NotAllowedException {
        return articleServices.editArticle(articlesDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Integer id) {
        return articleServices.deleteArticleById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticlesDto> getArticleById(@PathVariable Integer id) {
        return articleServices.getArticleById(id);
    }

    @GetMapping
    public ResponseEntity<Page<ArticlesDto>> getAllArticles(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return articleServices.getAllArticles(page, size);
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<ArticlesDto>> getArticlesOfUserFeed(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return articleServices.getArticlesOfUserFeed(page, size);
    }

    @PostMapping("/{articleId}/like")
    public ResponseEntity<ArticlesDto> addLike(@PathVariable Integer articleId) {
        return articleServices.addLike(articleId);
    }

    @DeleteMapping("/{articleId}/like")
    public ResponseEntity<ArticlesDto> deleteLike(@PathVariable Integer articleId) {
        return articleServices.deleteLike(articleId);
    }
}
