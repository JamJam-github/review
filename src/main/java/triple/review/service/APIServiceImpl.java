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

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> saveReview(ReviewDTO reviewDTO) {
        if(("ADD").equals(reviewDTO.getAction())){
            String byPlaceId = reviewDAO.findByPlaceId(reviewDTO);
            if(byPlaceId != null) {
                return ResponseEntity.badRequest().build();
            }
        }

        String reviewId = reviewDTO.getReviewId();
        reviewDAO.removeAttachPhotoId(reviewId);

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

        reviewDAO.saveReview(reviewDTO);
        reviewDAO.saveReviewHistory(reviewDTO);

        String userId = reviewDTO.getUserId();
        UserDTO userDTO = new UserDTO(userId, 0);
        userDAO.saveUser(userDTO);
        return ResponseEntity.ok().build();
    }

}
