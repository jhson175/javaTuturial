package com.factory;

import com.model.Computer;
import com.model.PC;
import com.model.Server;

public class ComputerFactory {

    public static Computer getComputer(String type, String ram, String hdd, String cpu){
        if("PC".equalsIgnoreCase(type)) return new PC(ram, hdd, cpu);
        else if("Server".equalsIgnoreCase(type)) return new Server(ram, hdd, cpu);
        return null;
    }
}
