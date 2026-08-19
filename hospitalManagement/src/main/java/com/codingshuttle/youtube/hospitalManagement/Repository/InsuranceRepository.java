package com.codingshuttle.youtube.hospitalManagement.Repository;

import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}