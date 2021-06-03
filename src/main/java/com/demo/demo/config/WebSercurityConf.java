package com.demo.demo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSercurityConf extends WebSecurityConfigurerAdapter {

//    public userdetail GetUserDetailsService(){
//        return new userdetail();
//    }

    @Autowired
    public UserDetailsService UesrDetail;

    public PasswordEncoder GetPassWordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //    认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.UesrDetail).passwordEncoder(GetPassWordEncoder());
        super.configure(auth);
    }

    //    权

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//   authorizeRequests()匹配所有request请求
//   antMatchers("/", "/home")过滤器，具体筛选 authorizeRequests() 匹配到的请求
//   authenticated()需要认证
        http.authorizeRequests()
                .antMatchers("/test/1").hasRole("admin").and().authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().rememberMe();
    }
}


@Service
class  userdetail implements UserDetailsService{

    public ArrayList<GrantedAuthority> GetAouthL(){
        ArrayList<GrantedAuthority> AouthList = new ArrayList<GrantedAuthority>();
        AouthList.add(new SimpleGrantedAuthority("admin"));
        AouthList.add(new SimpleGrantedAuthority("user"));
        return AouthList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException {
       return new User("wy","123456",GetAouthL());
    }
}