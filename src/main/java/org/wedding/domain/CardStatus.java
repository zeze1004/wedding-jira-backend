package org.wedding.domain;

public enum CardStatus {
    BACKLOG, PROGRESS, DONE;

    @Override
    public String toString() {
        return name();
    }
}
