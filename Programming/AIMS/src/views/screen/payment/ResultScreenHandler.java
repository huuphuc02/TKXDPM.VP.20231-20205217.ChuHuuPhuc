package views.screen.payment;

import java.io.IOException;
import java.util.Map;

import entity.cart.Cart;
import entity.payment.PaymentTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class ResultScreenHandler extends BaseScreenHandler {

  private String result;
  private String message;
  private Map<String, String> transactionInfo;

  public ResultScreenHandler(Stage stage, String screenPath, String result, String message, Map<String, String> transactionInfo) throws IOException {
    super(stage, screenPath);
    resultLabel.setText(result);
    messageLabel.setText(message);
    cardHolder.setText(transactionInfo.get("cardHolder"));
    transactionID.setText(transactionInfo.get("id"));
    amount.setText(transactionInfo.get("amount"));
    transactionContent.setText(transactionInfo.get("content"));
    transactionTime.setText(transactionInfo.get("time"));
  }

  public ResultScreenHandler(Stage stage, String screenPath, String result, String message) throws IOException {
    super(stage, screenPath);
    resultLabel.setText(result);
    messageLabel.setText(message);
    resultLabel.setTextFill(Color.RED);
    messageLabel.setTextFill(Color.RED);
    transactionPane.setVisible(false);
  }

  @FXML
  private Label pageTitle;

  @FXML
  private Label resultLabel;

  @FXML
  private Button okButton;

  @FXML
  private Label messageLabel;

  @FXML
  private Pane transactionPane;

  @FXML
  private Text transactionID;
  @FXML
  private Text amount;
  @FXML
  private Text transactionContent;
  @FXML
  private Text transactionTime;
  @FXML
  private Text cardHolder;

  /**
   * @param event
   * @throws IOException
   */
  @FXML
  void returnHome(MouseEvent event) throws IOException {
    Cart.getCart().emptyCart();
    homeScreenHandler.show();
  }

}