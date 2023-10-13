package com.example.socialplatform.datatransferobject;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDataTransferObject {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}