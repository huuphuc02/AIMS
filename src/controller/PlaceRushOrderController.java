package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import entity.media.Media;
import entity.order.OrderMedia;
import entity.shipping.RushDeliveryInfo;

/**
 * This class controls the flow of place rush order usecase in our AIMS project
 * 
 * @author giangleee
 */
public class PlaceRushOrderController extends BaseController {
	/**
	 * Just for logging purpose
	 */
	private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

	
	/** 
	 * @param deliveryData
	 */
	public static void validatePlaceRushOrderData(HashMap<String, String> deliveryData) {
		RushDeliveryInfo rushDeliveryInfo = new RushDeliveryInfo(deliveryData.get("deliveryInstruction"), deliveryData.get("shipmentDetail"), deliveryData.get("deliveryTime"));
	}

	public static int checkRushDelivery(String province, List listMedia){
		int result = 0;
		if(!province.equals("Hà Nội")){
			result = 1;
		}
		else if(!checkRushDeliveryProduct(listMedia)){
			result = 2;
		}
		return result;
	}

	public static boolean checkRushDeliveryProduct(List listMedia) {
		Iterator var3 = listMedia.iterator();

		while(((Iterator<?>) var3).hasNext()) {
			Object object = var3.next();
			OrderMedia media = (OrderMedia)object;
			Media med = media.getMedia();
			System.out.println(med);
			if (med.isSupportRushDelivery()) {
				return true;
			}
		}

		return false;
	}
}
