package in.techcamp.pictweet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
//    UserEntity findByUser_id(Integer userID);
}
