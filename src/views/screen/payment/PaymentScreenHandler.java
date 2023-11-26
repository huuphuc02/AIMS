package views.screen.payment;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.paypal.base.rest.PayPalRESTException;
import controller.PaymentController;
import entity.invoice.Invoice;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import subsystem.paypal.PaypalServices;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class PaymentScreenHandler extends BaseScreenHandler {

    @FXML
    private Button btnConfirmPayment;

    @FXML
    private ImageView loadingImage;

    private Invoice invoice;

    @FXML
    private Label pageTitle;
    @FXML
    private RadioButton paypal;
    @FXML
    private RadioButton vnpay;
    @FXML
    private RadioButton domesticCard;
    @FXML
    private RadioButton interCard;

    @FXML
    private VBox vbox;
    @FXML
    private TextField cardNumber;

    @FXML
    private TextField holderName;

    @FXML
    private TextField expirationDate;

    @FXML
    private TextField securityCode;
    @FXML
    private TextField issueDate;
    @FXML
    private TextField email;
    @FXML
    private TextField nation;
    @FXML
    private TextField city;
    @FXML
    private TextField address;


    public PaymentScreenHandler(Stage stage, String screenPath, int amount, String contents) throws IOException {
        super(stage, screenPath);
    }

    public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        btnConfirmPayment.setOnMouseClicked(e -> {
            try {
                confirmToPayOrder();
                ((PaymentController) getBController()).emptyCart();
            } catch (Exception exp) {
                System.out.println(exp.getStackTrace());
            }
        });
    }

    @FXML
    public void handlePaymentMethod(ActionEvent event) {
        if(this.vnpay.isSelected()){
            this.vbox.setVisible(true);
            this.domesticCard.setVisible(true);
            this.interCard.setVisible(true);
        }
        else if(this.paypal.isSelected()){
            this.vbox.setVisible(false);
            this.domesticCard.setVisible(false);
            this.interCard.setVisible(false);
        }
    }

    @FXML
    public void handleCardType(ActionEvent event) {
        this.cardNumber.setDisable(false);
        this.holderName.setDisable(false);
        if(this.domesticCard.isSelected()){
            this.issueDate.setDisable(false);
        }
        else if(this.interCard.isSelected()){
            this.expirationDate.setDisable(false);
            this.securityCode.setDisable(false);
            this.email.setDisable(false);
            this.nation.setDisable(false);
            this.city.setDisable(false);
            this.address.setDisable(false);
        }
    }

    /**
     * @throws IOException
     */
    void confirmToPayOrder() throws IOException, PayPalRESTException {
        Map<String, String> response = new Hashtable<String, String>();;
        if (paypal.isSelected()) {
            Stage webViewStage = new Stage();
            WebView webView = new WebView();
            PaypalServices paypalServices = new PaypalServices();
            String approvalLink = paypalServices.authorizePayment(invoice);
            System.out.println(approvalLink);
            webView.getEngine().load(approvalLink);

            Scene scene = new Scene(webView, 1000, 800);
            webViewStage.setScene(scene);
            webViewStage.show();
            try {
                webView.getEngine().getLoadWorker().stateProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            System.out.println(newValue);
                            if (newValue == Worker.State.SUCCEEDED) {
                                System.out.println(webView.getEngine().getLocation());
                                if (webView.getEngine().getLocation().startsWith("http://127.0.0.1:5500/success.html")) {

                                    response.put("RESULT", "PAYMENT SUCCESSFUL!");
                                    response.put("MESSAGE", "You have successfully paid for your order!");
                                    webViewStage.close();
//                                    webView.getEngine().load("about:blank");
                                    BaseScreenHandler resultScreen = null;
                                    try {
                                        resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH,
                                                response.get("RESULT"), response.get("MESSAGE"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    resultScreen.setPreviousScreen(this);
                                    resultScreen.setHomeScreenHandler(homeScreenHandler);
                                    resultScreen.setScreenTitle("Result Screen");
                                    resultScreen.show();

                                } else if (webView.getEngine().getLocation().startsWith("http://127.0.0.1:5500/fail.html")) {
                                    response.put("RESULT", "PAYMENT FAILED!");
                                    response.put("MESSAGE", "You have failed to pay for your order");
                                    webViewStage.close();
                                    BaseScreenHandler resultScreen = null;
                                    try {
                                        resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH,
                                                response.get("RESULT"), response.get("MESSAGE"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    resultScreen.setPreviousScreen(this);
                                    resultScreen.setHomeScreenHandler(homeScreenHandler);
                                    resultScreen.setScreenTitle("Result Screen");
                                    resultScreen.show();
                                }
                            }
                        });
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
//    PaymentController ctrl = (PaymentController) getBController();
//    Map<String, String> response = ctrl.paypalOrder(invoice);
//

    }

}