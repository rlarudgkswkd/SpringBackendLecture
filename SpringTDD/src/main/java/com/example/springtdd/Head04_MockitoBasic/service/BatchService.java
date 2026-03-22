package com.example.springtdd.Head04_MockitoBasic.service;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;

import java.util.List;

public interface BatchService {

    boolean processUsers(List<UserNew> users);

    String processEmails(List<String> emails);
}
