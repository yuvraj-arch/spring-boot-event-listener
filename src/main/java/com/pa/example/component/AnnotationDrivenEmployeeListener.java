package com.pa.example.component;

import com.pa.example.data.Employee;
import com.pa.example.event.EmployeeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AnnotationDrivenEmployeeListener {
    @Async
    @EventListener
    public void handleEmployeeEvent(EmployeeEvent event) {
        System.out.println("Employee id :: "+event.getEmployee().getEid());
        System.out.println("Source class name :: "+event.getSource().getClass());
        System.out.println("Listener thread :: "+Thread.currentThread().getId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish:: ");
    }

}
