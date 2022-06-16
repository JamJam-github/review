package triple.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.dao.ReviewDAO;
import triple.review.dao.UserDAO;
import triple.review.dto.ReviewDTO;
import triple.review.dto.UserDTO;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class APIServiceImpl implements APIService {

    private final UserDAO userDAO;
    private final ReviewDAO reviewDAO;

    @Override
    public ResponseEntity<UserDTO> selectUserPoint(String userId) {
        UserDTO param = new UserDTO(userId, 0);
        UserDTO user = userDAO.selectUser(param);

        // 조회된 사용자가 없는 경우
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> saveReview(ReviewDTO reviewDTO) {
        // ADD의 경우 중복 장소 확인
        if(("ADD").equals(reviewDTO.getAction())){
            String byPlaceId = reviewDAO.findByPlaceId(reviewDTO);
            if(byPlaceId != null) {
                return ResponseEntity.badRequest().build();
            }
        }

        // 리뷰 생성 전 첨부 이미지 ID 삭제
        String reviewId = reviewDTO.getReviewId();
        reviewDAO.removeAttachPhotoId(reviewId);

        // DELETE 제외한 이벤트는 첨부 이미지 ID 등록
        if (!("DELETE").equals(reviewDTO.getAction())){
            if (Objects.nonNull(reviewDTO.getAttachedPhotoIds()) && reviewDTO.getAttachedPhotoIds().size() > 0) {
                Map<String, Object> paramMap = new HashMap<>();
                List<Map<String, Object>> attachMap = new ArrayList<>();
                for (Object attachId : reviewDTO.getAttachedPhotoIds()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("reviewId", reviewId);
                    map.put("attachedId", attachId);
                    attachMap.add(map);
                }
                paramMap.put("attachList", attachMap);
                reviewDAO.saveAttachPhotoIds(paramMap);
            }
        }

        // 리뷰 테이블 Insert/Update, 이력 테이블 Insert
        reviewDAO.saveReview(reviewDTO);
        reviewDAO.saveReviewHistory(reviewDTO);

        // 유저 테이블 Insert/Update
        String userId = reviewDTO.getUserId();
        UserDTO userDTO = new UserDTO(userId, 0);
        userDAO.saveUser(userDTO);
        return ResponseEntity.ok().build();
    }

}
