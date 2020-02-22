package com.pa.example.controller;

import com.pa.example.data.Employee;
import com.pa.example.data.User;
import com.pa.example.event.EmployeeEvent;
import com.pa.example.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    //Async listener (no blockage)
    private final ApplicationEventMulticaster multiEventPublisher;

    @Autowired
    public UserRestController(SimpleApplicationEventMulticaster eventPublisher) {
        eventPublisher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        this.multiEventPublisher = eventPublisher;

    }
    @Autowired
    //Synchronous listener call (block)
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){

        User user = new User(id,"yuvraj");
        UserEvent userEvent = new UserEvent(this,user);
        System.out.println("Controller thread :: "+Thread.currentThread().getId());
       // eventPublisher.publishEvent(userEvent);
        multiEventPublisher.multicastEvent(userEvent);
       return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);

    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id){

        Employee employee = new Employee(id,"eyuvraj");
        EmployeeEvent userEvent = new EmployeeEvent(this,employee);
        System.out.println("Controller thread :: "+Thread.currentThread().getId());
        eventPublisher.publishEvent(userEvent);
        //eventPublisher.multicastEvent(userEvent);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employee);

    }
}
