package com.tataelxsi.labtool.service;

import com.tataelxsi.labtool.entity.Appointment;
import com.tataelxsi.labtool.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StorageOptimizationService storageOptimizationService;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.save(appointment);
        storageOptimizationService.updatePartUsage(savedAppointment.getParts());
        return savedAppointment;
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        Appointment existing = appointmentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setClient(appointment.getClient());
            existing.setService(appointment.getService());
            existing.setParts(appointment.getParts());
            Appointment updated = appointmentRepository.save(existing);
            storageOptimizationService.updatePartUsage(updated.getParts());
            return updated;
        }
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
