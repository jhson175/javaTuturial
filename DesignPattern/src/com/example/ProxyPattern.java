package com.example;

import com.design.proxy.CommandExecutor;
import com.design.proxy.CommandExecutorProxy;

public class ProxyPattern {

    public static void main(String[] args){
        CommandExecutor executor = new CommandExecutorProxy("Pankaj", "wrong_pwd");
	try {
            executor.runCommand("ls -ltr");
            executor.runCommand(" rm -rf abc.pdf");
        } catch (Exception e) {
            System.out.println("Exception Message::"+e.getMessage());
        }
		
    }

}
