package uz.nt.mediumclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(generator = "ImageIdSeq")
    @SequenceGenerator(name = "ImageIdSeq", sequenceName = "image_Id_seq", allocationSize = 1)
    private Integer id;
    private String path;
}
