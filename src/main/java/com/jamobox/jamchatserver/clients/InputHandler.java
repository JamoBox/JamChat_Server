package main.java.com.jamobox.jamchatserver.clients;

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

/**
 * Handles client input
 *
 * @author Pete Wicken
 */
public class InputHandler {

    /**
     * Interprets the input and sorts by prefix code.
     *
     * @param args The input to interpret
     * @throws NullPointerException
     */
    public static void interpret(Client sender, String[] args) throws NullPointerException {
        if (args != null) {
            String prefix = args[0];

            switch (prefix) {
                case ClientCodes.SET_USERNAME:
                    if (args.length >= 2)
                        if (ClientList.getList().containsKey(args[1]))
                            rejectClient(sender, "A client already exists with the username "+args[1]);
                        else
                            ClientList.add(args[1], sender);
                    else
                        rejectClient(sender, "Your client did not send the correct amount of arguments!");
                break;

                case ClientCodes.DO_COMMAND:
                    //TODO implement this and other codes
                    break;
            }
        } else
            throw new NullPointerException();
    }

    /**
     * Reject a client's connection for a given reason. This should mainly be used by
     * authentication methods and any other methods that are called when a user first connects,
     * however this may also be used to cleanly disconnect the client, by the client's own request.
     *
     * @param client The client to reject.
     * @param reason The reason for the disconnection.
     */
    private static void rejectClient(Client client, String reason) {
        //TODO: Call a method from client output stream class to send an error message.
        client.disconnect();
    }

}

