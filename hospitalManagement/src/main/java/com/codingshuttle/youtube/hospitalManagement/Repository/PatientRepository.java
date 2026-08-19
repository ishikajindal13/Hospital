//package com.codingshuttle.youtube.hospitalManagement.Repository;
//
//import com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity;
//import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
//import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
//import jakarta.transaction.Transactional;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface PatientRepository extends JpaRepository<Patient,Long> {
//
//
//  // GET QUIREIS
//
//
//    Patient findByName(String name);
//    List<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);
//List<Patient>findByBirthDateBetween(LocalDate startDate,LocalDate endDate);
//    Optional<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);
//
//    @Query("SELECT p FROM Patient p where p.bloodGroup=?1")
//    List<Patient>findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);
//
//    @Query("select p.bloodGroup,Count(p) from Patient p group by p.bloodGroup")
//    List<Object[]> countEachBloodGroupType();
//
//    @Query(value = "select * from patient",nativeQuery = true)
//    List<Patient> findAllPatients();
//
//
//
//
//
//    /*// CREATE / UPDATE
//    @Transactional
//    @Modifying
//    @Query("UPDATE Patient p SEt p.name=:name where p.id=:id")
//    int updateNameWithId(@Param("name") String name,@Param("id")Long id);
//*/
//    //PROJECTION
//    // IF I WANT ONLY SOME FIELDS
//    // i need not to make all fiels in db , i just want soem fields , and then other fields will be null
//    // poora patient na lakar v, uska kuch kuch data lekar aa jao
//    // for that i have to make a new patient obj , db se toh rows hi aayengi
//    // then i have to make a new entity -> where rows will be conveted to a aprticular obj , that will be done by hibernate
//    // craeted dto->BloodGroupCountResponseEntitty
// // not applicablr in native query
//    @Query("select new com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity( p.bloodGroup,Count(p)) from Patient p group by p.bloodGroup")
//    List<BloodGroupCountResponseEntity> countEachBloodGroupType();
//
//
//    //PAGINATION
//    // ager mereko sarew ke saare patients h but may ber 1 million patuients soi that on one page we get 20 only
//    // springbbot jpa has handled it
//
//
//    @Query(value = "select * from patient",nativeQuery = true)
//    Page<Patient> findAllPatients(Pageable pageable);
//
//
//
//}






package com.codingshuttle.youtube.hospitalManagement.Repository;

import com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

@Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor")
List<Patient> findAllPatientWithAppointment();

}
