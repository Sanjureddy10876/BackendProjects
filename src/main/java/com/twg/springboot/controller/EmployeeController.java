package com.twg.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg.springboot.exception.ResourceNotFoundException;
import com.twg.springboot.model.Employee;
import com.twg.springboot.repository.EmployeeRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all the employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){	
		return employeeRepository.findAll();
	}
	
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
//	get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));
	return ResponseEntity.ok(employee);
	}
	
//	@GetMapping("/employees/{id}")
//	public ResponseEntity <Employee> getemployeeByID(@PathVariable Long id){
//		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :"+id));
//		return ResponseEntity.ok(employee); 	
//	}
	
	// update employee rest api
//	@PutMapping("/employees/{id}")
//	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, Employee employeeDetails){
//		
//		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe not found with id:"+id));
//		
//		employee.setFirstname(employeeDetails.getFirstname());
//		employee.setLastname(employeeDetails.getLastname());
//		employee.setEmailId(employeeDetails.getEmailId());
//		
//		Employee updateEmployee = employeeRepository.save(employee);
//		return ResponseEntity.ok(updateEmployee);
//	}
	
	// update employee rest api
	 @PutMapping("/employees/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
	        Employee employee = employeeRepository.findById(id)
	                                              .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

	        // Update only non-null fields
	        if (employeeDetails.getFirstname() != null) {
	            employee.setFirstname(employeeDetails.getFirstname());
	        }
	        if (employeeDetails.getLastname() != null) {
	            employee.setLastname(employeeDetails.getLastname());
	        }
	        if (employeeDetails.getEmailId()!=null) {
	        	employee.setEmailId(employeeDetails.getEmailId());
	        }

	        Employee updatedEmployee = employeeRepository.save(employee);
	        return ResponseEntity.ok(updatedEmployee);
	    }
	 
	 // delete employee rest api
	 @DeleteMapping("/employees/{id}")
	 public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		 Employee employee = employeeRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		 employeeRepository.delete(employee);
		 Map<String , Boolean> response =new HashMap<>();
		 response.put("deleted", Boolean.TRUE);
		 
		 return ResponseEntity.ok(response);
		 
		 
	 }
} 
