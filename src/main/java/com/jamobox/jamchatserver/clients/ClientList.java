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
public class ClientList extends HashMap<String, Client> {

    public static volatile ClientList instance = null;

    /**
     * Provides a singleton structure for the ClientList. If
     * an instance of the ClientList already exists, then that instance
     * will be returned, to prevent new instances of the list being made.
     * This has also been optimized for concurrency and is therefore thread-safe;
     * it uses double-checked locking so a reference isn't returned before the default
     * constructor is executed.
     *
     * @return ClientList instance
     */
    public static ClientList getInstance() {
        if (instance == null)
            synchronized (ClientList.class) {
                if (instance == null)
                    instance = new ClientList();
            }

        return instance;
    }

}
