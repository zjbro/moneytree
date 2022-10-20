package vttp.caf.moneytree.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.caf.moneytree.payload.request.LoginRequest;
import vttp.caf.moneytree.payload.request.SignupRequest;
import vttp.caf.moneytree.payload.response.MessageResponse;
import vttp.caf.moneytree.payload.response.UserInfoResponse;
import vttp.caf.moneytree.security.jwt.JwtUtils;
import vttp.caf.moneytree.security.services.UserDetailsImpl;
import vttp.caf.moneytree.services.UserService;


// @CrossOrigin(origins = "*", maxAge = 3600)
// @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getUsername()));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userService.checkIfUserExist(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }
  
    // Create new user's account    
    boolean added = userService.addUser(signUpRequest.getUsername(),
        encoder.encode(signUpRequest.getPassword()));
    if(added){
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    else {
        return ResponseEntity.ok(new MessageResponse("Username already exists, please try again!"));
    }
    
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser(HttpSession sess) {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    sess.invalidate();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
