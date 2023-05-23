package uz.nt.mediumclone.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.mediumclone.model.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{

    @Query(value = """
            SELECT a.*
            FROM article a
            INNER JOIN follows f ON a.author_id = f.following_id
            WHERE f.follower_id = ?1
            """, countQuery = "SELECT COUNT(*) FROM article a INNER JOIN follows f ON a.author_id = f.following_id WHERE f.follower_id = ?1", nativeQuery = true)
    Page<Article> getArticlesOfUserFeed(Integer follower_id, Pageable pageable);
}
