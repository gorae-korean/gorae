package gorae.backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductName {
    FIRST_CLASS("First Class"),
    FOUR_CLASSES_ONE_TO_TWO("4 Classes (1:2)"),
    FOUR_CLASSES_ONE_TO_FOUR("4 Classes (1:4)"),
    EIGHT_CLASSES_ONE_TO_TWO("8 Classes (1:2)"),
    EIGHT_CLASSES_ONE_TO_FOUR("8 Classes (1:4)");

    private final String key;
}
