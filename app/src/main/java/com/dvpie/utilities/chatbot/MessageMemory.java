package com.dvpie.utilities.chatbot;

import java.util.Arrays;

public class MessageMemory {
    private String[] memory;

    public MessageMemory() {
        memory = new String[1];
    }
    public MessageMemory(int size) {
        memory = new String[size];
    }

    //Memory Handling
    public void addMessageToMemory(String message) {
        if(memory.length == 1 && (memory[0] == null || memory[0].equals(""))) {
            memory[0] = message;
            return;
        }
        int newSize = memory.length + 1;
        String[] newMemory = new String[newSize];
        int index = 0;
        for(String msg : memory) {
            newMemory[index++] = msg;
        }
        newMemory[index] = message;
        memory = newMemory.clone();
    }
    public String getMessage(int index) {
        if(index == memory.length)
            return memory[0];
        return memory[memory.length - (index + 1)];
    }
    public int getCurrentMemoryLength() {
        return memory.length;
    }

}
