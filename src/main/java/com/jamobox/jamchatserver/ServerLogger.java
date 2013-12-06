package com.jamobox.jamchatserver;

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

public class ServerLogger {

    private Logger log;
    private boolean doLog;

    public ServerLogger(boolean doLog) {
        this.doLog = doLog;
        this.log = Logger.getLogger("com.jamobox.jamchat");
    }

    public void info(String info) {
        if (doLog)
            log.info(info);
    }

    public void warning(String info) {
        if (doLog)
            log.warning(info);
    }

    public void severe(String info) {
        if (doLog)
            log.severe(info);
    }

}

