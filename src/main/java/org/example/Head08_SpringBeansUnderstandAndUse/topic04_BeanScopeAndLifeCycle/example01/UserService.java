package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example01;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")  // 생략해도 기본값이 singleton
public class UserService {

    // ⚠ 싱글톤 Bean에 상태 필드를 두면 여러 요청이 섞일 수 있어서 위험
    // private String currentUser; // 이렇게 하면 안 됨

    private final UserRepository userRepository;

    // 생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }
}