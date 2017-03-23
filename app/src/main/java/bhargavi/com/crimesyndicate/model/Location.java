package bhargavi.com.crimesyndicate.model;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private String latitude;
    private String humanAddress;
    private Boolean needsRecoding;
    private String longitude;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The humanAddress
     */
    public String getHumanAddress() {
        return humanAddress;
    }

    /**
     * @param humanAddress The human_address
     */
    public void setHumanAddress(String humanAddress) {
        this.humanAddress = humanAddress;
    }

    /**
     * @return The needsRecoding
     */
    public Boolean getNeedsRecoding() {
        return needsRecoding;
    }

    /**
     * @param needsRecoding The needs_recoding
     */
    public void setNeedsRecoding(Boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
    }

    /**
     * @return The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}