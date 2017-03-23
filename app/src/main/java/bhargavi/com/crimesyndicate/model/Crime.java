package bhargavi.com.crimesyndicate.model;

import java.util.HashMap;
import java.util.Map;

public class Crime {

    private String date;
    private String address;
    private String resolution;
    private String pddistrict;
    private String incidntnum;
    private String x;
    private String dayofweek;
    private String y;
    private Location location;
    private String time;
    private String pdid;
    private String category;
    private String descript;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The resolution
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * @param resolution The resolution
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     * @return The pddistrict
     */
    public String getPddistrict() {
        return pddistrict;
    }

    /**
     * @param pddistrict The pddistrict
     */
    public void setPddistrict(String pddistrict) {
        this.pddistrict = pddistrict;
    }

    /**
     * @return The incidntnum
     */
    public String getIncidntnum() {
        return incidntnum;
    }

    /**
     * @param incidntnum The incidntnum
     */
    public void setIncidntnum(String incidntnum) {
        this.incidntnum = incidntnum;
    }

    /**
     * @return The x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x The x
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return The dayofweek
     */
    public String getDayofweek() {
        return dayofweek;
    }

    /**
     * @param dayofweek The dayofweek
     */
    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    /**
     * @return The y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y The y
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return The pdid
     */
    public String getPdid() {
        return pdid;
    }

    /**
     * @param pdid The pdid
     */
    public void setPdid(String pdid) {
        this.pdid = pdid;
    }

    /**
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The descript
     */
    public String getDescript() {
        return descript;
    }

    /**
     * @param descript The descript
     */
    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}