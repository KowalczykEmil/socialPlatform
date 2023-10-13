package com.example.socialplatform.datatransferobject;

import com.example.socialplatform.schema.RatingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDataTransferObject {
    private RatingType ratingType;
    private Long postId;
}
