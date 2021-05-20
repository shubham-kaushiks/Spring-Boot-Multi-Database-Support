package com.example.multidb.slave.repo;

import com.example.multidb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveEmployeeRepository extends JpaRepository<Employee, Long> {
}
