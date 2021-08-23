package cn.ecomb.park.state.machine.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;

import javax.annotation.Resource;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
@SpringBootApplication
public class StateMachineApplication implements CommandLineRunner {

    @Resource
    private StateMachine<States, Events> stateMachine;


    public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
        stateMachine.sendEvent(Events.E3);
    }
}
