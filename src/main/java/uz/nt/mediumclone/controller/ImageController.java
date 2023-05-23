package uz.nt.mediumclone.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.mediumclone.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @PostMapping()
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file){
        return imageService.imageUpload(file);
    }

    @GetMapping("/{id}")
    public void dowloadImage(@PathVariable Integer id,HttpServletResponse response) throws IOException {
        imageService.imageDowload(id, response);
    }
}
