package triple.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triple.review.dto.ReviewDTO;
import triple.review.dto.UserDTO;
import triple.review.service.APIService;


@RestController
@RequiredArgsConstructor
public class APIController {

    private final APIService apiService;

    /**
     * 사용자 포인트 조회 API
     * @param userId
     * @return userId, point
     */
    @GetMapping("/events")
    public ResponseEntity<UserDTO> userPoint(@RequestParam(value="userId", defaultValue = "") String userId) {
        return apiService.selectUserPoint(userId);
    }

    /**
     * 리뷰 작성 기능으로 포인트 적립 API
     * @param reviewDTO
     * @return
     */
    @PostMapping("/events")
    public ResponseEntity<Object> saveReview(@RequestBody ReviewDTO reviewDTO) {
        return apiService.saveReview(reviewDTO);
    }
}
