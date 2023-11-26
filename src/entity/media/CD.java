// Source code is decompiled from a .class file using FernFlower decompiler.
package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CD extends Media {
    String artist;
    String recordLabel;
    String genre;
    Date releasedDate;
    String trackList;

    public CD() throws SQLException {
    }

    public CD(int id, String title, String category, int price, int quantity, int value, boolean isSupportRushDelivery, String artist, String recordLabel, String genre, Date releasedDate, String trackList) throws SQLException {
        super(id, title, category, price, quantity, value, isSupportRushDelivery);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.genre = genre;
        this.releasedDate = releasedDate;
        this.trackList = trackList;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.genre;
    }

    public CD setMusicType(String genre) {
        this.genre = genre;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public String toString() {
        return "{" + super.toString() + " artist='" + this.artist + "', recordLabel='" + this.recordLabel + "'', genre='" + this.genre + "', releasedDate='" + this.releasedDate + "'}";
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrackList() {
        return this.trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM aims.CD INNER JOIN aims.Product ON Product.id = CD.id where Product.id = " + id + ";";
        ResultSet res = this.stm.executeQuery(sql);
        if (res.next()) {
            String title = "";
            int value = res.getInt("value");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String genre = res.getString("genre");
            String trackList = res.getString("tracklist");
            Date releasedDate = res.getDate("releasedDate");
            Boolean isSupportRushDelivery = false;
            if (res.getBoolean("isSupportRushDelivery")) {
                isSupportRushDelivery = true;
            }

            return new CD(id, title, category, price, quantity, value, isSupportRushDelivery, artist, recordLabel, genre, releasedDate, trackList);
        } else {
            throw new SQLException();
        }
    }

    public List getAllMedia() {
        return null;
    }
}
