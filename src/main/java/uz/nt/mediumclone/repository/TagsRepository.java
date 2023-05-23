package uz.nt.mediumclone.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.mediumclone.model.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "WITH ins AS (INSERT INTO tag(name) VALUES (?1) ON CONFLICT (name) DO NOTHING RETURNING *) SELECT * FROM ins UNION ALL SELECT * FROM tag WHERE name = ?1", nativeQuery = true)
    Optional<Tag> saveNewTags(String tagName);

    @Query(value = """
            SELECT t.id, t.name, COUNT(*) AS usage_count
            FROM tag AS t
            JOIN article_tag AS at ON t.id = at.tag_id
            GROUP BY t.id, t.name
            ORDER BY usage_count DESC
            LIMIT 10;""", nativeQuery = true)
    List<Tag> getPopularTags();
}
