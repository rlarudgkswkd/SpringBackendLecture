package com.example.springtdd.Head04_MockitoBasic.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountService {

    int getTotalCount();

    List<Integer> getCountList();
}
