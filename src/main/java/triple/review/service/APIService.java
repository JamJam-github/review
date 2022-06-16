package triple.review.service;

import org.springframework.http.ResponseEntity;
import triple.review.dto.ReviewDTO;
import triple.review.dto.UserDTO;

public interface APIService {
    ResponseEntity<UserDTO> selectUserPoint(String userId);
    ResponseEntity<Object> saveReview(ReviewDTO reviewDTO);
}
