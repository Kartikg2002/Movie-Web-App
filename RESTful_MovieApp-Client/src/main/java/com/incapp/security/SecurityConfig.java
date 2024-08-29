package com.incapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import com.incapp.beans.MyUser;
import static org.springframework.security.config.Customizer.withDefaults;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests	
				.requestMatchers("/WEB-INF/**").permitAll()
				.requestMatchers("/signUp").permitAll()
				.requestMatchers("/moreDetails").hasRole("USER")
				.requestMatchers("/adminLogin","/updateMovie","/deleteMovie").hasRole("ADMIN")
				.requestMatchers("/home","/viewMovies").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login")
						.defaultSuccessUrl("/home", true)
						.failureUrl("/login?error=true") // Redirect to login with error parameter
						.permitAll())
				
				//oauth2
				.oauth2Login(oauth2 -> oauth2.loginPage("/oauth2/authorization/google") // Separate login page for
																						// Google OAuth2
						.defaultSuccessUrl("/home", true)

						.userInfoEndpoint(userInfo -> userInfo.userService(new DefaultOAuth2UserService() {
							@Override
							public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
								OAuth2User oauth2User = super.loadUser(userRequest);

								// Extract user details
								String email = oauth2User.getAttribute("email");
								String name = oauth2User.getAttribute("name");
								System.out.println(email + name);

								// Set default role
								SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

								// Wrap the OAuth2User with additional authorities
								return new DefaultOAuth2User(Collections.singleton(authority),oauth2User.getAttributes(), "email");
							}
						})).permitAll())
				
				.logout((logout) -> logout.logoutSuccessUrl("/login").permitAll()) //to redirect to specific page			
				.exceptionHandling(handling -> handling.accessDeniedPage("/accessDenied"));

		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	RestTemplate restTemplate = new RestTemplate();
	String URL = "http://localhost:8787";

	@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        
        //using anonymous class
		UserDetailsService customUserDetailsService=new UserDetailsService(){
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				String API = "/findByUsername/"+username;
				
				MyUser user = restTemplate.getForObject(URL+API,MyUser.class);
				
		        if (user == null) {
		            throw new UsernameNotFoundException("User not found");
		        }
		        //This User class belong to this package-> org.springframework.security.core.userdetails.User
		        return 
	        		new User(
	  		        user.getUsername(),
	  		        user.getPassword(), // Encrypting password,  
	  		        user.isEnabled(),
	  		        true, // accountNonExpired
	  		        true, // credentialsNonExpired
	  		        true, // accountNonLocked
	  		        List.of(new SimpleGrantedAuthority(user.getRole())));  		
			}
		};
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
	
}
