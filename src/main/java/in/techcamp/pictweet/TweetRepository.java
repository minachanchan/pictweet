package in.techcamp.pictweet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<TweetEntity, Integer> {
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



