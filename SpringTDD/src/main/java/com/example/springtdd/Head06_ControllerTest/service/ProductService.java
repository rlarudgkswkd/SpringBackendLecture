package com.example.springtdd.Head06_ControllerTest.service;


import com.example.springtdd.Head06_ControllerTest.dto.CreateProductRequest;
import com.example.springtdd.Head06_ControllerTest.dto.PageResponse;
import com.example.springtdd.Head06_ControllerTest.dto.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductService {

    PageResponse<ProductResponse> getProducts(Pageable pageable, String category);

    ProductResponse createProduct(CreateProductRequest request, MultipartFile image);

    ProductResponse getProductById(Long id);
}
