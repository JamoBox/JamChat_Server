package main.java.com.jamobox.jamchatserver;

import java.io.IOException;
import java.net.ServerSocket;
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

public class ClientListener {

    private int port;

    public ClientListener() {
        this.port = 23239; //Default port
    }

    public ClientListener(int port) {
        this.port = port;
    }

    /**
     * Attempts to open a socket and listen on the ChatListener port.
     *
     * @return clientSocket The client connecting
     * @throws IOException
     */
    public Socket openSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        return clientSocket;
    }

}

