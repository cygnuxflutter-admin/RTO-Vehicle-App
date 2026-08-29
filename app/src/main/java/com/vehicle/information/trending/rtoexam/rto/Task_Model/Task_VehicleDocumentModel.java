package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_VehicleDocumentModel {
    private String id;
    private String vehicleNumber;
    private String vehicleName;
    private String insuranceExpiry;
    private String pucExpiry;
    private String serviceDueDate;

    public Task_VehicleDocumentModel(String id, String vehicleNumber, String vehicleName, String insuranceExpiry, String pucExpiry, String serviceDueDate) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.insuranceExpiry = insuranceExpiry;
        this.pucExpiry = pucExpiry;
        this.serviceDueDate = serviceDueDate;
    }

    public String getId() {
        return id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getInsuranceExpiry() {
        return insuranceExpiry;
    }

    public String getPucExpiry() {
        return pucExpiry;
    }

    public String getServiceDueDate() {
        return serviceDueDate;
    }
}