package in.techcamp.pictweet;

//import lombok.RequiredArgsConstructor;
//import org.apache.catalina.User;
import jakarta.persistence.EntityNotFoundException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequiredArgsConstructor
public class TweetController {
//    private final TweetRepository tweetRepository;
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

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
//        var tweetList = tweetRepository.findAll();
        List<TweetEntity> tweetList = tweetRepository.findAll();
        model.addAttribute("tweets",tweetList);
        return "index";
    }

    @GetMapping("/tweetForm")
//    public String showTweetForm(@ModelAttribute("tweetF") TweetForm form){
//        return "newTweetForm";
//    }
    public String showTweetForm(@ModelAttribute("tweetF") TweetEntity tweetEntity){
        return "new";
    }

    @PostMapping("/tweets")
//    public  String createTweet(TweetForm form,
      public  String createTweet(@ModelAttribute("tweetForm") TweetEntity tweetEntity,
                               Authentication authentication,
                               Model model){
        User authenticatedUser = (User) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        tweetEntity.setUser(user);
        try{
//            tweetRepository.insert(form.getContent(),form.getImage());
            tweetRepository.save(tweetEntity);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "redirect:/";
    }
//    public String saveTweet(TweetForm form){
//        tweetRepository.insertTweet(form.getContent(), form.getImage());
//        return "redirect:/";
//    }

   @GetMapping("/tweet/{tweetId}")
    public String showTweetDetail(@PathVariable("tweetId") Integer tweetId, Model model){
        TweetEntity tweet;

        try {
            tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("Tweet not found:" + tweetId));
        } catch (EntityNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        model.addAttribute("t", tweet);
        return  "detail";
    }


    @GetMapping("/user/{userId}/tweet/{tweetId}/edit")
    public String edit(@PathVariable("tweetId") Integer tweetId, Model model){

        TweetEntity tweet;

        try{
            tweet = tweetRepository.findById(tweetId)
                    .orElseThrow(() -> new EntityNotFoundException("Tweet not found:" + tweetId));
        }catch (EntityNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        model.addAttribute("tweet", tweet);
        return  "edit";
    }




}
