package uz.nt.mediumclone.service;

import org.springframework.http.ResponseEntity;
import uz.nt.mediumclone.model.Tag;

import java.util.List;

public interface TagServices {

    List<Tag> addTags(List<String> tags);

    ResponseEntity<List<String>> popularTags();
}
