/*
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

package com.jamobox.jamchatserver;

//TODO: Make this cleaner
/**
 * Contains string constants for the server logger.
 *
 * @author Pete Wicken
 */
public abstract interface LogMessages {

    /**
     * A client username should be appended to this.
     */
    public static final String ERR_SENDMSG = "Could not send message to client ";

    public static final String ERR_INIT = "Could not initialize daemon! See stack trace for details!";

    /**
     * A client username should be appended to this.
     */
    public static final String ERR_CLIENT_DIS = "Could not disconnect the client ";

}
