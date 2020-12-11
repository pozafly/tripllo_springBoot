package com.pozafly.tripllo.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Header<T> {

    // api 통신시간
    private LocalDateTime transactionTime;

    // api 응답코드
    private String resultCode;

    // api 부가설명
    private String description;

    // api data
    private T data;

    // OK : 기본적인 OK
    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()   //Header<T> 로 형변환 해줘야 함.
                .transactionTime(LocalDateTime.now())
                .resultCode("200")
                .description("성공!")
                .build();
    }

    // DATA OK : 데이터가 들어있는 OK
    public static <T> Header<T> OK(T data){  //매개변수로 data를 받고,
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("200")
                .description("성공!")
                .data(data)    //data를 쏴주도록 하자.
                .build();
    }

    // ERROR : 설명을 가지고 있고 data는 없는 ERROR
    public static <T> Header<T> ERROR(String description){   //에러메세지를 매개변수로
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)   //여기서 에러메세지 쏴줌.
                .build();
    }

}