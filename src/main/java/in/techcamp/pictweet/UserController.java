package in.techcamp.pictweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registerForm")
    public String register(@ModelAttribute("user")UserEntity userEntity){
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user")UserEntity userEntity, Model model){
        try {
//            userRepository.save(userEntity);
            userService.registerNewUser(userEntity);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "名前かパスワードが間違っています。");
        }
        return "login";
    }

    @GetMapping("/user/{userId}")
    public String showUserDetail(@PathVariable("userId") Integer userId,
                                 @ModelAttribute("tweetEntity") TweetEntity tweetEntity,
                                 Model model){
        UserEntity user;

        try {
//         ※tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new EntityNotFoundException("Tweet not found:" + tweetId));
        } catch (EntityNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        List<TweetEntity> tweets = tweetRepository.findByUser_id(userId);
        model.addAttribute("tweets",tweets);
//        model.addAttribute("user", user);
        return  "userDetail";
    }
}
