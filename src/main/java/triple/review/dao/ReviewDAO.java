package triple.review.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import triple.review.dto.ReviewDTO;

import java.util.Map;


@Repository
@Mapper
public interface ReviewDAO {
    String findByPlaceId(ReviewDTO reviewDTO);
    int saveReview(ReviewDTO reviewDTO);
    void saveReviewHistory(ReviewDTO reviewDTO);
    void saveAttachPhotoIds(Map<String, Object> attachMap);
    int removeAttachPhotoId(String reviewId);
}
