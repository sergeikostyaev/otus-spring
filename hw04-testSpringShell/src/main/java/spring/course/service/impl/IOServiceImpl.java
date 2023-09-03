package spring.course.service.impl;

import org.springframework.stereotype.Service;
import spring.course.service.IOService;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {
    @Override
    public void print(String s) {
        System.out.println(s);
    }

    @Override
    public String readLine() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
