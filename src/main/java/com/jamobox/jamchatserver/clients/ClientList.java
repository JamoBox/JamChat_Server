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

import java.util.HashMap;

/**
 * Manages list of connected clients and contains methods
 * for manipulating the list and obtaining/adding data to it.
 *
 * @author Pete Wicken
 */
public class ClientList {

    private static HashMap<String, Client> clients = new HashMap<>();

    /**
     * Add a client to the list of connected clients
     *
     * @param username Unique client key
     * @param client The client to add to the list
     */
    public static void add(String username, Client client) {
        clients.put(username, client);
    }

    /**
     * Remove a client from the list
     *
     * @param username the client to remove
     */
    public static void remove(String username) {
        clients.remove(username);
    }

    /**
     * Get the client in the list that has the given username
     *
     * @param username The username of the client to return
     * @return The client with a matching username
     */
    public static Client getClient(String username) {
        return clients.get(username);
    }

    /**
     * @return The client list
     */
    public static HashMap<String, Client> getList() {
        return clients;
    }

}

