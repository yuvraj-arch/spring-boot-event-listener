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
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.lang.Thread.State;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<Employee> getEmployee(@PathVariable String id) throws InterruptedException {

        Employee employee = new Employee(id,"eyuvraj");
        EmployeeEvent userEvent = new EmployeeEvent(this,employee);
        System.out.println("Controller thread :: "+Thread.currentThread().getId());
        eventPublisher.publishEvent(userEvent);
        //eventPublisher.multicastEvent(userEvent);
        Thread.sleep(100000L);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employee);

    }


    @GetMapping("/data")
    public ResponseEntity<String> getData() throws IOException {
        System.out.println("Data started");
        List<String> data = new ArrayList<>();
        while(true){
            File file = ResourceUtils.getFile("classpath:data/sample_data.txt");
            String content = new String(Files.readAllBytes(file.toPath()));
            data.add(content);
        }
    }

    @GetMapping("/threadDump")
    public ResponseEntity<String> getThreadData(){
        String body = Thread.getAllStackTraces().entrySet().stream().map(entry -> {
            Thread thread = entry.getKey();
            StackTraceElement[] elements = entry.getValue();

            StringBuilder builder = new StringBuilder(
                    "\"" + thread.getName() + "\"" + (thread.isDaemon() ? " daemon" : "") +
                            " prio=" + thread.getPriority() +
                            " tid=" + thread.getId() +
                            " " + thread.getState().name().toLowerCase() + "\n");
            builder.append("   " + State.class.getName() + ": " + thread.getState().name().toUpperCase() + "\n");
            for (StackTraceElement element : elements) {
                builder.append("      at " + element.getClassName() + "." + element.getMethodName());
                if (element.isNativeMethod()) {
                    builder.append("(Native method)");
                } else if (element.getFileName() == null) {
                    builder.append("(Unknown source)");
                } else {
                    builder.append("(" + element.getFileName() + ":" + element.getLineNumber() + ")");
                }
                builder.append("\n");
            }
            return builder.toString();

        }).collect(Collectors.joining());
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(body);
    }
}
