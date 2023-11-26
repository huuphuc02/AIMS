package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import utils.Utils;

/**
 * The general media class, for another media it can be done by inheriting this class
 * @author nguyenlm
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());
    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value;
    protected int price;
    protected int quantity;
    protected String imageURL;
    protected static boolean isSupportRushDelivery;

    public Media() throws SQLException {
        this.stm = AIMSDB.getConnection().createStatement();
    }

    public Media(int id, String title, String category, int price, int quantity, int value, boolean isSupportRushDelivery) throws SQLException {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.value = value;
        this.quantity = quantity;
        this.isSupportRushDelivery = isSupportRushDelivery;
    }

    public int getQuantity() throws SQLException {
        return this.quantity;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Product ;";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        return res.next() ? (new Media()).setId(res.getInt("id")).setTitle(res.getString("title")).setQuantity(res.getInt("quantity")).setCategory(res.getString("category")).setMediaURL(res.getString("imageUrl")).setPrice(res.getInt("price")).setPrice(res.getInt("price")).setSupportRushDelivery(res.getBoolean("isSupportRushDelivery")) : null;
    }

    public List getAllMedia() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Product");
        System.out.println(res);
        ArrayList medium = new ArrayList();

        while(res.next()) {
            Media media = (new Media()).setId(res.getInt("id")).setTitle(res.getString("title")).setQuantity(res.getInt("quantity")).setCategory(res.getString("category")).setMediaURL(res.getString("imageUrl")).setPrice(res.getInt("price")).setValue(res.getInt("value")).setSupportRushDelivery(res.getBoolean("isSupportRushDelivery"));
            medium.add(media);
        }

        return medium;
    }

    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        if (value instanceof String) {
            value = "\"" + value + "\"";
        }

        stm.executeUpdate(" update " + tbname + " set " + field + "=" + value + " where id=" + id + ";");
    }

    public int getId() {
        return this.id;
    }

    private Media setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Media setMediaURL(String url) {
        this.imageURL = url;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public Media setValue(int value) {
        this.value = value;
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public static boolean isSupportRushDelivery() {
        return isSupportRushDelivery;
    }

    public Media setSupportRushDelivery(boolean supportRushDelivery) {
        isSupportRushDelivery = supportRushDelivery;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String toString() {
        return "{ id='" + this.id + "', title='" + this.title + "', category='" + this.category + "', price='" + this.price + "', quantity='" + this.quantity + "', value='" + this.value + "', imageURL='" + this.imageURL + "'}";
    }

}