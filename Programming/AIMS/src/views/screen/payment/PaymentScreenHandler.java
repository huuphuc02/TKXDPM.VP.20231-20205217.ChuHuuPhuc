package views.screen.payment;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.paypal.base.rest.PayPalRESTException;
import controller.PaymentController;
import entity.invoice.Invoice;
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
                ((PaymentController) getBController()).emptyCart();
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
                                    goToResultScreen(response);

                                } else if (webView.getEngine().getLocation().startsWith("http://127.0.0.1:5500/fail.html")) {
                                    response.put("RESULT", "PAYMENT FAILED!");
                                    response.put("MESSAGE", "You have failed to pay for your order");
                                    webViewStage.close();
                                    goToResultScreen(response);
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
                                    response.put("RESULT", "PAYMENT SUCCESSFUL!");
                                    response.put("MESSAGE", "You have successfully paid for your order!");
                                    webViewStage.close();
                                    goToResultScreen(response);
                                } else if (webView.getEngine().getLocation().contains("vnp_ResponseCode=24")) {
                                    response.put("RESULT", "PAYMENT FAILED!");
                                    response.put("MESSAGE", "You have failed to pay for your order");
                                    webViewStage.close();
                                    goToResultScreen(response);
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

    void goToResultScreen(Map<String, String> response){
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

}