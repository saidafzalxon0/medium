package uz.nt.mediumclone.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ResponseEntity<?> imageUpload(MultipartFile file);
    void imageDowload(Integer id,HttpServletResponse response) throws IOException;


}
