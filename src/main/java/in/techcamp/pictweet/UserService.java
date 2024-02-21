package in.techcamp.pictweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    public void registerNewUser(UserEntity userEntity){
        String password = userEntity.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity user = new UserEntity();
        user.setUsername(userEntity.getUsername());
        user.setPassword(encodedPassword);

        userRepository.save(user);

//        // `UsernamePasswordAuthenticationToken` を作成
//        UsernamePasswordAuthenticationToken authReq
//                = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), password);
//        Authentication auth = authenticationManager.authenticate(authReq);
//
//        // `SecurityContextHolder` に認証オブジェクトを設定
//        SecurityContextHolder.getContext().setAuthentication(auth);

        // 自動ログイン
        autoLogin(userEntity.getUsername(), password);
    }
    private void autoLogin(String username, String password) {
        UserDetails userDetails = userAuthenticationService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
