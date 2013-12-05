package com.jamobox.jamchatserver.clients;

import com.jamobox.jamchatserver.JamChatServer;
import com.jamobox.jamchatserver.LogMessages;

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

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
    private String username;
    private InetAddress address;

    /**
     * Creates a Client object setting the client's socket
     * to be the one passed through the parameters. Also sets the
     * client address to be the local address of the socket.
     *
     * @param socket The socket to bind the client to.
     */
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
        if (reader == null)
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        return reader;
    }

    /**
     * Get the client's output stream.
     * @return PrintWriter object of client output stream.
     * @throws IOException
     */
    public PrintWriter getClientWriter() throws IOException {
        if (writer == null)
            writer = new PrintWriter(socket.getOutputStream());

        return writer;
    }

    /**
     * Attempts to send the given message to the client's output stream.
     *
     * @param message The message to send the client.
     */
    public void sendMessage(String message) {
        try {
            PrintWriter out = getClientWriter();
            out.write(String.format("%s\n", message));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JamChatServer.getLogger().severe(LogMessages.ERR_SENDMSG);
        }
    }

    /**
     * Attempts to disconnect the client from the server. Uses default disconnect message.
     */
    public void disconnect() {
        disconnect("Disconnected by server!");
    }

    /**
     * Disconnect a client's connection for a given reason. This should mainly be used by
     * authentication methods and any other methods that are called when a user first connects,
     * however this may also be used to cleanly disconnect the client, by the client's own request or by
     * the server itself.
     *
     * @param reason The reason for the disconnection.
     */
    public void disconnect(String reason) {
        try {
            sendMessage(reason);
            socket.close();
            ClientList.getInstance().remove(this.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
            JamChatServer.getLogger().severe(LogMessages.ERR_CLIENT_DIS+getUsername());
        }
    }

}
