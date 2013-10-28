package main.java.com.jamobox.jamchatserver.exceptions;

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
 * @author Pete Wicken
 */
public class DuplicateClientException extends Exception {

    /**
     * Thrown when a client tries connecting using an existing username,
     * or if the same client tries connecting more than once in a session.
     *
     * @param s The exception message
     */
    public DuplicateClientException(String s) {

    }

}

