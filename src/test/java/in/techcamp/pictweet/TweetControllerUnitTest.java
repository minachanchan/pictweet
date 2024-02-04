package in.techcamp.pictweet;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TweetControllerUnitTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TweetController tweetController = new TweetController();
}