package in.techcamp.pictweet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // ここでは権限は使っていないようなので空のリストを返します
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // アカウントが期限切れでないことを示す
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // アカウントがロックされていないことを示す
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 資格情報が期限切れでないことを示す
    }

    @Override
    public boolean isEnabled() {
        return true; // アカウントが有効であることを示す
    }

    // 追加したメソッド: ユーザーのIDを取得
    public Integer getId() {
        return userEntity.getId();
    }

}