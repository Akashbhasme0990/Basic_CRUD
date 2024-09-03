package com.EmployeeData.Employee_data.Controller;

import com.EmployeeData.Employee_data.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // In-memory storage for employees
    private Map<String, Employee> employeeMap = new HashMap<>();

    // GET all employees or GET by ID
    @GetMapping
    public ResponseEntity<Collection<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeMap.values(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") String id) {
        Employee employee = employeeMap.get(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // POST - Add a new employee
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        if (employeeMap.containsKey(employee.getEmployeeId())) {
            return new ResponseEntity<>("Employee with ID " + employee.getEmployeeId() + " already exists.", HttpStatus.CONFLICT);
        }
        employeeMap.put(employee.getEmployeeId(), employee);
        return new ResponseEntity<>("Employee added successfully.", HttpStatus.CREATED);
    }

    // PUT - Update an existing employee (except ID)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") String id, @RequestBody Employee updatedEmployee) {
        Employee existingEmployee = employeeMap.get(id);
        if (existingEmployee != null) {
            // Update fields except employeeId
            existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
            existingEmployee.setEmployeeEmail(updatedEmployee.getEmployeeEmail());
            existingEmployee.setLocation(updatedEmployee.getLocation());
            employeeMap.put(id, existingEmployee);
            return new ResponseEntity<>("Employee updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Remove an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") String id) {
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            return new ResponseEntity<>("Employee deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
