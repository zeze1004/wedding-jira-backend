package org.wedding.domain.todo;

public enum TodoCheckStatus {
    CHECKED, UNCHECKED;

    @Override
    public String toString() {
        return name();
    }
}
