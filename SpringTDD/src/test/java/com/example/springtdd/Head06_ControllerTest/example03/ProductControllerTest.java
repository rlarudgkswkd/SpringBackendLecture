package com.example.springtdd.Head06_ControllerTest.example03;

import com.example.springtdd.Head06_ControllerTest.config.TestConfig;
import com.example.springtdd.Head06_ControllerTest.controller.ProductController;
import com.example.springtdd.Head06_ControllerTest.dto.product.CreateProductRequest;
import com.example.springtdd.Head06_ControllerTest.dto.PageResponse;
import com.example.springtdd.Head06_ControllerTest.dto.product.ProductResponse;
import com.example.springtdd.Head06_ControllerTest.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class) // 웹 계층만 로드 (Controller 중심 테스트)
@Import(TestConfig.class) // Security 등 추가 설정 주입
class ProductControllerTest {

    @Autowired private MockMvc mockMvc; // HTTP 요청 시뮬레이션 도구
    @Autowired private ObjectMapper objectMapper;
    @Autowired private WebApplicationContext webApplicationContext;

    @MockBean private ProductService productService; // Service는 Mock으로 대체

    @BeforeEach
    void setUp() {
        // UTF-8 인코딩 필터를 추가하여 한글 깨짐 방지
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("상품 목록 조회시 페이징과 정렬이 올바르게 처리되어야 한다")
    void getProductsWithPagingAndSorting() throws Exception {

        // Given
        // 서비스가 반환할 데이터 구성
        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1L, "상품1", 10000, "카테고리A", "image-url"),
                new ProductResponse(2L, "상품2", 20000, "카테고리B", "image-url")
        );

        // Page 형태 응답 구성 (content + page 정보)
        PageResponse<ProductResponse> pageResponse =
                new PageResponse<>(
                        products,
                        new PageResponse.PageInfo(0, 10, 2, 1, true, false)
                );

        // 서비스 Mock 동작 정의
        // Pageable과 category를 받아 위에서 만든 응답 반환
        when(productService.getProducts(
                org.mockito.ArgumentMatchers.any(Pageable.class),
                eq("카테고리A")
        )).thenReturn(pageResponse);

        // When & Then
        // 실제 HTTP 요청을 시뮬레이션
        mockMvc.perform(get("/api/products")
                        .param("page", "0")       // 페이지 번호
                        .param("size", "10")      // 페이지 크기
                        .param("sort", "name,asc")// 정렬 조건
                        .param("category", "카테고리A") // 추가 필터 조건
                        .accept(MediaType.APPLICATION_JSON))
                // HTTP 응답 상태 검증
                .andExpect(status().isOk())

                // 응답 데이터 구조 검증
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name").value("상품1"))

                // 페이지 정보 검증
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.size").value(10))
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.page.first").value(true))
                .andExpect(jsonPath("$.page.last").value(false));

        // Then
        // Controller → Service로 전달된 Pageable 값 검증
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(productService).getProducts(captor.capture(), eq("카테고리A"));

        Pageable pageable = captor.getValue();

        // 전달된 페이징 정보 확인
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());

        // 정렬 조건 검증
        assertEquals("name: ASC", pageable.getSort().toString());
    }

    @Test
    @DisplayName("파일 업로드가 포함된 상품 등록 요청 처리")
    @WithMockUser
    void createProductWithFileUpload() throws Exception {

        // Given - 업로드 파일
        MockMultipartFile productImage = new MockMultipartFile(
                "image",
                "product.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        // JSON 데이터 (DTO)
        MockMultipartFile productData = new MockMultipartFile(
                "product",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(
                        new CreateProductRequest("신상품", 50000, "카테고리C")
                ).getBytes()
        );

        ProductResponse createdProduct =
                new ProductResponse(1L, "신상품", 50000, "카테고리C", "image-url");

        when(productService.createProduct(
                org.mockito.ArgumentMatchers.any(CreateProductRequest.class),
                org.mockito.ArgumentMatchers.any(MultipartFile.class)
        )).thenReturn(createdProduct);

        // When & Then
        mockMvc.perform(multipart("/api/products")
                        .file(productImage)
                        .file(productData)
                        .with(csrf()) // POST multipart → 필수
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("신상품"))
                .andExpect(jsonPath("$.imageUrl").value("image-url"));

        // 전달값 검증
        verify(productService).createProduct(
                argThat(req -> req.getName().equals("신상품")),
                argThat(file -> file.getOriginalFilename().equals("product.jpg"))
        );
    }

    @Test
    @DisplayName("커스텀 헤더가 포함된 요청 처리")
    @WithMockUser
    void requestWithCustomHeaders() throws Exception {

        // Given - 커스텀 헤더 값 설정
        String apiKey = "test-api-key-123";
        String clientVersion = "1.2.3";

        // 서비스 응답 Mock 설정
        when(productService.getProductById(1L)).thenReturn(
                new ProductResponse(1L, "테스트상품", 30000, "카테고리D", "image-url")
        );

        // When & Then - 헤더 포함 요청 검증
        mockMvc.perform(get("/api/products/{id}", 1L)
                        .header("X-API-Key", apiKey)
                        .header("X-Client-Version", clientVersion)
                        .header("X-Request-ID", UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("테스트상품"));

        // 서비스 호출 검증
        verify(productService).getProductById(1L);
    }
}