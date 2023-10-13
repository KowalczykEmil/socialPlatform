package com.example.socialplatform.mapper;

import com.example.socialplatform.datatransferobject.TagDataTransferObject;
import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.Tag;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(tag))")
    TagDataTransferObject mapTagToDto(Tag tag);

    default int mapPosts(Tag tag) {
        return Optional.ofNullable(tag.getPosts()).map(List::size).orElse(0);
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Tag mapDtoToTag(TagDataTransferObject tagDataTransferObject);
}
