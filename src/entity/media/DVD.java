// Source code is decompiled from a .class file using FernFlower decompiler.
package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DVD extends Media {
    String discType;
    String director;
    int runtime;
    String studio;
    String subtitles;
    Date releasedDate;
    String language;

    public DVD() throws SQLException {
    }

    public DVD(int id, String title, String category, int price, int quantity, int value, boolean isSupportRushDelivery, String discType, String director, int runtime, String studio, String subtitles, Date releasedDate, String language) throws SQLException {
        super(id, title, category, price, quantity, value, isSupportRushDelivery);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
        this.language = language;
    }

    public String getDiscType() {
        return this.discType;
    }

    public DVD setDiscType(String discType) {
        this.discType = discType;
        return this;
    }

    public String getDirector() {
        return this.director;
    }

    public DVD setDirector(String director) {
        this.director = director;
        return this;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public DVD setRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getStudio() {
        return this.studio;
    }

    public DVD setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    public String getSubtitles() {
        return this.subtitles;
    }

    public DVD setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public DVD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public DVD setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String toString() {
        return "{" + super.toString() + " discType='" + this.discType + "', director='" + this.director + "', runtime='" + this.runtime + "', studio='" + this.studio + "', subtitles='" + this.subtitles + "', releasedDate='" + this.releasedDate + "', language='" + this.language + "'}";
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM aims.DVD INNER JOIN aims.Product ON Product.id = DVD.id where Product.id = " + id + ";";
        ResultSet res = this.stm.executeQuery(sql);
        if (res.next()) {
            String title = "";
            int value = res.getInt("value");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            String discType = res.getString("discType");
            String director = res.getString("director");
            int runtime = res.getInt("runtime");
            String studio = res.getString("studio");
            String subtitles = res.getString("subtitle");
            Date releasedDate = res.getDate("releasedDate");
            String language = res.getString("language");
            Boolean isSupportRushDelivery = false;
            if (res.getBoolean("isSupportRushDelivery")) {
                isSupportRushDelivery = true;
            }

            return new DVD(id, title, category, price, quantity, value, isSupportRushDelivery, discType, director, runtime, studio, subtitles, releasedDate, language);
        } else {
            throw new SQLException();
        }
    }

    public List getAllMedia() {
        return null;
    }
}
