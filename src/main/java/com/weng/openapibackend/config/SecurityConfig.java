package com.weng.openapibackend.config;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weng.openapibackend.filter.JwtAuthenticationFilter;
import com.weng.openapibackend.mapper.UserMapper;
import com.weng.openapibackend.model.entity.User;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
{
    @Lazy
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    private UserMapper userMapper;

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager=new ProviderManager(authenticationProvider);
        return providerManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/auth/login","/auth/register",
                                "/doc.html","/webjars/**","/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //默认是IF_REQUIRED,即如果需要就创建一个session。
                //这里改为STATELESS，不创建session。因为每次请求携带的jwt信息包含了用户信息
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return username -> {
            LambdaQueryWrapper<User> enumUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
            enumUserLambdaQueryWrapper.eq(User::getUsername, username);
            User user = userMapper.selectOne(enumUserLambdaQueryWrapper);
            if (user == null)
            {
                throw new UsernameNotFoundException("用户名不存在");
            }
            return user;
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
