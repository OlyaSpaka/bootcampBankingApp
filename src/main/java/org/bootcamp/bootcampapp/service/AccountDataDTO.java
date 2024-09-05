package org.bootcamp.bootcampapp.service;

import java.text.DecimalFormat;

public class AccountDataDTO {

	private int id;
	private String login;
	private String password;
	private String name;
	private String surname;
	private double balance;
	private String currency;

	DecimalFormat df = new DecimalFormat("0.00");

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getBalance() {
		return Double.parseDouble(df.format(balance));
	}

	public void setBalance(double balance) {
		this.balance = Double.parseDouble(df.format(balance));
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
