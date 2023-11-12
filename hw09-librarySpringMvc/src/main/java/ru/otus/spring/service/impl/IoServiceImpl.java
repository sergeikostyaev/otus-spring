package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.IoService;

import java.util.Scanner;

@Service
public class IoServiceImpl implements IoService {

    @Override
    public void print(String s) {
        System.out.println(s);
    }

}
