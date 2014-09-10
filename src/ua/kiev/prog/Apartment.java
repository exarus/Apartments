package ua.kiev.prog;

import java.sql.Date;

/**
 * Apartment.
 * Created by User on 11.07.2014.
 */
public class Apartment {

    public Apartment(String district, String address,
                     float area, byte roomCount, double price)
    {
        this(district, address, area, roomCount, price, new Date(System.currentTimeMillis()));
    }

    public Apartment(String district, String address,
                     float area, byte roomCount, double price, Date createdDate)
    {

        this.district = district;
        this.address = address;
        this.area = area;
        this.roomCount = roomCount;
        this.price = price;
        this.createdDate = createdDate;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public float getArea() {
        return area;
    }

    public byte getRoomCount() {
        return roomCount;
    }

    public double getPrice() {
        return price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    private final String district;
    private final String address;
    private final float area;
    private final byte roomCount;
    private final double price;
    private final Date createdDate;
}
