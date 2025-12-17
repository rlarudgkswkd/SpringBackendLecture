package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example03;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        /* AnnotationConfigApplicationContext 생성자에 basePackages 문자열을 전달하며
         * ApplicationContext 생성한다.
         *
         * Spring Container 초기화 과정:
         * 1. 지정된 패키지 스캔
         * 2. @Component 계열 어노테이션 찾기
         * 3. Bean 등록 및 의존성 주입
         */
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example03");

        // Bean 이름으로 조회
        BookService bookService = context.getBean("bookServiceField", BookService.class);

        /* 전체 도서 목록 조회 후 출력 확인 */
        System.out.println("=== 전체 도서 목록 ===");
        bookService.selectAllBooks().forEach(System.out::println);

        /* 도서번호로 검색 후 출력 확인 */
        System.out.println("\n=== 개별 도서 조회 ===");
        System.out.println(bookService.searchBookBySequence(1));
        System.out.println(bookService.searchBookBySequence(2));

        // 컨텍스트 종료
        context.close();
    }
}
