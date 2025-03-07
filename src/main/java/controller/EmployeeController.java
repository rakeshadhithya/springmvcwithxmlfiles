package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import entity.Employee;
import service.EmployeeService;


/*
Multi action controller is deprecated in spring 4.* and completely removed from spring 5.*.
It is replaced by @Controller and @RequestMapping
 */

public class EmployeeController extends MultiActionController {
	EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response) {
		List<Employee> employeeList = employeeService.findAll();
		if(employeeList==null) {
			System.out.println("Finding employees failed, try again!");
			return new ModelAndView("find-all-failed");
		}
        return new ModelAndView("all-employees", "employeeList", employeeList);
    }	
	

    public ModelAndView findById(HttpServletRequest request, HttpServletResponse response) {
    	
    	Employee employeeFound = employeeService.findById(Integer.valueOf(request.getParameter("id")));
		if(employeeFound==null) {
			System.out.println("Employee with given id not found, try again!");
			return new ModelAndView("find-by-id-failed");
		}
        return new ModelAndView("employee-found", "employeeFound", employeeFound);
    }
       
    
    
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String department = request.getParameter("department");
		String salary = request.getParameter("salary");
		if(name==null || department==null || salary==null){
			return new ModelAndView("employee-details-missing");
		}
    	Integer employeeId = employeeService.save(name, department, salary);
		if(employeeId==null) {
			System.out.println("Employee with given credentials cannot be saved, try again!");
			return new ModelAndView("employee-save-failed");
		}
        return new ModelAndView("employee-saved", "employeeId", employeeId);
    }
    
    
    public ModelAndView update(HttpServletRequest request, HttpServletResponse respone) {
    	Integer id = Integer.valueOf(request.getParameter("id"));
		if(employeeService.findById(id)==null){
			return new ModelAndView("find-by-id-failed");
		}
    	String name = request.getParameter("name");
    	String department = request.getParameter("department");
    	String salary = request.getParameter("salary");
		if(name==null || department ==null || salary==null){
			return new ModelAndView("employee-details-missing");
		}
    	
    	Employee employeeUpdated = employeeService.update(id, name, department, salary);
		if(employeeUpdated==null) {
			System.out.println("Employee with given id not found to update, try again!");
			return new ModelAndView("update-failed");
		}
    	return new ModelAndView("update-successfull", "employeeUpdated", employeeUpdated);
    }
    
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.valueOf(request.getParameter("id"));
		if(employeeService.findById(id)==null){
			return new ModelAndView("find-by-id-failed");
		}
    	employeeService.delete(Integer.valueOf(request.getParameter("id")));
		System.out.println("employee with id deleted successfully");
    	return new ModelAndView("delete-successfull");
    }
}