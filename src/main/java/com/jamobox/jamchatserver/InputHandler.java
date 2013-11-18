package main.java.com.jamobox.jamchatserver;

/**
 * JamChat_Server
 * Copyright (C) 2013 Pete Wicken
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see [http://www.gnu.org/licenses/].
 */

import main.java.com.jamobox.jamchatserver.clients.Client;
import main.java.com.jamobox.jamchatserver.clients.ClientList;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Handles input given at runtime by the person running the server.
 * executeCommand is the 'point of interest' method of this class, other
 * methods in the class are simply used as either utilities or breakdowns
 * of individual parts for readability.
 *
 * @author Pete Wicken
 */
public class InputHandler {

    private ClientList clientList = ClientList.getInstance();

    /**
     * Handles the execution of runtime commands given by the user who
     * is running the server at the terminal. The 'commands' accepted here
     * should also be listed in the runtimeArgs array in JamChatServer and
     * in the argument description method in the same class. The first argument
     * in the given string is considered as the command name. The rest of the arguments
     * (if any) are then parsed individually by the command's case statement and processed
     * there. Empty strings ("" and " ") as well as null strings will simply return false.
     *
     * @param command The command string
     * @return true if command execution was successful, false if not.
     * @see JamChatServer
     */
    public boolean executeCommand(String command) {
        String[] args;

        if (command != null && !(command.equals(" ") || command.equals("")))
            args = command.split(" ");
        else
            return false;

        if (args.length != 0)
            switch (args[0]) {

                /***************/
                case "stop":
                    JamChatServer.shutdown();
                    return true;

                /***************/
                case "restart":
                    JamChatServer.restart();
                    return true;

                /***************/
                case "clients":
                    if (!clientList.isEmpty())
                        for (String username: clientList.keySet())
                            System.out.printf("%s\t%s\n\n", username, clientList.get(username).getAddress());
                    else
                        System.out.println("No clients connected!");
                    return true;

                /***************/
                case "kill":
                    return kill(args);

                /***************/
                case "uptime":
                    return uptime();

                /***************/
                case "broadcast":
                    return broadcast(args);

                /***************/
                default:
                    System.out.printf("\"%s\" is not a valid command.\n", args[0]);
                    JamChatServer.printUsage(JamChatServer.ArgType.RUN_ARGS);
                    return false;
            }
        else
            return false;

    }

    /**
     * Kills a client's connecting using the disconnect method.
     * Performs argument checks on each parameter; args[1] being the
     * client that is being killed, args[2] being the first word of the kill reason.
     * If only two args are given (kill(1) and client_name(2)) then the client is killed
     * with disconnect()'s default message. If there are more arguments then a string is built
     * by concatenating those arguments and the killed is killed with that string as a reason.
     *
     * @param args Kill <ClientUsername> [Reason]
     * @return True if successful, false if not.
     */
    private boolean kill(String[] args) {
        if (args.length >= 2) {
            Client client;
            if (clientList.containsKey(args[1])) {
                client = clientList.get(args[1]);
                if (args.length == 2) {
                    client.disconnect();
                    return true;
                } else {
                    /* Build a string from the given arguments */
                    String reason = "";
                    for (int i = 2; i < args.length; i++) {
                        reason = reason+" "+args[i];
                    }
                    client.disconnect(reason);
                    return true;
                }
            } else {
                System.out.printf("The client: \"%s\" is not currently connected.\n", args[1]);
                return false;
            }
        } else {
            System.out.println("Incorrect usage. \"kill <client_username>\"");
            return false;
        }
    }

    private boolean uptime() {
        PeriodFormatter uptimeFormat = new PeriodFormatterBuilder()
                .appendDays().appendSuffix(" day, ", " days, ")
                .appendHours().appendSuffix(" hour, ", " hours, ")
                .appendMinutes().appendSuffix(" minute and ", " minutes and ")
                .appendSeconds().appendSuffix(" second.", " seconds.")
                .toFormatter();

        System.out.printf("Up-time: %s\n",
                uptimeFormat.print(new Period(System.currentTimeMillis()-JamChatServer.getStartTime())));
        return true;
    }

    private boolean broadcast(String[] args) {
        if (args.length >= 2) {
            /* Create the message from the args */
            String message = "";
            for (int i = 1; i < args.length; i++)
                message = message.concat(String.format("%s ", args[i]));
            /* Send the message to all online clients */
            for (Client client : clientList.values())
                client.sendMessage("BROADCAST "+message);
            System.out.printf("[Server Announcement]: %s\n", message);
            return true;
        } else {
            System.out.println("Incorrect usage. \"broadcast <message>\"");
            return false;
        }
    }

}
