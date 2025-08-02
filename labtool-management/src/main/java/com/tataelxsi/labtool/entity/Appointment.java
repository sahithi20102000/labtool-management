package com.tataelxsi.labtool.entity;
import java.util.*;
import jakarta.persistence.*;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;
    private String service;
    private List<String> parts;


    private String customerName;
    private String serviceType;
    private String appointmentDate;

    public Appointment() {}

    public Appointment(String customerName, String serviceType, String appointmentDate) {
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.appointmentDate = appointmentDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getClient() {
    return client;
}

public void setClient(String client) {
    this.client = client;
}

public String getService() {
    return service;
}

public void setService(String service) {
    this.service = service;
}

public List<String> getParts() {
    return parts;
}

public void setParts(List<String> parts) {
    this.parts = parts;
}

}