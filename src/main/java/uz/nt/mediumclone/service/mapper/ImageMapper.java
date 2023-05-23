package uz.nt.mediumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.mediumclone.dto.ImageDto;
import uz.nt.mediumclone.model.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper extends CommonMapper<ImageDto, Image> {

}
