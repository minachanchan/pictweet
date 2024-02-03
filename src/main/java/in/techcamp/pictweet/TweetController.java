package in.techcamp.pictweet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TweetController {
    private final TweetRepository tR;

//    @GetMapping("/tweets")
//    @ResponseBody
//    public String showHello(){
//        return "<h1>Hello World</h1>";
//    }

    @GetMapping
    public String showList(Model model){
//        var tweetList = List.of(
//                new TweetEntity(1, "投稿1", "https://tech-master.s3.amazonaws.com/uploads/curriculums/images/Rails1-4/sample.jpg"),
//                new TweetEntity(2, "投稿2", ""),
//                new TweetEntity(3, "投稿3", "")
//        );
        var tweetList = tR.findAll();
        model.addAttribute("tweets",tweetList);
        return "index";
    }

    @GetMapping("/tweetForm")
    public String showTweetForm(@ModelAttribute("tweetF") TweetForm form){
        return "newTweetForm";
    }

    @PostMapping("/tweets")
    public String saveTweet(TweetForm form){
        tR.insertTweet(form.getContent(), form.getImage());
        return "redirect:/";
    }
}