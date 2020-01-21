package com.dvpie.utilities.chatbot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBot {
    MessageMemory currentMessages;
    Person[] people;
    Person currentPerson;
    public ChatBot() {
        currentMessages = new MessageMemory();
        currentPerson = new Person();
        //TODO: Initiate Opening People save and such
    }

    public String interpret(String message) {
        String statement = new String(message);
        message = message.toLowerCase();
        currentMessages.addMessageToMemory(message);
        String response = "Talk to me.";
        //Lets check for Greetings
        {
            Pattern pattern;
            Matcher match;
            //Greetings 1:
            {
                for(String ptrn : MessagePatterns.Greetings) {
                    pattern = Pattern.compile(ptrn);
                    match = pattern.matcher(message);
                    if(match.matches()) {
                        response = Math.random() >= 0.5 ? "Hello, who am I talking to?" :"Hey, wassup!";
                        response = Math.random() < 0.8 ? response : "Who are you?";

                        return response;
                    }
                }
            }
            //Formal Greetings 2:
            {

            }
            //Formal Greetings 1:
            {
                for(String ptrn : MessagePatterns.FormalGreetings1) {
                    pattern = Pattern.compile(ptrn);
                    match = pattern.matcher(message);
                    if(match.matches()) {
                        String firstName = match.group(1);
                        currentPerson.setFirstName(firstName);
                        //TODO: Check if this person exists.
                        response = Math.random() >= 0.5 ? "Hey what\'s up " + firstName + "?" : "Ayyyy " + firstName + "!";
                        response = Math.random() < 0.8 ? response : "Hey there " + firstName + "!";

                        return response;
                    }
                }
            }
        }

        //Let's check for pets in a pet block
        {
            Pattern pattern;
            Matcher match;
            for(String ptrn : MessagePatterns.MentioningPet) {
                pattern = Pattern.compile(ptrn);
                match = pattern.matcher(message);
                if(match.matches()) {
                    String pet = match.group(2);
                    response = "Tell me more about your " + pet;
                    //TODO: Initiate Pet Memory

                    return response;
                }
            }
        }
        return response;
    }
}
