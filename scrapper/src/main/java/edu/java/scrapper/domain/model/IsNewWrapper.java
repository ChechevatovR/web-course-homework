package edu.java.scrapper.domain.model;

public class IsNewWrapper<T> {
    public final boolean isNew;
    public final T value;

    public IsNewWrapper(boolean isNew, T value) {
        this.isNew = isNew;
        this.value = value;
    }
}
