package org.example.service;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RolesRepository;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;
import org.example.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userrepository;

    @Autowired
    private JwtUtil jwtutil;

   @Autowired
   private PasswordEncoder passwordEncoder;

   private final RolesRepository rolesRepository;

   public UserService(RolesRepository rolesRepository){
       this.rolesRepository = rolesRepository;
   }

    public User createUser(UserVO uservo, Set<String> roleNames) {

            if (userrepository.findByUsername(uservo.getUsername()).isPresent()) {
                throw new RuntimeException("username already exists");
            }



        if(uservo.getUsername() == null || uservo.getUsername().trim().isEmpty() || uservo.getPassword() == null || uservo.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Username and password must not be empty");}
            User user = new User();
            user.setUsername(uservo.getUsername());
            String encodedPassword = passwordEncoder.encode(uservo.getPassword());
            user.setPassword(encodedPassword);
            Set<Role> roles = roleNames.stream()
                            .map(roleName -> rolesRepository.findByRole(roleName)
                                    .orElseThrow(()->new RuntimeException("role not found: "+roleName)))
                                    .collect(Collectors.toSet());
            user.setRoles(roles);
            return userrepository.save(user);

            

    }
        public String login(UserVO uservo){
        Optional<User> optionaluser = userrepository.findByUsername(uservo.getUsername());
            if(uservo.getUsername() == null || uservo.getUsername().trim().isEmpty() || uservo.getPassword() == null || uservo.getPassword().trim().isEmpty()) {
                throw new RuntimeException("Username and password must not be empty");
            }
        if(optionaluser.isEmpty()) {
            throw new RuntimeException("username not found");
        }
        User user = optionaluser.get();
        if(!passwordEncoder.matches(uservo.getPassword(),user.getPassword())){
            throw new RuntimeException("invalid username or password");
        }
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getRole())
                    .collect(Collectors.toList());

        return jwtutil.generateToken(user.getUsername(),roles);
    }

    public User findByUsername(String username){
        return userrepository.findByUsername(username)
                .orElse(null);
    }
    }

