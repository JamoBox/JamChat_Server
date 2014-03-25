/*
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

package com.jamobox.jamchatserver.clients;

import com.jamobox.jamchatserver.JamChatServer;

/**
 * Handles client input
 *
 * @author Pete Wicken
 */
public class ClientInputHandler {

    //TODO: Clean this up!
    /**
     * Interprets the input and sorts by prefix code. Performs a check on the
     * ClientLevel before executing any commands to ensure they are signed
     * clients (logged in). Some commands may be used by unsigned clients in
     * order to maintain fundamental functionality, for example unsigned clients may
     * use the "USERNAME" command so they may sign themselves under that name and become
     * classified as a signed client for that session.
     *
     * @param sender The client that sent the command
     * @param args The input to interpret
     * @return True if the event was successful, false if not.
     * @see Client.ClientLevel
     */
    public static boolean interpret(Client sender, String[] args) {
        if (args != null) {
            String prefix = args[0];

            if (sender.getClientLevel().equals(Client.ClientLevel.UNSIGNED))
                if (!args[0].equalsIgnoreCase(ClientCodes.SET_USERNAME)) {
                    sender.sendMessage("Your client must set a username before sending any other requests!");
                    return false;
                }

            switch (prefix.toUpperCase()) {

                case ClientCodes.SET_USERNAME:
                    if (args.length >= 2) {
                        if (ClientList.getInstance().containsKey(args[1])) {
                            sender.sendMessage("A client already exists with the username "+args[1]);
                            return false;
                        } else {
                            ClientList.getInstance().put(args[1], sender);
                            sender.setClientLevel(Client.ClientLevel.SIGNED);
                            return true;
                        }
                    } else {
                        sender.disconnect("Your client did not send the correct amount of arguments!");
                        return false;
                    }

                case ClientCodes.PING:
                    sender.sendMessage("PONG");
                    JamChatServer.getLogger().info(sender.getUsername()+" sent a ping to the server.");
                    return true;

                case ClientCodes.DISCONNECT:
                    sender.disconnect();
                    return true;

                default:
                    sender.sendMessage("Unknown command!");
                    return false;
            }
        } else {
            throw new NullPointerException();
        }
    }

}
