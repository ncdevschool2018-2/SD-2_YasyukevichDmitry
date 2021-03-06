package com.netcracker.edu.backend.fapi.controller;

import com.netcracker.edu.backend.fapi.config.TokenProvider;
import com.netcracker.edu.backend.fapi.model.AuthToken;
import com.netcracker.edu.backend.fapi.model.LoginUser;
import com.netcracker.edu.backend.fapi.model.User;
import com.netcracker.edu.backend.fapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }
    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    public ResponseEntity<User> findUserByToken(@PathVariable(name="token") String token) throws AuthenticationException {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.findUserByUsername(username);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
