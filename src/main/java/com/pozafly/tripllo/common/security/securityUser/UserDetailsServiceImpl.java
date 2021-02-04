package com.pozafly.tripllo.common.security.securityUser;

import com.pozafly.tripllo.user.dao.UserDao;
import com.pozafly.tripllo.user.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 시도하려는 유저정보 조회
        log.info("security loadUserByUsername에서 유저 조회");
        User user = userDao.readUser(username);

        // 조회가 되지않는 고객은 에러발생.
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        // 조회한 정보를 userCustom에 담는다.
        // 만약 파라미터를 추가해야한다면 UserCustom 을 먼저 수정한다.
        return new SecurityUser() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(user.getRole()));  // Role을 위해
                return authorities;
            }
            @Override
            public String getPassword() {
                return user.getPassword();
            }
            @Override
            public String getUsername() {
                return user.getId();
            }
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
