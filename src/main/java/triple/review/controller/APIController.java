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

    @GetMapping("/events")
    public ResponseEntity<UserDTO> userPoint(@RequestParam(value="userId", defaultValue = "") String userId) {
        return apiService.selectUserPoint(userId);
    }

    @PostMapping("/events")
    public ResponseEntity<Object> saveReview(@RequestBody ReviewDTO reviewDTO) {
        return apiService.saveReview(reviewDTO);
    }
}
