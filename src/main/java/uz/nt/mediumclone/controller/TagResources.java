package uz.nt.mediumclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.mediumclone.service.TagServices;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagResources {

    @Autowired
    private TagServices tagServices;

    @GetMapping
    public ResponseEntity<List<String>> getPopularTags() {
        return tagServices.popularTags();
    }
}
