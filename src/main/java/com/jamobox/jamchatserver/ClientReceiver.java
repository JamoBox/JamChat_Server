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

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * Assigns connecting clients usernames and adds them to a client array.
 *
 * @author Pete Wicken
 */
public class ClientReceiver implements Runnable {

    private Client client;

    public ClientReceiver(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = client.getClientReader();
            while (in.readLine() != null) {
                //TODO: Handle client input
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

