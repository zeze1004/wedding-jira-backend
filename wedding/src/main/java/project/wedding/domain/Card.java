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

    // 신규 카드 생성
	public Card() {
		this.status = Status.BACKLOG;
	}

    // 카드 이동
    public Card(Status status) {
        this.status = status;
    }

    public Card(String title, String todo) {
        this.title = title;
        this.todo = todo;
    }

    public Card(Date date) {
        this.date = date;
    }
}