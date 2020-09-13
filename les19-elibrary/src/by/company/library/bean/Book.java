package by.company.library.bean;

import java.io.Serializable;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private int price;
	private Role access;
	
	public Book() {
		this.title = "Unknown";
		this.price = 50;
		this.access = Role.JUNIOR;
	}
	
	public Book(String title, int price) {
		this.title = title;
		this.price = price;
		this.access = Role.JUNIOR;
	}
	
	public Book(String title, int price, Role access) {
		this.title = title;
		this.price = price;
		this.access = access;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Role getAccess() {
		return access;
	}

	public void setAccess(Role access) {
		this.access = access;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((access == null) ? 0 : access.hashCode());
		result = prime * result + price;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (access != other.access)
			return false;
		if (price != other.price)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", price=" + price + ", access=" + access + "]";
	}
}
