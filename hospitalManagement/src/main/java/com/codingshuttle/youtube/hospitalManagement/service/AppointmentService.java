package com.codingshuttle.youtube.hospitalManagement.service;


import com.codingshuttle.youtube.hospitalManagement.Repository.AppointmentRepository;
import com.codingshuttle.youtube.hospitalManagement.Repository.DoctorRepository;
import com.codingshuttle.youtube.hospitalManagement.Repository.PatientRepository;
import com.codingshuttle.youtube.hospitalManagement.entity.Appointment;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;

@Service
@RequiredArgsConstructor

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    @Transactional
    public Appointment createNewAppointment(Appointment appointment,Long doctorId,Long patientId){
    Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();
    Patient patient=patientRepository.findById(patientId).orElseThrow();

    if(appointment.getId()!=null) throw new IllegalArgumentException("Appointment should not have ");

    appointment.setPatient(patient);
    appointment.setDoctor(doctor);

    patient.getAppointments().add(appointment);
    // no bidirectional mapping b/w doctor and aapouintment b/e no record of appoinement in doctor

    return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId,Long doctorId){
      Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor); // this will automatically call the update , b/e it is dirty
        doctor.getAppointments().add(appointment);   // just for bidirectional consistency
        return appointment;
    }
}
