package triple.review.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {
    private String reviewId;
    private String content;
    private List attachedPhotoIds;
    private String userId;
    private String placeId;
    private String action;
    private String type;
}
