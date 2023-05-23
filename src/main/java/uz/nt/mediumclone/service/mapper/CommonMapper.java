package uz.nt.mediumclone.service.mapper;

import org.springframework.stereotype.Component;
import uz.nt.mediumclone.model.Tag;
import uz.nt.mediumclone.model.User;

import java.util.List;
import java.util.stream.Collectors;

public interface CommonMapper<D,E> {

    D toDto(E e);
    E toEntity(D d);
    default List<String> mapTags(List<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    default Integer mapLikes(List<User> likes) {
        return likes.size();
    }
}
