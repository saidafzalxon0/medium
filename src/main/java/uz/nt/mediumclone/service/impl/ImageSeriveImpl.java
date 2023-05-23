package uz.nt.mediumclone.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.mediumclone.model.Image;
import uz.nt.mediumclone.repository.ImageRepository;
import uz.nt.mediumclone.service.ImageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageSeriveImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<?> imageUpload(MultipartFile file){
        File files = new File(filePath("C:/Users/saida/Downloads/Telegram Desktop"));

        try {
            file.transferTo(files);
            Image imageEntity = new Image();
            imageEntity.setPath(files.getPath());
            imageRepository.save(imageEntity);
            return ResponseEntity.status(201).body(imageEntity.getId());
        } catch (IOException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }


    }

    @Override
    public void imageDowload(Integer id, HttpServletResponse response) throws IOException {
        Optional<Image> imageentity = imageRepository.findById(id);


        response.setContentType("image/jpeg");
        InputStream is = new FileInputStream(new File(imageentity.get().getPath()));
        IOUtils.copy(is, response.getOutputStream());
    }


    public String filePath(String folder){
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        java.io.File file = new java.io.File(folder + "/"+ path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        return file.getPath() + "\\"+ uuid + ".jpg";
    }

}
