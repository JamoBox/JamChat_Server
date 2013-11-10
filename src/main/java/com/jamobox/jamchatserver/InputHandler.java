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

/**
 * Handles commands given at runtime by the person running the server.
 *
 * @author Pete Wicken
 */
public class InputHandler {

    public boolean executeCommand(String command) {
        String[] args;

        if (command != null && !(command.equals(" ")))
            args = command.split(" ");
        else
            return false;

        if (args.length != 0)
            switch (args[0]) {
                case "stop":
                    //TODO: Stop server.
                    break;
                case "restart":
                    //TODO Restart server.
                    break;
                case "clients":
                    //TODO: List all connected clients.
                    break;
                case "kill": // This may be moved to its own method.
                    if (args.length >= 2) {
                        Client client = null;
                        if (ClientList.getList().containsKey(args[1])) {
                            client = ClientList.getClient(args[1]);
                            if (args.length == 2) {
                                client.disconnect();
                                return true;
                            } else {
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

                default:
                    System.out.printf("\"%s\" is not a valid command.\n", args[0]);
                    JamChatServer.printUsage(JamChatServer.ArgType.RUN_ARGS);
                    return false;
            }

        return false; // This is only here to keep the compiler happy for the mean time.
    }
}

