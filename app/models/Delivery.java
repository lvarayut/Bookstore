package models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Delivery {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("deliveryType")
    private String deliveryType;
    @JsonProperty("deliveryStatus")
    private String deliveryStatus;
    @JsonProperty("trackingNo")
    private String trackingNo;
    @JsonProperty("dateDelivered")
    private String dateDelivered;
    @JsonProperty("orders")
    private List<Order> orders;

    public Delivery() {
        orders = new ArrayList<Order>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(String dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id='" + id + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", trackingNo='" + trackingNo + '\'' +
                ", dateDelivered='" + dateDelivered + '\'' +
                '}';
    }
}
