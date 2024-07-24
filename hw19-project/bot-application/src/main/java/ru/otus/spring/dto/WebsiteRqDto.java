package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteRqDto {

    private String ouid;
    private String tel;
    private String dostavka_metod;
    private String adres_id;
    private String street;
    private String house;
    private String porch;
    private String door_phone;
    private String flat;
    private String delivtime;
    private String dop_info;
    private String cart_question_answer;
    private String order_metod;
    private String send_to_order;
    private String d;
    private String nav;
    private String mail;
    private String date;
    private String datas;
    private String uid;
    private Orders orders;
    private Status status;
    private String user;
    private String sum;
    private String bonus_minus;
    private String bonus_plus;
    private String company;
    private String fio;

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Orders {
        private Cart cart;
        private Person person;

        @Getter
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Cart {
            private Map<String, CartItem> cart;
            private String num;
            private String sum;
            private String weight;
            private String dostavka;

            @Getter
            @Builder
            @ToString
            @NoArgsConstructor
            @AllArgsConstructor
            public static class CartItem {
                private String id;
                private String name;
                private String price;
                private String price_n;
                private String price_purch;
                private String uid;
                private String num;
                private String ed_izm;
                private String pic_small;
                private String weight;
                private String category;
                private String total;
            }
        }

        @Getter
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Person {
            private String ouid;
            private String data;
            private String time;
            private String mail;
            private String name_person;
            private String dostavka_metod;
            private String discount;
            private String user_id;
            private String order_metod;
        }
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status {
        private String maneger;
        private String time;
    }

}
