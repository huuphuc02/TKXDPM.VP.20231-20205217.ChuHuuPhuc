package controller;

import java.sql.SQLException;
import java.util.List;

import entity.cart.Cart;
import entity.media.Media;

public class HomeController extends BaseController {

	/**
	 * this method gets all Media in DB and return back to home to display
	 * 
	 * @return List[Media]
	 * @throws SQLException
	 */
	public List getAllMedia() throws SQLException {
		return new Media().getAllMedia();
	}

}
