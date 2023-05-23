package uz.nt.mediumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.mediumclone.model.Follows;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Integer> {
}
