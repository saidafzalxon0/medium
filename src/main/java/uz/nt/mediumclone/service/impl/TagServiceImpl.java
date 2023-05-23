package uz.nt.mediumclone.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.UniqueConstraint;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.mediumclone.model.Tag;
import uz.nt.mediumclone.repository.TagsRepository;
import uz.nt.mediumclone.service.TagServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TagServiceImpl implements TagServices {
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public List<Tag> addTags(List<String> tagsList) {

        List<Tag> newTags = new ArrayList<>();

        for (String tag : tagsList) {
            try {
                newTags.add(tagsRepository.saveNewTags(tag).get());
            } catch (ConstraintViolationException e) {
                log.error("Tag already exists: {}", e.getCause().getMessage());
            }
        }

        return newTags;
    }

    @Override
    public ResponseEntity<List<String>> popularTags() {
        return ResponseEntity.ok().body(tagsRepository.getPopularTags().stream().map(Tag::getName).toList());
    }

}
