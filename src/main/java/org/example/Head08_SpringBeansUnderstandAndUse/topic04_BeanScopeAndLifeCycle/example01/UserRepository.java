package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example01;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}