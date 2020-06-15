package ro.sdi.lab.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sdi.lab.core.model.User;
import ro.sdi.lab.core.repository.UserRepository;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public User getUserByUserName(String userName)
    {
        return userRepository.getUserByUserName(userName);
    }
}