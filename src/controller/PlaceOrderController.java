package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * 
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    /**
     * This method checks the avalibility of product when user click PlaceOrder
     * button
     * 
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * 
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * 
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * 
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        return this.validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     * 
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        return this.validateName((String)info.get("name")) && this.validatePhoneNumber((String)info.get("phone")) && this.validateAddress((String)info.get("address")) && validateEmail((String)info.get("email")) && info.get("shippingMethod") != null;
    }

    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            return false;
        } else if (Character.compare(phoneNumber.charAt(0), '0') != 0) {
            return false;
        } else {
            try {
                Integer.parseInt(phoneNumber);
                return true;
            } catch (NumberFormatException var3) {
                return false;
            }
        }
    }

    public boolean validateName(String name) {
        if (name == null) {
            return false;
        } else if (name.trim().length() == 0) {
            return false;
        } else {
            return name.matches("^[a-zA-Z ]*$");
        }
    }

    public boolean validateAddress(String address) {
        if (address == null) {
            return false;
        } else if (address.trim().length() == 0) {
            return false;
        } else {
            return address.matches("^[a-zA-Z ]*$");
        }
    }

    /**
     * This method calculates the shipping fees of order
     * 
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order) {
        Random rand = new Random();
        int fees = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }

    /**
     * This method get product available place rush order media
     * 
     * @param order
     * @return media
     * @throws SQLException
     */
//    public Media getProductAvailablePlaceRush(Order order) throws SQLException {
//        Media media = new Media();
//        HashMap<String, String> deliveryInfo = order.getDeliveryInfo();
//        validateAddressPlaceRushOrder(deliveryInfo.get("province"), deliveryInfo.get("address"));
//        for (Object object : order.getlstOrderMedia()) {
//            // CartMedia cartMedia = (CartMedia) object;
//            validateMediaPlaceRushorder();
//        }
//        return media;
//    }

    
    /** 
     * @param province
     * @param address
     * @return boolean
     */
    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateAddress(address))
            return false;
        if(!province.equals("Hà Nội"))
            return false;
        return true;
    }

    public boolean checkRushDeliveryProduct(List listMedia) {
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
    /** 
     * @return boolean
     */
    public boolean validateMediaPlaceRushorder() {
        if (Media.isSupportRushDelivery()) {
            return true;
        }
        return false;
    }
}
