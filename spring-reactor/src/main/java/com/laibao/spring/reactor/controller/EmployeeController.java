package com.laibao.spring.reactor.controller;

import com.laibao.spring.reactor.domain.Department;
import com.laibao.spring.reactor.domain.Employee;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class EmployeeController {


    @GetMapping("/contact/{deptId}/employee/{empId}")
    public Employee findEmployee(@PathVariable long deptId, @PathVariable long empId) {
        //TODO
        // Find the employee.
        return null;
    }

    @GetMapping("/fibonacci")
    public List<Long> fibonacci(@RequestHeader("Accept-Encoding") String encoding) {
        //TODO
        // Determine Series
        return null;
    }

    @PostMapping("/department")
    public void createDept(@RequestBody Mono<Department> dept) {
        //TODO
        // Add new department
    }

    @GetMapping("/contact/employee")
    public Employee findEmployeeByParam(@RequestParam("deptId")long deptId, @RequestParam("empId") long empId) {
        //TODO
        // Find the employee.
        return null;
    }

    @PostMapping("/department")
    public void createDept(@ModelAttribute Department dept) {
        //TODO
        // Add new department
    }
}
