package uz.nt.mediumclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.mediumclone.dto.CommentsDto;
import uz.nt.mediumclone.service.CommentsServices;

import java.util.List;

@RestController
@RequestMapping("articles")
public class CommentsResources {

    @Autowired
    private CommentsServices commentsServices;

    @PostMapping("/{article_id}/comment")
    public ResponseEntity<CommentsDto> addComment(@PathVariable Integer article_id, @RequestBody CommentsDto commentsDto) {
        return commentsServices.addComment(article_id, commentsDto);
    }

    @GetMapping("/{article_id}/comment")
    public ResponseEntity<List<CommentsDto>> getCommentByArticleId(@PathVariable Integer article_id) {
        return commentsServices.getCommentsByArticleId(article_id);
    }
}
