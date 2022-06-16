package triple.review.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import triple.review.dto.UserDTO;


@Repository
@Mapper
public interface UserDAO {
    UserDTO selectUser(UserDTO param);
    void saveUser(UserDTO param);
}
