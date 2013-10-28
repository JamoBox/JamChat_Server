package main.java.com.jamobox.jamchatserver.clients;

import main.java.com.jamobox.jamchatserver.JamChatServer;
import main.java.com.jamobox.jamchatserver.LogMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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

public abstract class Client extends Socket {

    /**
     * @return The client's unique username
     */
    public abstract String getUsername();

    /**
     * @return The client's ip address
     */
    public abstract String getAddress();

    public abstract void setUsername(String username);

    /**
     * Get the client's input stream.
     * @return BufferedReader object of client input stream.
     * @throws IOException
     */
    public BufferedReader getClientReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public void disconnect() {
        try {
            this.close();
        } catch (IOException e) {
            e.printStackTrace();
            JamChatServer.getLogger().severe(LogMessages.ERR_CLIENT_DIS+getUsername());
        }
    }

}

