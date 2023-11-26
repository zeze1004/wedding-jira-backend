package project.wedding.domain;

import java.util.Date;

public class TodoCard {
	private Status status;
	private int id;
	private String title;
	private String todo;
	private Date date;

	public TodoCard() {
		this.status = Status.BACKLOG;
	}
}