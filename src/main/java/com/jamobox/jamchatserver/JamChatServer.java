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
    private static ClientSocket clientSocket;
    private static boolean running = false;
    private static final String version = "0.2.1";
    private static String[] serverArgs = {"start","debug","version", "verbose"};

    /**
     * Initialize the server with the essential resources. If initialization fails
     * it is heavily recommended that the daemon closes to prevent further errors.
     *
     * @return true if successful, false if not.
     */
    private static boolean initialize() {
        log = Logger.getLogger("com.jamobox.jamchatserver");
        try {
            clientSocket = new ClientSocket(Defaults.DEF_PORT);
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

        if (args.length == 1) {
            switch (args[0]) {
                case "start":
                    acceptClients();
                    break;
                case "version":
                    System.out.printf("JamChat Server version %s. Copyright (C) 2013 Pete Wicken.\n", getVersion());
                    System.exit(0);
                case "debug":
                    //TODO implement debug feature. fall through to verbose.
                case "verbose":
                    //TODO: implement verbose option to print more (detailed) log messages.
                    break;
                default:
                    printUsage();
                    break;
            }
        } else {
            printUsage();
        }

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
            Client client = new Client(clientSocket.openSocket());
            new Thread(new ClientReader(client)).start();
            log.info("Client connected: (" + client.getAddress() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the program usage text to the standard output device.
     */
    private static void printUsage() {
        System.out.printf("JamChat Server version %s. Copyright (C) 2013 Pete Wicken.\n\n", getVersion());
        System.out.println("Usage:");
        for (String arg: serverArgs)
            System.out.printf("\t%s: %s\n",arg, getArgDescription(arg));
        System.out.println();
    }

    /**
     * Gets the description of the given program argument. All added arguments _MUST_ be also listed in the
     * serverArgs array to maintain a consistent user feedback. If the argument is not in the array an empty
     * string is returned in attempt to not destroy any formatting that implements this method.
     *
     * @param arg The argument to get the description of. Arguments _MUST_ be listed in serverArgs array.
     * @return The description of the argument. Returns empty string if arg is not listed in serverArgs array.
     */
    private static String getArgDescription(String arg) {
        switch (arg) {
            case "start":    return "Starts the JamChat Server, begins accepting client connections.";
            case "version":  return "Prints the JamChat Server version.";
            case "debug":    return "Starts the JamChat Server in debug mode; also uses verbose features.";
            case "verbose":  return "Starts the JamChat Server in verbose mode.";
            default:         return "";
        }
    }

    /**
     * @return The program version number.
     */
    public static String getVersion() {
        return version;
    }

    /**
     * Get the server logger.
     * @return Logger com.jamobox.jamchatserver
     */
    public static Logger getLogger() {
        return log;
    }
}

