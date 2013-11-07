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
import main.java.com.jamobox.jamchatserver.clients.ClientReader;
import main.java.com.jamobox.jamchatserver.clients.ClientSocket;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * Main server class. Initializes and runs the server.
 *
 * @author Pete Wicken
 */
public class JamChatServer {

    private static Logger log;
    private static ClientSocket listener;
    private static boolean running = false;

    /**
     * Initialize the server with the essential resources. If initialization fails
     * it is heavily recommended that the daemon closes to prevent further errors.
     *
     * @return true if successful, false if not.
     */
    private static boolean initialize() {
        log = Logger.getLogger("com.jamobox.jamchatserver");
        try {
            listener = new ClientSocket(Defaults.DEF_PORT);
            running = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Main server run method.
     *
     * @param args The given program arguments
     */
    public static void main(String[] args) {

        if (!(initialize())) {
            running = false;
            log.severe(LogMessages.ERR_INIT);
            System.exit(-1);
        }

        acceptClients();
    }

    /**
     * Listens for connecting clients. Once a client connects
     * a new thread is created to handle input the input stream from
     * the client.
     *<p/>
     * NOTE: This may be moved to its own thread at some point.
     * @see Client
     * @see main.java.com.jamobox.jamchatserver.clients.ClientReader
     */
    private static void acceptClients() {
        while (running) try {
            Client client = new Client(listener.openSocket());
            new Thread(new ClientReader(client)).start();
            log.info("Client connected: (" + client.getAddress() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the server logger.
     * @return Logger com.jamobox.jamchatserver
     */
    public static Logger getLogger() {
        return log;
    }
}

