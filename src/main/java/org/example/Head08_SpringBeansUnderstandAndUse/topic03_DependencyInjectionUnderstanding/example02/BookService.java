package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* @Service : @Component의 세분화 어노테이션의 한 종류로 Service 계층에서 사용한다.
 * Spring이 컴포넌트 스캔을 통해 이 클래스를 찾아 Bean으로 등록한다.
 */
@Service("bookServiceConstructor")  // Bean 이름을 명시적으로 지정
public class BookService {

    // final 키워드 사용 - 불변성 보장
    // 한 번 주입되면 변경 불가능
    private final BookDAO bookDAO;

    /* BookDAO 타입의 bean 객체를 생성자에 자동으로 주입해준다.
     * Spring 4.3 이후 생성자가 하나만 있으면 @Autowired 생략 가능
     *
     * 생성자 주입의 장점:
     * 1. 순환 참조를 컴파일 시점에 발견
     * 2. final 키워드 사용으로 불변성 보장
     * 3. 테스트 시 Mock 객체 주입 용이
     * 4. NPE(NullPointerException) 방지
     */
    @Autowired  // 생성자가 하나만 있다면 생략 가능
    public BookService(BookDAO bookDAO) {
        // null 체크를 추가할 수도 있음
        if (bookDAO == null) {
            throw new IllegalArgumentException("BookDAO는 필수입니다");
        }
        this.bookDAO = bookDAO;
        System.out.println("BookService 생성자 호출 - BookDAO 주입 완료");
    }

    // 비즈니스 메서드
    public List<BookDTO> selectAllBooks(){
        System.out.println("전체 도서 목록 조회 시작");
        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequence) {
        System.out.println("도서 번호 " + sequence + " 조회 시작");
        return bookDAO.selectOneBook(sequence);
    }
}