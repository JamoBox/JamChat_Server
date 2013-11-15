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

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Handles client input and calls the interpreter.
 *
 * @author Pete Wicken
 */
public class ClientReader implements Runnable {

    private Client client;

    public ClientReader(Client client) {
        this.client = client;
    }

    /**
     * Reads the client input and sends it to the client input interpreter.
     *
     * @see ClientInputHandler
     */
    public void run() {
        try {
            BufferedReader in = client.getClientReader();
            String input;
            while ((input = in.readLine()) != null) {
                ClientInputHandler.interpret(client, input.split(" "));
            }
            in.close(); // Close the reader when we are done with it.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

