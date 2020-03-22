package com.example.auto2;

import com.example.auto2.config.JwtUtil;
import com.example.auto2.model.JwtRequest;
import com.example.auto2.model.UserD;
import com.example.auto2.repository.UserRepository;
import com.example.auto2.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/userData", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello(){
        return "Hello new User";
    }



    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserD> findAll(){
        return userRepository.findAll();
    }



    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getName(), jwtRequest.getPassword()));
        }catch(BadCredentialsException e){
            throw new Exception("Incorrect username and password", e);
            }
//        final UserD userD = (UserD)userDetailsService.loadUserByUsername(jwtRequest.getName());

        UserD userD = userRepository.findByName(jwtRequest.getName());

        String token = jwtUtil.generateToken(userD.getName(), userD.getRole());

        Map<Object, Object> model = new HashMap<>();
        model.put("username", userD.getName());
        model.put("token", token);

        return ResponseEntity.ok(model);
    }
}