package views.screen.shipping;

import java.io.IOException;
import java.util.HashMap;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;

public class RushDeliveryScreenHandler extends BaseScreenHandler {

  private Order order;

  @FXML
  private TextField deliveryInstruction;

  @FXML
  private TextField shipmentDetail;

  @FXML
  private DatePicker deliveryTime;

  @FXML
  private Button updateDeliveryMethodInfoButton;

  public RushDeliveryScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
    super(stage, screenPath);
    this.order = order;
  }

  
  /** 
   * @param event
   * @throws IOException
   */
  @FXML
  private void updateDeliveryMethodInfo(MouseEvent event) throws IOException {
    String deliveryInstructionString = new String(deliveryInstruction.getText());
    String shipmentDetailString = new String(shipmentDetail.getText());
    String deliveryDateString = new String();
    if (deliveryTime.getValue() != null) {
        deliveryDateString = new String(deliveryTime.getValue().toString());
    }

    HashMap<String, String> deliveryData = new HashMap<String, String>();
    deliveryData.put("deliveryInstruction", deliveryInstructionString);
    deliveryData.put("shipmentDetail", shipmentDetailString);
    deliveryData.put("deliveryDate", deliveryDateString);

    PlaceRushOrderController.validatePlaceRushOrderData(deliveryData);

    // // create invoice screen
    Invoice invoice = getBController().createInvoice(order);
    BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
    InvoiceScreenHandler.setPreviousScreen(this);
    InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
    InvoiceScreenHandler.setScreenTitle("Invoice Screen");
    InvoiceScreenHandler.setBController(getBController());
    InvoiceScreenHandler.show();
  }

  
  /** 
   * @param event
   * @throws IOException
   */
  @FXML
  private void handleBack(MouseEvent event) throws IOException {
    // Back to previous screen
    BaseScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH,
        this.order);
    ShippingScreenHandler.setPreviousScreen(this);
    ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
    ShippingScreenHandler.setScreenTitle("Shipping Screen");
    ShippingScreenHandler.setBController(getBController());
    ShippingScreenHandler.show();
  }

  /**
   * @return PlaceOrderController
   */
  public PlaceOrderController getBController() {
    return (PlaceOrderController) super.getBController();
  }
}
