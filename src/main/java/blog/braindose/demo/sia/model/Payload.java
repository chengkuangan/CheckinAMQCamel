package blog.braindose.demo.sia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("flightNo")
    private String flightNo;
    @JsonProperty("hotelNight")
    private Integer hotelNight;
    @JsonProperty("departureAirport")
    private String departureAirport;
    @JsonProperty("trackingNo")
    private String trackingNo;
    @JsonProperty("fee")
    private Double fee;
    @JsonProperty("scheduledDepartureTime")
    private String scheduledDepartureTime;
    @JsonProperty("totalCharges")
    private Double totalCharges;
    @JsonProperty("hotel")
    private String hotel;
    @JsonProperty("scheduledArrivalTime")
    private String scheduledArrivalTime;
    @JsonProperty("hotelCharges")
    private Double hotelCharges;
    @JsonProperty("arrivalAirport")
    private String arrivalAirport;

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("flightNo")
    public String getFlightNo() {
        return flightNo;
    }

    @JsonProperty("flightNo")
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @JsonProperty("hotelNight")
    public Integer getHotelNight() {
        return hotelNight;
    }

    @JsonProperty("hotelNight")
    public void setHotelNight(Integer hotelNight) {
        this.hotelNight = hotelNight;
    }

    @JsonProperty("departureAirport")
    public String getDepartureAirport() {
        return departureAirport;
    }

    @JsonProperty("departureAirport")
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    @JsonProperty("trackingNo")
    public String getTrackingNo() {
        return trackingNo;
    }

    @JsonProperty("trackingNo")
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @JsonProperty("fee")
    public Double getFee() {
        return fee;
    }

    @JsonProperty("fee")
    public void setFee(Double fee) {
        this.fee = fee;
    }

    @JsonProperty("scheduledDepartureTime")
    public String getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    @JsonProperty("scheduledDepartureTime")
    public void setScheduledDepartureTime(String scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    @JsonProperty("totalCharges")
    public Double getTotalCharges() {
        return totalCharges;
    }

    @JsonProperty("totalCharges")
    public void setTotalCharges(Double totalCharges) {
        this.totalCharges = totalCharges;
    }

    @JsonProperty("hotel")
    public String getHotel() {
        return hotel;
    }

    @JsonProperty("hotel")
    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    @JsonProperty("scheduledArrivalTime")
    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    @JsonProperty("scheduledArrivalTime")
    public void setScheduledArrivalTime(String scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    @JsonProperty("hotelCharges")
    public Double getHotelCharges() {
        return hotelCharges;
    }

    @JsonProperty("hotelCharges")
    public void setHotelCharges(Double hotelCharges) {
        this.hotelCharges = hotelCharges;
    }

    @JsonProperty("arrivalAirport")
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    @JsonProperty("arrivalAirport")
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String toDelimited(String delimiter){
        return id + delimiter + flightNo + delimiter + departureAirport + delimiter + trackingNo + delimiter + scheduledDepartureTime + delimiter + scheduledArrivalTime + delimiter + arrivalAirport;
    }

}