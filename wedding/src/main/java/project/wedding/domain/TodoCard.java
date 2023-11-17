package project.wedding.domain;

import java.util.Date;

import lombok.Setter;

@Setter
public class TodoCard {
	private static int cardCount = -1;
	public String status;
	public int id;
	public String title;
	public String todo;
	public Date date;

	public TodoCard() {
		this.id = ++cardCount;
		this.status = "Backlog";
	}
}
