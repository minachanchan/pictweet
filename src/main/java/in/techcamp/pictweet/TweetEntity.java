package in.techcamp.pictweet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

//@AllArgsConstructor
@Data
@Table(name = "tweets")
@Entity
public class TweetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String content;
    private String image;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private  UserEntity user;

    @ToString.Exclude
    @OneToMany(mappedBy = "tweet",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private List<CommentEntity> comments;

//    public TweetEntity(long id, String content, String image) {
//        this.id = id;
//        this.content = content;
//        this.image = image;
//    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
