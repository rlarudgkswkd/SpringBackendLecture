package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* @Service : @Component의 세분화 어노테이션의 한 종류로 Service 계층에서 사용한다.
 *
 * 필드 주입은 간단해 보이지만 여러 문제점이 있어 권장되지 않는다.
 */
@Service("bookServiceField")
public class BookService {

    /* BookDAO 타입의 빈 객체를 이 프로퍼티에 자동으로 주입해준다.
     *
     * 필드 주입의 문제점:
     * 1. final 키워드 사용 불가 - 불변성 보장 못함
     * 2. 외부에서 변경 불가 - 테스트 어려움
     * 3. 순환 참조를 런타임에 발견
     * 4. Spring 컨테이너에 강한 의존성
     */
    @Autowired
    private BookDAO bookDAO;  // final 사용 불가

    // 기본 생성자만 가능
    public BookService() {
        System.out.println("BookService 생성 - bookDAO는 아직 null");
        // 이 시점에서 bookDAO는 null
        // Spring이 리플렉션으로 나중에 주입
    }

    /* 도서 목록 전체 조회 */
    public List<BookDTO> selectAllBooks(){
        // 필드 주입은 null 체크가 어려움
        // @Autowired가 실패하면 런타임에 NPE 발생
        return bookDAO.selectBookList();
    }

    /* 도서 번호로 도서 조회 */
    public BookDTO searchBookBySequence(int sequence) {
        return bookDAO.selectOneBook(sequence);
    }
}
