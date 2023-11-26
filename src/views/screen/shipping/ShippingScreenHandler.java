package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import controller.ViewCartController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

  @FXML
  private Label screenTitle;

  @FXML
  private TextField name;

  @FXML
  private TextField phone;
  @FXML
  private TextField email;
  @FXML
  private TextField address;

  @FXML
  private TextField instructions;

  @FXML
  private ComboBox<String> province;
  @FXML
  private ComboBox<String> shippingMethod;
  private VBox vbox;
  private Label labelShippingFee;
  @FXML
  private Label labelAmount;
  @FXML
  private Label labelSubtotal;
  @FXML
  private Label labelVAT;
  private Label labelTotal;

  private Order order;

  public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
    super(stage, screenPath);
    this.order = order;
  }

  /**
   * @param arg0
   * @param arg1
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
    name.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue && firstTime.get()) {
        content.requestFocus(); // Delegate the focus to container
        firstTime.setValue(false); // Variable value changed for future references
      }
    });
    this.province.getItems().addAll(Configs.PROVINCES);
    this.shippingMethod.getItems().addAll(Configs.SHIPPING_METHOD);
  }
//  void updateCartAmount() {
//    int subtotal = Cart.getCart().calSubtotal();
//    int vat = (int)(Configs.PERCENT_VAT / 100.0F * (float)subtotal);
//    int amount = subtotal + vat;
//    this.labelSubtotal.setText(Utils.getCurrencyFormat(subtotal));
//    this.labelVAT.setText(Utils.getCurrencyFormat(vat));
//    this.labelAmount.setText(Utils.getCurrencyFormat(amount));
//  }

//  private void displayCartWithMediaAvailability() {
//    List lstMedia = this.getBController().getListCartMedia();
//
//    try {
//      Iterator var2 = lstMedia.iterator();
//
//      while(var2.hasNext()) {
//        Object cm = var2.next();
//        CartMedia cartMedia = (CartMedia)cm;
//        MediaHandler mediaCartScreen = new MediaHandler("/views/fxml/media_cart.fxml", this);
//        mediaCartScreen.setCartMedia(cartMedia);
//        this.vbox.getChildren().add(mediaCartScreen.getContent());
//      }
//
//      this.updateCartAmount();
//    } catch (IOException var6) {
//      var6.printStackTrace();
//    }
//
//  }
  /**
   * @param event
   * @throws IOException
   * @throws InterruptedException
   * @throws SQLException
   */
  @FXML
  void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

    // add info to messages
    HashMap messages = new HashMap<>();
    messages.put("name", name.getText());
    messages.put("phone", phone.getText());
    messages.put("email", this.email.getText());
    messages.put("address", address.getText());
    messages.put("instructions", instructions.getText());
    messages.put("province", province.getValue());
    messages.put("shippingMethod", this.shippingMethod.getValue());
//    try {
//      // process and validate delivery info
//      if(!getBController().processDeliveryInfo(messages)){
//        PopupScreen.error("Invalid shipping information!");
//      }
//    } catch (InvalidDeliveryInfoException e) {
//      throw new InvalidDeliveryInfoException(e.getMessage());
//    }

    // calculate shipping fees
    int shippingFees = getBController().calculateShippingFee(order);
    order.setShippingFees(shippingFees);
    order.setDeliveryInfo(messages);
    if (((String)this.shippingMethod.getValue()).equals("STANDARD DELIVERY")) {
      Invoice invoice = this.getBController().createInvoice(this.order);
      BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, "/views/fxml/invoice.fxml", invoice);
      InvoiceScreenHandler.setPreviousScreen(this);
      InvoiceScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
      InvoiceScreenHandler.setScreenTitle("Invoice Screen");
      InvoiceScreenHandler.setBController(this.getBController());
      InvoiceScreenHandler.show();
    }
    else {
      int result = PlaceRushOrderController.checkRushDelivery((String)this.province.getValue(),this.order.getlstOrderMedia());
      if(result==1){
        PopupScreen.error("Sorry. Your address doesn't support rush delivery. Please try again!");
        return;
      }

      if (result==2) {
        PopupScreen.error("Sorry. There are no products supported rush delivery. Please try again!");
        return;
      }
      BaseScreenHandler DeliveryMethodsScreenHandler = new RushDeliveryScreenHandler(this.stage, Configs.DELIVERY_METHODS_PATH, this.order);
      DeliveryMethodsScreenHandler.setPreviousScreen(this);
      DeliveryMethodsScreenHandler.setHomeScreenHandler(homeScreenHandler);
      DeliveryMethodsScreenHandler.setScreenTitle("Rush delivery screen");
      DeliveryMethodsScreenHandler.setBController(getBController());
      DeliveryMethodsScreenHandler.show();
    }
  }
  @FXML
  private void handleBack(MouseEvent event) throws IOException, SQLException {
    // Back to previous screen
    CartScreenHandler cartScreen = new CartScreenHandler(this.stage, Configs.CART_SCREEN_PATH);
    cartScreen.setHomeScreenHandler(homeScreenHandler);
    cartScreen.setBController(new ViewCartController());
    cartScreen.requestToViewCart(homeScreenHandler);
  }
  /**
   * @return PlaceOrderController
   */
  public PlaceOrderController getBController() {
    return (PlaceOrderController) super.getBController();
  }

  public void notifyError() {
    // TODO: implement later on if we need
  }

}
