package com.codingshuttle.youtube.hospitalManagement;

import com.codingshuttle.youtube.hospitalManagement.Repository.PatientRepository;
import com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PatientTests {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;
    @Test
    public void testPatientRepo(){
//     List<Patient> patientList=patientRepository.findAll();
        List<Patient> patientList=patientRepository.findAllPatientWithAppointment();
        System.out.println(patientList);

//        Patient p1=new Patient();
//        patientRepository.save(p1);
    }
    @Test
    public void testTransactionMethods(){
//    Patient patient=patientService.getPatientById(1L);
//        Patient patient =patientRepository.findById(1L).orElseThrow(()-> new EntityNotFoundException("patient with id"+id+" not found")
//        Patient patient =patientRepository.findByName("Diya Patel");
//        List<Patient> patientList=patientRepository.findByBirthDateOrEmail(LocalDate.of(1990,5,10),"kabir.singh@example.com");
//        List<Patient> patientList=patientRepository.findByBirthDateBetween(LocalDate.of(2026,8,10),LocalDate.of(1900,7,9));
//                List<Patient> patientList=patientRepository.findByBloodGroup(BloodGroupType.A_POSITIVE);
//
//       for(Patient patient:patientList) System.out.println(patient);
//       List<Object[]>bloodGroupList=patientRepository.countEachBloodGroupType();
//       for(Object[] objects:bloodGroupList){
//           System.out.println(objects[0]+" "+objects[1]);
//       }
//List<Patient> patientList=patientRepository.findAllPatients();
//       for(Patient patient:patientList) System.out.println(patient);
//
//        int rowsUpdated=patientRepository.updateNameWithId("Arav Sharma",1L);
//        System.out.println(rowsUpdated);


//
//        List<BloodGroupCountResponseEntity>bloodGroupList=patientRepository.countEachBloodGroupType();
//       for(BloodGroupCountResponseEntity bloodGroupCountResponse:bloodGroupList){
//           System.out.println(bloodGroupCountResponse);

//       }



//Page<Patient> patientList=patientRepository.findAllPatients(PageRequest.of(0,2, Sort.by("name")));
//
//       for(Patient patient:patientList)
//       {System.out.println(patient);}
//
    }


}
