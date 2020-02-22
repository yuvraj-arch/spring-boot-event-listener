package com.pa.example.event;

import com.pa.example.data.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserEvent extends ApplicationEvent {
    private User user;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserEvent(Object source,User user) {
        super(source);
        this.user = user;
    }
}
