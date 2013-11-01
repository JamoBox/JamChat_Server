package main.java.com.jamobox.jamchatserver.clients;

import main.java.com.jamobox.jamchatserver.JamChatServer;
import main.java.com.jamobox.jamchatserver.LogMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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

/**
 * This class defines what a client is. ALL clients MUST be an instance of this
 * class, and any possible different client types (if this is ever implemented)
 * should also extend this class.
 *
 * @author Pete Wicken
 */
public class Client {

    private Socket socket;
    private String username;
    private InetAddress address;


    public Client(Socket socket) {
        this.socket = socket;
        this.address = socket.getLocalAddress();
    }

    /**
     * @return The client's unique username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The client's ip address.
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Set the username of the client.
     * @param username The username to use.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the client's input stream.
     * @return BufferedReader object of client input stream.
     * @throws IOException
     */
    public BufferedReader getClientReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Get the client's output stream.
     * @return PrintWriter object of client output stream.
     * @throws IOException
     */
    public PrintWriter getClientWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public void sendMessage(String s) {
        try {
            PrintWriter out = getClientWriter();
            out.write(s);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JamChatServer.getLogger().severe(LogMessages.ERR_SENDMSG);
        }
    }

    /**
     * Attempt to disconnect the client from the server.
     */
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            JamChatServer.getLogger().severe(LogMessages.ERR_CLIENT_DIS+getUsername());
        }
    }

}

