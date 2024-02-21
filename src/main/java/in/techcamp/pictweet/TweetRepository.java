package in.techcamp.pictweet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<TweetEntity, Integer> {
    List<TweetEntity> findByUser_id(Integer userId);
    List<TweetEntity> findByContentContaining(String keyword);
}


//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//
//import java.util.List;
//
//@Mapper
//public interface TweetRepository {
//    @Select("select * from tweets")
//    List<TweetEntity> findAll();
//
//    @Insert("insert into tweets (content,image) values (#{content}, #{image})")
//    void insertTweet(@Param("content")String content, @Param("image")String image);
//}



