package com.reactive.webFluxNonBlock.controller;

import com.reactive.webFluxNonBlock.model.Employee;
import com.reactive.webFluxNonBlock.model.EmployeeEvent;
import com.reactive.webFluxNonBlock.repository.EmployeeRepository;
import com.reactive.webFluxNonBlock.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;


import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
//@RequestMapping("/rest/employee")
public class EmployeeController {


    private EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    @Operation(summary = "list all employee")
    public Flux<Employee> getAll() {
        return employeeService
                .findAll();
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Employee e) {
        employeeService.create(e);
    }

    @GetMapping("/{id}")
    public Mono<Employee> getId(@PathVariable("id") final String empId) {
        return employeeService.findById(empId);
    }

    @GetMapping(value = "/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Employee> findByName(@PathVariable("name") String name) {
        return employeeService.findByName(name);
    }


    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEvents(@PathVariable("id")
                                    final String empId) {
        return employeeService.findById(empId)
                .flatMapMany(employee -> {

                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

                    Flux<EmployeeEvent> employeeEventFlux =
                            Flux.fromStream(
                                    Stream.generate(() -> new EmployeeEvent(employee,
                                            new Date()))
                            );


                    return Flux.zip(interval, employeeEventFlux)
                            .map(Tuple2::getT2);

                });

    }



}
