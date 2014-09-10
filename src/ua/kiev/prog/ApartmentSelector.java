package ua.kiev.prog;

/**
 * Selects the info from DB
 * Created by User on 09.07.2014.
 */
public class ApartmentSelector {

    public ApartmentSelector(String district, byte roomCount,
                             float minArea, float maxArea,
                             double minPrice, double maxPrice)
    {
        this.district = district;
        this.roomCount = roomCount;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public ApartmentSelector() {
        this.maxArea = Float.MAX_VALUE;
        this.maxPrice = Double.MAX_VALUE;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public byte getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(byte roomCount) {
        this.roomCount = roomCount;
    }

    public float getMinArea() {
        return minArea;
    }

    public void setMinArea(float minArea) {
        this.minArea = minArea;
    }

    public float getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(float maxArea) {
        this.maxArea = maxArea;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "ApartmentSelector{" +
                "district='" + district + '\'' +
                ", roomCount=" + roomCount +
                ", minArea=" + minArea +
                ", maxArea=" + maxArea +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }

    private String district;
    private byte roomCount;
    private float minArea;
    private float maxArea;
    private double minPrice;
    private double maxPrice;
}
