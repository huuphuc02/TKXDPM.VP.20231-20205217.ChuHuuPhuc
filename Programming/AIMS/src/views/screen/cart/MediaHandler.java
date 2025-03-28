package views.screen.cart;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

import common.exception.MediaUpdateException;
import common.exception.ViewCartException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Configs;
import utils.Utils;
import views.screen.FXMLScreenHandler;

public class MediaHandler extends FXMLScreenHandler {

	private static Logger LOGGER = Utils.getLogger(MediaHandler.class.getName());

	@FXML
	protected HBox hboxMedia;

	@FXML
	protected ImageView image;

//	@FXML
//	protected VBox description;

	@FXML
	protected Label labelOutOfStock;

	@FXML
	protected VBox spinnerFX;

	@FXML
	protected Label title;

	@FXML
	protected Label price;

//	@FXML
//	protected Label currency;

	@FXML
	protected Button btnDelete;

	private CartMedia cartMedia;
	private Spinner<Integer> spinner;
	private CartScreenHandler cartScreen;

	public MediaHandler(String screenPath, CartScreenHandler cartScreen) throws IOException {
		super(screenPath);
		this.cartScreen = cartScreen;
		hboxMedia.setAlignment(Pos.CENTER);
	}


	/**
	 * @param cartMedia
	 */
	public void setCartMedia(CartMedia cartMedia) {
		this.cartMedia = cartMedia;
		setMediaInfo();
	}

	private void setMediaInfo() {
		title.setText(cartMedia.getMedia().getTitle());
		price.setText(Utils.getCurrencyFormat(cartMedia.getPrice()));
		File file = new File(cartMedia.getMedia().getImageURL());
		Image im = new Image(file.toURI().toString());
		image.setImage(im);
		image.setPreserveRatio(false);
		image.setFitHeight(110);
		image.setFitWidth(92);

		// add delete button
		btnDelete.setFont(Configs.REGULAR_FONT);
		btnDelete.setOnMouseClicked(e -> {
			try {
				Cart.getCart().removeCartMedia(cartMedia); // update user cart
				cartMedia.getMedia().setQuantity(cartMedia.getMedia().getOriginalQuantity()); // restore quantity when delete
				cartScreen.updateCart(); // re-display user cart
				LOGGER.info("Deleted " + cartMedia.getMedia().getTitle() + " from the cart");
			} catch (SQLException exp) {
				exp.printStackTrace();
				throw new ViewCartException();
			}
		});

		initializeSpinner();
	}

	private void initializeSpinner(){
		SpinnerValueFactory<Integer> valueFactory = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, cartMedia.getQuantity());
		spinner = new Spinner<Integer>(valueFactory);
		spinner.setOnMouseClicked( e -> {
			try {
				int numOfProd = this.spinner.getValue();
				int originalQuantity = cartMedia.getMedia().getOriginalQuantity();
				LOGGER.info("NumOfProd: " + numOfProd + " -- remainOfProd: " + originalQuantity);
				if (numOfProd > originalQuantity){
					numOfProd -= 1;
					LOGGER.info("product " + cartMedia.getMedia().getTitle() + " only remains " + originalQuantity + " (required " + numOfProd + ")");
					labelOutOfStock.setText("Sorry, Only " + 0 + " remain in stock");
					spinner.getValueFactory().setValue(numOfProd);
//					numOfProd = remainQuantity;
				}

				// update quantity of media
				cartMedia.getMedia().setQuantity(originalQuantity - numOfProd);

				// update quantity of mediaCart in useCart
				cartMedia.setQuantity(numOfProd);

				// update the total of mediaCart
				price.setText(Utils.getCurrencyFormat(numOfProd*cartMedia.getPrice()));

				// update subtotal and amount of Cart
				cartScreen.updateCartAmount();

			} catch (SQLException e1) {
				throw new MediaUpdateException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
			}

		});
		spinnerFX.setAlignment(Pos.CENTER);
		spinnerFX.getChildren().add(this.spinner);
	}
}