package com.codingshuttle.youtube.hospitalManagement.Repository;

import com.codingshuttle.youtube.hospitalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}