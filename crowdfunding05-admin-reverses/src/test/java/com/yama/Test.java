package com.yama;

public class Test {
    public static void main(String[] args) {
        Person person1 = new Person("李莹", 26);
        Person person2 = person1;

        person2.setAge(30);

        System.out.println(person1);
    }
}
