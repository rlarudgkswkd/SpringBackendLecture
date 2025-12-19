package org.example.Head09_SpringMVC.topic05_APItest.example01;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //여러 스레드가 동시에 접근해도 안전하게 증가하는 숫자(ID)를 생성하기 위한 카운터
    private final AtomicLong seq = new AtomicLong(0);
    private final Map<Long, UserDto> db = new ConcurrentHashMap<>();

    // 1) 사용자 생성: POST /api/users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@RequestBody CreateUserRequest req) {
        long id = seq.incrementAndGet();
        UserDto user = new UserDto(id, req.name());
        db.put(id, user);

        // Postman에서 id만 쉽게 뽑기 위해 id를 최상위로 둠
        return Map.of(
                "id", id,
                "user", user
        );
    }

    // 2) 사용자 조회: GET /api/users/{id}
    @GetMapping("/{id}")
    public UserDto get(@PathVariable long id) {
        UserDto user = db.get(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found. id=" + id);
        }
        return user;
    }

    // 3) 사용자 삭제: DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        db.remove(id);
    }

    public record CreateUserRequest(String name) {}
    public record UserDto(long id, String name) {}
}