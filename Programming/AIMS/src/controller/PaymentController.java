package controller;

import java.net.URI;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import common.exception.InvalidCardException;
import entity.cart.Cart;
import entity.invoice.Invoice;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
public class PaymentController extends BaseController {

  /**
   * Represent the card used for payment
   */
  private CreditCard card;

  /**
   * Validate the input date which should be in the format "mm/yy", and then
   * return a {@link java.lang.String String} representing the date in the
   * required format "mmyy" .
   * 
   * @param date - the {@link java.lang.String String} represents the input date
   * @return {@link java.lang.String String} - date representation of the required
   *         format
   * @throws InvalidCardException - if the string does not represent a valid date
   *                              in the expected format
   */
  private String getDate(String date) throws InvalidCardException {
    String[] strs = date.split("/");
    if (strs.length != 2) {
      throw new InvalidCardException();
    }

    String datee = null;
    int month = -1;
    int year = -1;

    try {
      month = Integer.parseInt(strs[0]);
      year = Integer.parseInt(strs[1]);
      if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
        throw new InvalidCardException();
      }
     datee  = strs[0] + strs[1];

    } catch (Exception ex) {
      throw new InvalidCardException();
    }

    return datee;
  }


  public Map<String, String> readTransanctionFromResponseURL(String url) {
    Map<String, String> transactionInfo = new Hashtable<String, String>();
    try {
      URI uri = new URI(url);

      String query = uri.getQuery();

      String[] params = query.split("&");

      Map<String, String> paramMap = new HashMap<>();
      for (String param : params) {
        String[] keyValue = param.split("=");
        String key = keyValue[0];
        String value = keyValue.length > 1 ? keyValue[1] : "";
        paramMap.put(key, value);
      }
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      transactionInfo.put("id", paramMap.get("vnp_BankTranNo"));
      transactionInfo.put("content", paramMap.get("vnp_OrderInfo"));
      int amount = Integer.parseInt(paramMap.get("vnp_Amount")) / 100;
      transactionInfo.put("amount", String.valueOf(amount));
      transactionInfo.put("time", String.valueOf(sdf.parse(paramMap.get("vnp_PayDate"))));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return transactionInfo;
  }

  public void saveTransaction(Map<String, String> transactionInfo){
    PaymentTransaction transaction = new PaymentTransaction(transactionInfo.get("cardHolder"), transactionInfo.get("id"),
            transactionInfo.get("content"),Integer.parseInt(transactionInfo.get("amount")), transactionInfo.get("time"));
    try {
      PaymentTransaction.saveTransaction(transaction);
      System.out.println("Saved transaction to database");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    System.out.println(transactionInfo);
  }

  public void emptyCart() {
    Cart.getCart().emptyCart();
  }
}