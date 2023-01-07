package com.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestReactor {

    public static void main(String[] args) {

        // just方法直接声明相关元素
        Flux.just(1,2,3,4).subscribe(System.out::println);
        Mono.just(1).subscribe(System.out::println);

        // 声明数组
        Integer[] array = {1,2,3,4};
        Flux.fromArray(array);

        // 声明集合
        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);

        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);
    }
}
