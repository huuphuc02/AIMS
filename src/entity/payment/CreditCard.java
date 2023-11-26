package entity.payment;

import java.sql.Timestamp;

public class CreditCard {
	private String cardCode;
	private String owner;
	private int cvvCode;
	private String bank;
	private String dateExpired;
	private String dateIssued;
	private String email;
	private String nation;
	private String city;
	private String address;

	public CreditCard(String cardCode, String owner, String dateIssued) {
		super();
		this.cardCode = cardCode;
		this.owner = owner;
		this.dateIssued = dateIssued;
	}

	public CreditCard(String cardCode, String owner, int cvvCode, String dateExpired, String email, String nation, String city, String address) {
		this.cardCode = cardCode;
		this.owner = owner;
		this.cvvCode = cvvCode;
		this.dateExpired = dateExpired;
		this.email = email;
		this.nation = nation;
		this.city = city;
		this.address = address;
	}
}
