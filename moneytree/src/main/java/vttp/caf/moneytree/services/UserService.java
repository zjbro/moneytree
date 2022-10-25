package vttp.caf.moneytree.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.caf.moneytree.repositories.UsersRepository;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepo;

    public boolean authenticate(String username, String password) {
        return 1 == userRepo.countUsersByUsernameAndPassword(username, password);
    }

    public boolean checkIfUserExist(String username) {
        return 1 == userRepo.countUsersByUsername(username);
    }

    public boolean addUser(String username, String password){
        return userRepo.addUser(username, password);
    }
    
}
