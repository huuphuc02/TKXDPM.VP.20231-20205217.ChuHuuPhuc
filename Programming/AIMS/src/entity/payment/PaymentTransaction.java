package entity.payment;

import entity.db.AIMSDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentTransaction {
	private String transactionId;
	private String transactionContent;
	private int amount;
	private String createdAt;

	private String cardHolder;

	public PaymentTransaction(String cardHolder, String transactionId, String transactionContent,int amount, String createdAt) {
		super();
		this.cardHolder = cardHolder;
		this.transactionId = transactionId;
		this.transactionContent = transactionContent;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	public static void saveTransaction(PaymentTransaction transaction) throws SQLException {
		String sql = "INSERT INTO PaymentTransaction(id, content, transactionTime, amount, cardHolderName) VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(sql);

		preparedStatement.setString(1, transaction.getTransactionId());
		preparedStatement.setString(2, transaction.getTransactionContent());
		preparedStatement.setString(3, transaction.getCreatedAt());
		preparedStatement.setInt(4, transaction.getAmount());
		preparedStatement.setString(5, transaction.getCardHolder());
		System.out.println(preparedStatement);
		preparedStatement.executeUpdate();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getTransactionContent() {
		return transactionContent;
	}

	public int getAmount() {
		return amount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public String toString(){
		return "\nTransactionID: " + transactionId +
				"\nCard holder name: " + cardHolder +
				"\nTransaction content: " + transactionContent +
				"\nAmount: " + amount + " VND" +
				"\nTransaction time: " + createdAt;
	}
}
