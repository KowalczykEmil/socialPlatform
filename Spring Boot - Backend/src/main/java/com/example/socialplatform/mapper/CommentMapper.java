package com.example.socialplatform.mapper;

import com.example.socialplatform.datatransferobject.CommentsDataTransferObject;
import com.example.socialplatform.schema.Comment;
import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDataTransferObject.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDataTransferObject commentsDataTransferObject, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDataTransferObject mapToDto(Comment comment);
}