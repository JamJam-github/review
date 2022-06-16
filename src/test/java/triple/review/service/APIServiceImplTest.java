package triple.review.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.review.dao.ReviewDAO;
import triple.review.dao.UserDAO;
import triple.review.dto.ReviewDTO;
import triple.review.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
class APIServiceImplTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ReviewDAO reviewDAO;

    @Autowired
    APIServiceImpl apiService;

    @Test
    @DisplayName("사용자 조회")
    void selectUserPoint() {
        String userId = UUID.randomUUID().toString();
        UserDTO userDTO = new UserDTO(userId, 0);
        UserDTO user = userDAO.selectUser(userDTO);
        assertThatThrownBy(() -> user.getUserId()).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("리뷰 등록")
    @Transactional
    void insertReview() {
        String reviewId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String placeId = UUID.randomUUID().toString();
        String content = "여행 가자";
        log.info("reviewId={}, userId={}", reviewId, userId);

        // 리뷰 1건 등록 후 해당 userId 조회
        ReviewDTO reviewDTO = new ReviewDTO(reviewId, content, null, userId, placeId, "ADD", "REVIEW");
        apiService.saveReview(reviewDTO);

        UserDTO userDTO = new UserDTO(userId, 0);
        UserDTO user = userDAO.selectUser(userDTO);
        log.info("save user={}", user.toString());
        assertThat(user.getPoint()).isEqualTo(2);
    }

    @Test
    @DisplayName("리뷰 수정")
    @Transactional
    void updateReview() {
        String reviewId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String placeId = UUID.randomUUID().toString();
        List<String> attachPhotoId = new ArrayList<>();
        attachPhotoId.add(UUID.randomUUID().toString());
        log.info("reviewId={}, userId={}", reviewId, userId);

        // 리뷰 1건 등록
        ReviewDTO addReviewData = new ReviewDTO(reviewId, "여행 여행", null, userId, placeId, "ADD", "REVIEW");
        int cnt = reviewDAO.saveReview(addReviewData);

        // 등록한 리뷰에 사진 ID 첨부
        ReviewDTO modReviewData = new ReviewDTO(reviewId, "여행 수정", attachPhotoId, userId, placeId, "MOD", "REVIEW");
        apiService.saveReview(modReviewData);

        UserDTO userDTO = new UserDTO(userId, 0);
        UserDTO user = userDAO.selectUser(userDTO);
        log.info("save user={}", user.toString());
        assertThat(user.getPoint()).isEqualTo(3);
    }
}