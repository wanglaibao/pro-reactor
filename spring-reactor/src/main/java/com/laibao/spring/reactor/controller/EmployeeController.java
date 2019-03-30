package com.laibao.spring.reactor.controller;

import com.laibao.spring.reactor.domain.Department;
import com.laibao.spring.reactor.domain.Employee;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class EmployeeController {


    @GetMapping("/contact/{deptId}/employee/{empId}")
    public Mono<Employee> findEmployee(@PathVariable long deptId, @PathVariable long empId) {
        //TODO
        // Find the employee.
        return null;
    }


    @GetMapping("/contact/employee")
    public Mono<Employee> findEmployeeByParam(@RequestParam("deptId")long deptId, @RequestParam("empId") long empId) {
        //TODO
        // Find the employee.
        return null;
    }


    @GetMapping("/fibonacci")
    public Flux<Long> fibonacci(@RequestHeader("Accept-Encoding") String encoding) {
        // TODO
        return null;
    }


    @PostMapping("/department")
    public Mono<Void> createDept(@RequestBody Mono<Department> dept) {
        //TODO
        // Add new department
        return null;
    }

    @ExceptionHandler
    public String handleError(RuntimeException ex) {
        return null;
    }
}
