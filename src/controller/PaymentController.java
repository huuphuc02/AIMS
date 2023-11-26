package controller;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import com.paypal.base.rest.PayPalRESTException;
import common.exception.InvalidCardException;
import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import entity.invoice.Invoice;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import subsystem.InterbankInterface;
import subsystem.InterbankSubsystem;
import subsystem.paypal.PaypalServices;

/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 * 
 * @author hieud
 *
 */
public class PaymentController extends BaseController {

  /**
   * Represent the card used for payment
   */
  private CreditCard card;

  /**
   * Represent the Interbank subsystem
   */
  private InterbankInterface interbank;

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


  /**
   * Pay order, and then return the result with a message.
   * 
   * @param amount         - the amount to pay
   * @param contents       - the transaction contents
   * @param cardNumber     - the card number
   * @param cardHolderName - the card holder name
   * @return {@link java.util.Map Map} represent the payment result with a
   *         message.
   */
  public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
      String issueDate) {
    Map<String, String> result = new Hashtable<String, String>();
    result.put("RESULT", "PAYMENT FAILED!");
    try {
      this.card = new CreditCard(cardNumber, cardHolderName,
          getDate(issueDate));

      this.interbank = new InterbankSubsystem();
      PaymentTransaction transaction = interbank.payOrder(card, amount, contents);

      result.put("RESULT", "PAYMENT SUCCESSFUL!");
      result.put("MESSAGE", "You have successfully paid the order!");
    } catch (PaymentException | UnrecognizedException ex) {
      result.put("MESSAGE", ex.getMessage());
    }
    return result;
  }

  public Map<String, String> paypalOrder(Invoice invoice) {
    Map<String, String> result = new Hashtable<String, String>();
    result.put("RESULT", "PAYMENT FAILED!");
    try {
      PaypalServices paypalServices = new PaypalServices();
      String approvalLink = paypalServices.authorizePayment(invoice);


      result.put("RESULT", "PAYMENT SUCCESSFUL!");
      result.put("MESSAGE", "You have successfully paid the order!");
    } catch (PaymentException | UnrecognizedException ex) {
      result.put("MESSAGE", ex.getMessage());
    } catch (PayPalRESTException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public void emptyCart() {
    Cart.getCart().emptyCart();
  }
}