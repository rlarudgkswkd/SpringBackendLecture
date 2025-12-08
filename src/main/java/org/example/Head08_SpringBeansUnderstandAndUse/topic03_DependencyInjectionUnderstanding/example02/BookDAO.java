package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example01;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Repository :
 * - @Component의 세분화 어노테이션 중 하나로 "데이터 접근 계층"에서 사용
 * - Spring이 자동으로 Bean 등록 및 예외 변환(AOP) 적용
 */

@Repository
public class BookDAO {

    // 간단한 메모리 기반 DB 대체 컬렉션
    private static final List<BookDTO> books = new ArrayList<>();

    // 정적 블록: DAO 생성 시 초기 더미 데이터 세팅
    static {
        books.add(new BookDTO(1, "토비의 스프링 3.1", "이일민", 45000));
        books.add(new BookDTO(2, "자바 ORM 표준 JPA 프로그래밍", "김영한", 38000));
        books.add(new BookDTO(3, "스프링 인 액션", "Craig Walls", 42000));
        books.add(new BookDTO(4, "Effective Java", "Joshua Bloch", 55000));
    }

    /** 도서 전체 목록 조회 */
    public List<BookDTO> selectBookList() {
        System.out.println("BookDAO: 전체 도서 목록 조회");
        return books;
    }

    /** 번호로 한 권 검색 */
    public BookDTO selectOneBook(int sequence) {
        System.out.println("BookDAO: 도서 번호 " + sequence + " 조회");

        return books.stream()
                .filter(book -> book.getSequence() == sequence)
                .findFirst()
                .orElse(null);
    }
}
