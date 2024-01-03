package com.thiago.restspringjava.service;

import com.thiago.restspringjava.controller.PersonController;
import com.thiago.restspringjava.data.vo.v1.PersonVO;
import com.thiago.restspringjava.exception.RequiredObjectIsNullException;
import com.thiago.restspringjava.exception.ResourceNotFoundException;
import com.thiago.restspringjava.mapper.DozerMapper;
import com.thiago.restspringjava.mapper.custom.PersonMapper;
import com.thiago.restspringjava.model.Person;
import com.thiago.restspringjava.repository.PersonRepository;
import com.thiago.restspringjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username + "!");
        var user = repository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
