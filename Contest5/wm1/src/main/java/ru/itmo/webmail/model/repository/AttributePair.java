package ru.itmo.webmail.model.repository;

public class AttributePair<T> {
    private final String attribute;
    private final T value;

    public AttributePair(String attribute, T value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public T getValue() {
        return value;
    }

    public boolean matches(T pattern) {
        return this.value.equals(pattern);
    }
}
