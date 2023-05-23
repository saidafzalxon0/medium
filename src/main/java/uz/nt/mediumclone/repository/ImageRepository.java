package uz.nt.mediumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.mediumclone.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
