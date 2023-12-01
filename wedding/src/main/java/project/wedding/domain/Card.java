package project.wedding.domain;

import java.util.Date;

/**
 * TodoCard의 속성을 지닌 객체
 */
public class Card {
	private Status status;
	private String title;
	private String todo;
	private Date date;
    private int id;

    public Card(Status status, String title, String todo, Date date, int id) {
        this.status = status;
        this.title = title;
        this.todo = todo;
        this.date = date;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
