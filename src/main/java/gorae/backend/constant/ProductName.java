package gorae.backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductName {
    FIRST_CLASS("First Class"),
    ONE_CLASS("1 Class"),
    FOUR_CLASSES("4 Classes"),
    EIGHT_CLASSES("8 Classes");

    private final String key;
}
