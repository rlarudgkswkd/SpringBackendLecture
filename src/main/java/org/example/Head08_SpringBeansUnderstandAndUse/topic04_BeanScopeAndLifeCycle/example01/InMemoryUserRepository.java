package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example01;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> userDb = new ConcurrentHashMap<>();

    public InMemoryUserRepository() {
        // 더미 데이터
        save(new User("alice", "password1"));
        save(new User("bob", "password2"));
    }

    @Override
    public User findByUsername(String username) {
        System.out.println("[InMemoryUserRepository] 사용자 조회: " + username);
        return userDb.get(username);
    }

    @Override
    public void save(User user) {
        System.out.println("[InMemoryUserRepository] 사용자 저장: " + user);
        userDb.put(user.getUsername(), user);
    }
}