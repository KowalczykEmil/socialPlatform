package com.example.socialplatform.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String tagName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean like;
    private boolean disLike;
}
