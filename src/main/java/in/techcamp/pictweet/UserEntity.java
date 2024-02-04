package in.techcamp.pictweet;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<TweetEntity> tweets;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<CommentEntity> comments;
}
