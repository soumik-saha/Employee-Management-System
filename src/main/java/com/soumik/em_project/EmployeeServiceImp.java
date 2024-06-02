package com.soumik.em_project;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    List<Employee> employees = new ArrayList<>();

    @Override
    public String createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        return employee.getName() + " added successfully";
    }

    @Override
    public List<EmployeeEntity> readEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();

        for (EmployeeEntity employeeEntity : employees) {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeEntity, employee);
            this.employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            EmployeeEntity employee = employeeOptional.get();
            employeeRepository.delete(employee);
            return true;
        }
        return false;
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        employeeEntity.setName(employee.getName());
        employeeEntity.setPhone(employee.getPhone());
        employeeEntity.setEmail(employee.getEmail());
        employeeRepository.save(employeeEntity);

        return employee.getName() + " updated successfully";
    }

    @Override
    public Employee readEmployee(Long id) {
        EmployeeEntity emp = employeeRepository.findById(id).get();
        Employee employee = new Employee();
        BeanUtils.copyProperties(emp, employee);

        return employee;
    }

}
