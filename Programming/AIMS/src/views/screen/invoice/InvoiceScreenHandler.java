package views.screen.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import controller.PlaceOrderController;
import controller.ViewCartController;
import entity.invoice.Invoice;
import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.payment.PaymentScreenHandler;
import views.screen.shipping.ShippingScreenHandler;

public class InvoiceScreenHandler extends BaseScreenHandler {

	private static Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

	@FXML
	private Label pageTitle;

	@FXML
	private Label name;

	@FXML
	private Label phone;
	@FXML
	private Label email;

	@FXML
	private Label province;

	@FXML
	private Label address;

	@FXML
	private Label instructions;

	@FXML
	private Label subtotal;
	@FXML
	private Label amount;

	@FXML
	private Label shippingFees;

	@FXML
	private Label total;

	@FXML
	private VBox vboxItems;

	private Invoice invoice;

	public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		setInvoiceInfo();
	}

	private void setInvoiceInfo(){
		HashMap<String, String> deliveryInfo = invoice.getOrder().getDeliveryInfo();
		name.setText(deliveryInfo.get("name"));
		province.setText(deliveryInfo.get("province"));
		phone.setText(deliveryInfo.get("phone"));
		email.setText(deliveryInfo.get("email"));
		instructions.setText(deliveryInfo.get("instructions"));
		address.setText(deliveryInfo.get("address"));
		subtotal.setText(Utils.getCurrencyFormat((int) invoice.getOrder().getSubTotal()));
		amount.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmount()));
		shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().getShippingFees()));
		int amount = invoice.getOrder().getAmount() + invoice.getOrder().getShippingFees();
		total.setText(Utils.getCurrencyFormat(amount));
		invoice.setAmount(amount);
		invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
			try {
				MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
				mis.setOrderMedia((OrderMedia) orderMedia);
				vboxItems.getChildren().add(mis.getContent());
			} catch (IOException | SQLException e) {
				System.err.println("errors: " + e.getMessage());
				throw new ProcessInvoiceException(e.getMessage());
			}
			
		});

	}

	@FXML
	private void handleBack(MouseEvent event) throws IOException, SQLException {
		// Back to previous screen
		ShippingScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH, invoice.getOrder());
		ShippingScreenHandler.setPreviousScreen(this);
		ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
		ShippingScreenHandler.setScreenTitle("Shipping Screen");
		PlaceOrderController placeOrderController = new PlaceOrderController();
		ShippingScreenHandler.setBController(placeOrderController);
		ShippingScreenHandler.show();
	}
	/** 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void confirmInvoice(MouseEvent event) throws IOException {
		BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
		paymentScreen.setBController(new PaymentController());
		paymentScreen.setPreviousScreen(this);
		paymentScreen.setHomeScreenHandler(homeScreenHandler);
		paymentScreen.setScreenTitle("Payment Screen");
		paymentScreen.show();
		LOGGER.info("Confirmed invoice");
	}

}
