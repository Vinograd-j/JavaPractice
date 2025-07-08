package net.vinograd.newlookatjava;

import net.vinograd.newlookatjava.console.OperationConsoleListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NewLookAtJavaApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NewLookAtJavaApplication.class, args);

        OperationConsoleListener listener = context.getBean(OperationConsoleListener.class);

        listener.listenToCommands();
    }

}