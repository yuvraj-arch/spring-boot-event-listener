package com.pa.example.listener;

import com.pa.example.data.User;
import com.pa.example.event.UserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListner implements ApplicationListener<UserEvent> {
    @Override
    public void onApplicationEvent(UserEvent event) {
        User user =  event.getUser();
        System.out.println("User  name :: "+user.getName());
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
