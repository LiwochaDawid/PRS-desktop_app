package prs.models;

public class AccountModel {
	private String username;
	private String password;

	public AccountModel(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
