package in.techcamp.pictweet;

//import lombok.RequiredArgsConstructor;
//import org.apache.catalina.User;
import jakarta.persistence.EntityNotFoundException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private  CommentRepository commentRepository;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Integer userId = userDetails.getId();
            model.addAttribute("userId",userId);
        }
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
//        User authenticatedUser = (User) authentication.getPrincipal();
        CustomUserDetails authenticatedUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        String username = authenticatedUser.getUsername();
//        String username = authenticatedUserDetails.getUsername();
//        UserEntity user = userRepository.findByUsername(username);
        UserEntity user = authenticatedUserDetails.getUserEntity();
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
    public String showTweetDetail(@PathVariable("tweetId") Integer tweetId,
                                  @ModelAttribute("commentEntity") CommentEntity commentEntity,
                                  Model model){
        TweetEntity tweet;

        try {
            tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("Tweet not found:" + tweetId));
        } catch (EntityNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        List<CommentEntity> comments = commentRepository.findByTweet_id(tweetId);
        model.addAttribute("comments",comments);
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


    @PostMapping("/user/{userId}/tweet/{tweetId}/update")
    public String update(Authentication authentication,
                         @ModelAttribute("tweet") TweetEntity tweetEntity,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("tweetId") Integer tweetId,
                         Model model
    ) {

        String username = authentication.getName();

        TweetEntity tweet;
        UserEntity user;

        try {
            tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + tweetId));
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (user.getUsername().equals(username)) {
            try {
                tweet.setContent(tweetEntity.getContent());
                tweet.setImage(tweetEntity.getImage());

                tweetRepository.save(tweet);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "ツイートの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/tweet/" + tweetId;
    }

    @PostMapping("/user/{userId}/tweet/{tweetId}/delete")
    public String update(Authentication authentication,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("tweetId") Integer tweetId,
                         Model model
    ) {

        String username = authentication.getName();

        UserEntity user;

        try {
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (user.getUsername().equals(username)) {
            try {
                tweetRepository.deleteById(tweetId);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "ツイートの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchTweets(@RequestParam(name = "keyword") String keyword, Model model) {
        List<TweetEntity> tweetList;

        if (keyword != "" ) {
            tweetList = tweetRepository.findByContentContaining(keyword);
        } else {
            tweetList = tweetRepository.findAll();
        }

        model.addAttribute("tweets", tweetList);
        return "search";
    }
}
