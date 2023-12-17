package views.screen.payment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.paypal.base.rest.PayPalRESTException;
import controller.PaymentController;
import controller.PlaceOrderController;
import entity.invoice.Invoice;
import entity.payment.PaymentTransaction;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import subsystem.paypal.PaypalController;
import subsystem.vnpay.VNPayController;
import subsystem.vnpay.entity.PaymentResponse;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;

public class PaymentScreenHandler extends BaseScreenHandler {

    @FXML
    private Button btnConfirmPayment;

    private Invoice invoice;

    @FXML
    private RadioButton paypal;
    @FXML
    private RadioButton vnpay;


    public PaymentScreenHandler(Stage stage, String screenPath, int amount, String contents) throws IOException {
        super(stage, screenPath);
    }

    public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        btnConfirmPayment.setOnMouseClicked(e -> {
            try {
                confirmToPayOrder();
            } catch (Exception exp) {
                System.out.println(exp.getStackTrace());
            }
        });
    }

    /**
     * @throws IOException
     */
    void confirmToPayOrder() throws IOException, PayPalRESTException {
        Map<String, String> response = new Hashtable<String, String>();
        if (paypal.isSelected()) {
            Stage webViewStage = new Stage();
            WebView webView = new WebView();
            PaypalController paypalController = new PaypalController();
            String approvalLink = paypalController.authorizePayment(invoice);
            System.out.println(approvalLink);
            webView.getEngine().load(approvalLink);

            Scene scene = new Scene(webView, 1000, 800);
            webViewStage.setScene(scene);
            webViewStage.show();
            final String[] paymentId = {""};
            try {
                webView.getEngine().getLoadWorker().stateProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            System.out.println(newValue);
                            if (newValue == Worker.State.SUCCEEDED) {
                                String url = webView.getEngine().getLocation();
                                System.out.println(url);
                                URI uri = null;
                                if(url.startsWith("http://127.0.0.1:5500/confirm.html")) {
                                    try {
                                        uri = new URI(url);
                                        paymentId[0] = uri.getQuery().split("&")[0].split("=")[1];
                                        System.out.println(paymentId[0]);
                                    } catch (URISyntaxException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                                if (webView.getEngine().getLocation().startsWith("http://127.0.0.1:5500/success.html")) {
                                    Map<String, String> transactionInfo = new Hashtable<>();
                                    transactionInfo.put("cardHolder", (String) invoice.getOrder().getDeliveryInfo().get("name"));
                                    transactionInfo.put("id", paymentId[0]);
                                    transactionInfo.put("content", "Thanh toan don hang");
                                    transactionInfo.put("amount", String.valueOf((invoice.getOrder().getShippingFees()+invoice.getOrder().getAmount()) * 1000));
                                    transactionInfo.put("time", Utils.getCurrentTime());
                                    getBController().saveTransaction(transactionInfo);
                                    response.put("RESULT", "PAYMENT SUCCESSFUL!");
                                    response.put("MESSAGE", "You have successfully paid for your order. Your order will be in a waiting status for approval!");
                                    webViewStage.close();
                                    goToSuccessfulScreen(response, transactionInfo);
                                } else if (webView.getEngine().getLocation().startsWith("http://127.0.0.1:5500/fail.html")) {
                                    response.put("RESULT", "PAYMENT FAILED!");
                                    response.put("MESSAGE", "You have failed to pay for your order.");
                                    webViewStage.close();
                                    goToFailScreen(response);
                                }
                            }
                        });
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else if(vnpay.isSelected()){
            Stage webViewStage = new Stage();
            WebView webView = new WebView();
            VNPayController vnPayController = new VNPayController();
            PaymentResponse paymentResponse = vnPayController.createPayment(invoice);

            System.out.println(paymentResponse.getURL());
            webView.getEngine().load(paymentResponse.getURL());

            Scene scene = new Scene(webView, 1000, 800);
            webViewStage.setScene(scene);
            webViewStage.show();
            try {
                webView.getEngine().getLoadWorker().stateProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            System.out.println(newValue);
                            if (newValue == Worker.State.SUCCEEDED) {
                                System.out.println(webView.getEngine().getLocation());
                                if (webView.getEngine().getLocation().contains("vnp_ResponseCode=00")) {
                                    String url = webView.getEngine().getLocation();
                                    Map<String, String> transactionInfo = getBController().readTransanctionFromResponseURL(url);
                                    transactionInfo.put("cardHolder", (String) invoice.getOrder().getDeliveryInfo().get("name"));
                                    getBController().saveTransaction(transactionInfo);
                                    response.put("RESULT", "PAYMENT SUCCESSFUL!");
                                    response.put("MESSAGE", "You have successfully paid for your order. Your order will be in a waiting status for approval!");
                                    webViewStage.close();
                                    goToSuccessfulScreen(response, transactionInfo);
                                } else if (webView.getEngine().getLocation().contains("vnp_ResponseCode=24")) {
                                    response.put("RESULT", "PAYMENT FAILED!");
                                    response.put("MESSAGE", "You have failed to pay for your order");
                                    webViewStage.close();
                                    goToFailScreen(response);
                                }
                            }
                        });
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    void goToSuccessfulScreen(Map<String, String> response, Map<String, String> transactionInfo){
        try {
            BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH,
                    response.get("RESULT"), response.get("MESSAGE"), transactionInfo);
            resultScreen.setPreviousScreen(this);
            resultScreen.setHomeScreenHandler(homeScreenHandler);
            resultScreen.setScreenTitle("Result Screen");
            resultScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void goToFailScreen(Map<String, String> response){
        try {
            BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH,
                    response.get("RESULT"), response.get("MESSAGE"));
            resultScreen.setPreviousScreen(this);
            resultScreen.setHomeScreenHandler(homeScreenHandler);
            resultScreen.setScreenTitle("Result Screen");
            resultScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PaymentController getBController() {
        return (PaymentController) super.getBController();
    }
}