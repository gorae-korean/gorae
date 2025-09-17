package gorae.backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductName {
    FIRST_CLASS("First Class"),
    ONE_CLASS_ONE_TO_TWO("1 Class"),
    ONE_CLASS_ONE_TO_FOUR("1 Class"),
    FOUR_CLASSES_ONE_TO_TWO("4 Classes"),
    FOUR_CLASSES_ONE_TO_FOUR("4 Classes"),
    EIGHT_CLASSES_ONE_TO_TWO("8 Classes"),
    EIGHT_CLASSES_ONE_TO_FOUR("8 Classes");

    private final String key;
}
