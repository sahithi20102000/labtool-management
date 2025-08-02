package com.tataelxsi.labtool.service;

import com.tataelxsi.labtool.entity.Appointment;
import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Appointment createAppointment(Appointment appointment);

    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Long id, Appointment appointment);

    void deleteAppointment(Long id);
}
