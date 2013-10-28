package main.java.com.jamobox.jamchatserver;

import main.java.com.jamobox.jamchatserver.clients.Client;
import main.java.com.jamobox.jamchatserver.clients.ClientListener;
import main.java.com.jamobox.jamchatserver.clients.ClientReceiver;

import java.io.IOException;
import java.util.logging.Logger;

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

public class JamChatServer {

    private static Logger log;
    private static ClientListener listener;

    private static boolean initialize() {
        log = Logger.getLogger("com.jamobox.jamchatserver");
        try {
            listener = new ClientListener(Defaults.DEF_PORT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {

        if (!(initialize())) {
            log.severe(LogMessages.ERR_INIT);
            return;
        }

        /* Create a new thread for each client that connects.
         * TODO: Move this to its own method once expanded
         */
        while (true) {
            try {
                Client client = (Client) listener.openSocket();
                new Thread(new ClientReceiver(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    public static Logger getLogger() {
        return log;
    }
}

