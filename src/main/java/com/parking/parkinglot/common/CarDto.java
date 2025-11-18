package com.parking.parkinglot.common;

public class CarDto {
    Long id;
    String licensePlate;
    String parkingSpot;
    String ownerName;

    public CarDto(Long id, String licensePlate, String parkingSpot, String ownerName) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.parkingSpot = parkingSpot;
        this.ownerName = ownerName;
    }

    public CarDto(com.parking.parkinglot.entities.Car car) {
        this.id = car.getId();
        this.licensePlate = car.getLicensePlate();
        this.parkingSpot = car.getParkingSpot();
        this.ownerName = car.getOwner().getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getOwnerName() {
        return ownerName;
    }


}
