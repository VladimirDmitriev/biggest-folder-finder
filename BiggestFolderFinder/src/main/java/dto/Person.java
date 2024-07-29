package dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Person {
    private String name;
    private int age;
    private boolean isMarried;
    private List<String> hobbies;
    private List<String> children;
    private Car car;
    private Job job;

    public Person() {
    }
    //сетерры и геттеры
}

