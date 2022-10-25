package vttp.caf.moneytree.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import vttp.caf.moneytree.models.ERole;
import vttp.caf.moneytree.models.Role;
import vttp.caf.moneytree.models.User;
import vttp.caf.moneytree.payload.request.LoginRequest;
import vttp.caf.moneytree.payload.request.SignupRequest;
import vttp.caf.moneytree.payload.response.MessageResponse;
import vttp.caf.moneytree.payload.response.UserInfoResponse;
import vttp.caf.moneytree.security.jwt.JwtUtils;
import vttp.caf.moneytree.security.repositories.RoleRepository;
import vttp.caf.moneytree.security.repositories.UserRepository;
import vttp.caf.moneytree.security.services.UserDetailsImpl;
import vttp.caf.moneytree.services.UserService;


// @CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
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

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpSession sess) {

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

    sess.setAttribute("username", username);

    // return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
    //     .body(new UserInfoResponse(userDetails.getUsername()));

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                   roles));
  }

  // @PostMapping("/signup")
  // public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
  //   if (userService.checkIfUserExist(signUpRequest.getUsername())) {
  //     return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
  //   }
  
  //   // Create new user's account    
  //   boolean added = userService.addUser(signUpRequest.getUsername(),
  //       encoder.encode(signUpRequest.getPassword()));
  //   if(added){
  //       return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  //   }
  //   else {
  //       return ResponseEntity.ok(new MessageResponse("Username already exists, please try again!"));
  //   }
    
  // }
  
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

 

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error 1: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error 2: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error 3: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error 4: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser(HttpSession sess) {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    sess.invalidate();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
