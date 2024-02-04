package in.techcamp.pictweet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TweetControllerUnitTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TweetController tweetController = new TweetController();

    @Test
    public void testShowTweetDetail() {
        TweetEntity expectedTweet = new TweetEntity();

        List<CommentEntity> expectedComments = new ArrayList<>();

        CommentEntity comment1 = new CommentEntity();
        comment1.setId(1);
        comment1.setMessage("This is a comment.");
        expectedComments.add(comment1);

        when(tweetRepository.findById(any(Integer.class))).thenReturn(Optional.of(expectedTweet));
        when(commentRepository.findByTweet_id(any(Integer.class))).thenReturn(expectedComments);

        Model model = new ExtendedModelMap();
        String result = tweetController.showTweetDetail(1, new CommentEntity(), model);

        assertThat(result, is("detail"));
        assertThat(model.getAttribute("t"), is(expectedTweet));
        assertThat(model.getAttribute("comments"), is(expectedComments));
    }
}