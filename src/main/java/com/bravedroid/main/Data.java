package com.bravedroid.main;

import java.util.UUID;

public  class Data {
        public String id = UUID.randomUUID().toString();
        public String name;
        public int age;

        public Data(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
