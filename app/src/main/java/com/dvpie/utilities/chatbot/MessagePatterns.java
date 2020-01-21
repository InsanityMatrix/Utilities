package com.dvpie.utilities.chatbot;

public class MessagePatterns {
    public static final String pets = "(dog|cat|turtle|lizard|monkey|bird|parrot|rabbit|snake)";
    public static final String[] MentioningPet =
            {
                    ".*i\\s+have\\s+an?\\s+(new\\s+)?" + pets + ".*",
                    ".*i\\s+got\\s+a\\s+(new\\s+)?" + pets + ".*",
                    ".*have\\s+i\\s+told\\s+you\\s+about\\s+my\\s+(new\\s+)" + pets + ".*",
            };

    public static final String[] Greetings =
            {
                    "^\\s*sup\\??\\s*$",
                    "^\\s*hey\\s*$",
                    "^\\s*hi\\s*$",
                    "^\\s*wassup\\s*$"
            };
    public static final String[] FormalGreetings1 =
            {
                    "^\\s*hey\\s*my\\s*name\\s*is\\s*(\\w+).*",
                    "^\\s*my\\s*name\\s*is\\s*(\\w).*",
                    "^\\s*i\\s*am\\s*(\\w).*"
            };






}
