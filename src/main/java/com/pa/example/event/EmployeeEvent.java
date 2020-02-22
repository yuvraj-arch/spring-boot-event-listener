package com.pa.example.event;

import com.pa.example.data.Employee;
import com.pa.example.data.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;

public class EmployeeEvent extends ApplicationEvent {
    private  Employee employee;
    private boolean admin;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public EmployeeEvent(Object source,Employee employee) {
        super(source);
        this.employee = employee;
        this.admin = true;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
