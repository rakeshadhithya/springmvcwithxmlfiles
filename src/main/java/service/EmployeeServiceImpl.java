package service;

import java.util.List;

import entity.Employee;
import repository.EmployeeRepository;

public class EmployeeServiceImpl implements EmployeeService {
	
	EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	

	@Override
	public List<Employee> findAll() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	@Override
	public Employee findById(Integer id) {
		Employee employeeFound = employeeRepository.findById(id);
		return employeeFound;
	}

	@Override
	public Integer save(String name, String department, String salary) {
		
		int id = employeeRepository.findAll().size() + 1;
		Employee employee = new Employee(id, name, department, salary);
		Integer employeeId = employeeRepository.save(employee);
		return employeeId;
	}

	@Override
	public Employee update(Integer id, String name, String department, String salary) {
		Employee employee = new Employee(id, name, department, salary);
		Employee employeeUpdated = employeeRepository.update(employee);
		return employeeUpdated;
	}

	@Override
	public void delete(Integer id) {
		employeeRepository.delete(id);

	}
	
}
