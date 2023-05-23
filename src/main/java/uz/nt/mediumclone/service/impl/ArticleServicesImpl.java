package uz.nt.mediumclone.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.mediumclone.dto.ArticlesDto;
import uz.nt.mediumclone.exeption.DatabaseException;
import uz.nt.mediumclone.exeption.NotAllowedException;
import uz.nt.mediumclone.model.Article;
import uz.nt.mediumclone.model.Tag;
import uz.nt.mediumclone.model.User;
import uz.nt.mediumclone.repository.ArticleRepository;
import uz.nt.mediumclone.repository.UserRepository;
import uz.nt.mediumclone.security.SecurityServices;
import uz.nt.mediumclone.service.ArticleServices;
import uz.nt.mediumclone.service.TagServices;
import uz.nt.mediumclone.service.mapper.ArticleMapper;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServicesImpl implements ArticleServices {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserRepository userRepository;
    private final TagServices tagService;
    private final SecurityServices securityServices;

    @Override
    @Transactional
    public ResponseEntity<ArticlesDto> addArticle(ArticlesDto articlesDto) {
        List<Tag> list = tagService.addTags(articlesDto.getTags());
        Article article = articleMapper.toEntity(articlesDto);
        User loggedUser = securityServices.getLoggedUser();
        article.setAuthor(loggedUser);
        article.setTags(list);

        Article savedArticle = articleRepository.save(article);
        savedArticle.setPublishDate(LocalDateTime.now());
        savedArticle.setUpdatedAt(LocalDateTime.now());
        try {
            return ResponseEntity.ok()
                    .body(articleMapper.toDto(savedArticle));
        } catch (Exception e) {
            throw new DatabaseException("Error while saving article to database: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> deleteArticleById(Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        articleRepository.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<ArticlesDto> getArticleById(Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(200).body(articleMapper.toDto(articleOptional.get()));
    }

//    @Override
//    public ResponseEntity<List<ArticlesDto>> getAllArticles() {
//        List<Article> articleOptional = articleRepository.findAll();
//        return ResponseEntity.status(200).body(articleOptional.stream().map(articleMapper::toDto).toList());
//    }

    @Override
    public ResponseEntity<Page<ArticlesDto>> getAllArticles(Integer page, Integer size) {
        size = Math.max(size, 1);
        page = Math.max(page, 0);

        Long count = articleRepository.count();

        PageRequest pageRequest = PageRequest.of((count / size) <= page ? (count % size == 0) ? (int) (count / size) - 1 : (int) (count / size) : page, size);

        pageRequest = pageRequest.withSort(Sort.by("publishDate").descending());

        Page<ArticlesDto> articles = articleRepository.findAll(pageRequest).map(articleMapper::toDto);

        return ResponseEntity.ok().body(articles);
    }

    @Override
    public ResponseEntity<ArticlesDto> editArticle(ArticlesDto articlesDto) throws NotAllowedException {
        Article article = isValidArticle(articlesDto).getBody();

        if (articlesDto.getTitle() != null) {
            article.setTitle(articlesDto.getTitle());
        }
        if (articlesDto.getAbout() != null) {
            article.setAbout(articlesDto.getAbout());
        }
        if (articlesDto.getBody() != null) {
            article.setBody(articlesDto.getBody());
        }
        if (articlesDto.getTags() != null) {
            List<Tag> tagList = tagService.addTags(articlesDto.getTags());
            article.setTags(tagList);
        }

        return ResponseEntity.ok()
                .body(articleMapper.toDto(articleRepository.save(article)));
    }

    private ResponseEntity<Article> isValidArticle(ArticlesDto articlesDto) throws NotAllowedException {
        if (articlesDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Article> articleOptional = articleRepository.findById(articlesDto.getId());

        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Article article = articleOptional.get();

        if (!article.getAuthor().getUsername().equals(securityServices.getLoggedUser().getUsername())) {
            throw new NotAllowedException();
        }
        return ResponseEntity.ok().body(article);
    }

    @Override
    public ResponseEntity<ArticlesDto> addLike(Integer articleId) {
        User loggedUser = securityServices.getLoggedUser();
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            List<User> articleLikes = article.getLikes();
            articleLikes.add(loggedUser);
            article.setLikes(articleLikes);
            articleRepository.save(article);

            List<Article> userLikes = loggedUser.getLikes();
            userLikes.add(article);
            loggedUser.setLikes(userLikes);
            userRepository.save(loggedUser);

            return ResponseEntity.ok().body(articleMapper.toDto(article));
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<ArticlesDto> deleteLike(Integer articleId) {
        User loggedUser = securityServices.getLoggedUser();
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            List<User> articleLikes = article.getLikes();
            articleLikes.remove(loggedUser);
            article.setLikes(articleLikes);
            articleRepository.save(article);

            List<Article> userLikes = loggedUser.getLikes();
            userLikes.remove(article);
            loggedUser.setLikes(userLikes);
            userRepository.save(loggedUser);

            return ResponseEntity.ok().body(articleMapper.toDto(article));
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Page<ArticlesDto>> getArticlesOfUserFeed(Integer page, Integer size) {
        size = Math.max(size, 1);
//        page = Math.max(page, 0);

        User loggedUser = securityServices.getLoggedUser();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("publish_date").descending());

        Page<ArticlesDto> articles = articleRepository.getArticlesOfUserFeed(loggedUser.getId(), pageRequest).map(articleMapper::toDto);

        return ResponseEntity.ok().body(articles);
    }
}
