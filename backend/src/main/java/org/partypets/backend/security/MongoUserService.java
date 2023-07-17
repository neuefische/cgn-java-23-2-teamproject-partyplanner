package org.partypets.backend.security;

import org.springframework.stereotype.Service;

@Service
public class MongoUserService {

    private final MongoUserRepository userRepository;

    public MongoUserService(MongoUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MongoUser getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow();
    }
}
