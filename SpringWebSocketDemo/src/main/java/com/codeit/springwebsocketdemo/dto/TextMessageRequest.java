package com.codeit.springwebsocketdemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TextMessageRequest {

    private String sender;
    private String content;
}