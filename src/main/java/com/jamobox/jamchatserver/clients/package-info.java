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

/**
 * Provides all the classes that handle client connections
 * and communications, as well as maintaining a current list of
 * connected clients.
 * <p/>
 * Included in the package is also an abstract
 * interface that defines the client response codes as string constants.
 * The request codes of the JamChat_Core/JamChat_Client MUST always be
 * in sync with these codes.
 *
 * @see com.jamobox.jamchatserver.clients.ClientCodes
 */
package com.jamobox.jamchatserver.clients;
