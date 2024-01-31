package in.techcamp.pictweet;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TweetEntity {
    private long id;
    private String content;
    private String image;

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
