package com.example.multidb.master.repo;

import com.example.multidb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterEmployeeRepository extends JpaRepository<Employee, Long> {
}
