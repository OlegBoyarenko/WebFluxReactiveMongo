package com.reactive.webFluxNonBlock.service;

import com.reactive.webFluxNonBlock.model.Employee;
import com.reactive.webFluxNonBlock.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepo;

    @Override
    public void create(Employee e) {
        employeeRepo.save(e).subscribe();
    }

    @Override
    public Mono<Employee> findById(String id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Flux<Employee> findByName(String name) {
        return employeeRepo.findByName(name);
    }

    @Override
    public Flux<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Mono<Employee> update(Employee e) {
        return employeeRepo.save(e);
    }

    @Override
    public Mono<Void> delete(String  id) {
        return employeeRepo.deleteById(id);
    }
}
