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
import main.java.com.jamobox.jamchatserver.clients.ClientList;
import main.java.com.jamobox.jamchatserver.clients.ClientReader;
import main.java.com.jamobox.jamchatserver.clients.ClientSocket;

import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;
import java.util.logging.Logger;


/**
 * Main server class. This class contains many of the essential server methods,
 * such as ways of shutting it down, restarting it and running it. All of the server
 * commands are also documented here; any commands being added to the server should be
 * declared in either the serverArgs array or the runtimeArgs array depending on what the
 * command type is. Command documentation goes into the `getArgDescription` method.
 *
 * @author Pete Wicken
 */
public class JamChatServer {

    private static long startTime;
    private static Logger log;
    private static ClientSocket clientSocket;
    private static ClientList clientList;
    private static boolean running = false;
    private static final String version = "0.2.1";
    private static String[] serverArgs = {"start","debug","version", "verbose"}; //Program args
    private static String[] runtimeArgs = {"stop", "restart", "clients", "kill", "uptime"}; //Run time args

    /**
     * The type of arguments being used. Arguments used are either program
     * arguments (arguments given when launching the server [e.g. java -jar jamchatserver start]
     * where 'start' is argument 0), or runtime arguments (arguments given by the user running
     * the server).
     *
     * @see InputHandler
     */
    public enum ArgType {
        PROG_ARGS, RUN_ARGS
    }

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
            clientList = ClientList.getInstance();
            return true;
        } catch (IOException e) {
            if (e instanceof BindException)
                System.out.println("An instance of JamChat Server is already running! Check your processes!");
            else
                e.printStackTrace();
            return false;
        }
    }

    /**
     * Main server run method. Parses the program argument given and deals
     * with it accordingly.
     *
     * @param args The given program arguments
     */
    public static void main(String[] args) {

        //TODO: Move command components to separate methods.
        if (args.length == 1)
            switch (args[0]) {

                /***************/
                case "start":
                    start();
                    break;

                /***************/
                case "version":
                    System.out.printf("JamChat Server version %s. Copyright (C) 2013 Pete Wicken.\n", getVersion());
                    System.exit(0);
                    break; // Unreachable code; here to keep the IDE formatting happy

                /***************/
                case "debug":
                    //TODO implement debug feature. fall through to verbose.

                /***************/
                case "verbose":
                    //TODO: implement verbose option to print more (detailed) log messages.
                    break;

                /***************/
                default:
                    printUsage(ArgType.PROG_ARGS);
                    break;
            }
        else
            printUsage(ArgType.PROG_ARGS);

    }

    /**
     * Listens for connecting clients. Once a client connects
     * a new thread is created to handle the input stream from
     * the client.
     *
     * @see Client
     * @see ClientReader
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
     * Prints the program usage text to the standard output device. The ArgType determines whether the argument
     * is a program argument, or an argument given at runtime.
     *
     * @param type The type of argument
     */
    public static void printUsage(ArgType type) {
        System.out.printf("JamChat Server version %s. Copyright (C) 2013 Pete Wicken.\n\n", getVersion());
        System.out.println("Usage:");
        if (type.equals(ArgType.PROG_ARGS)) {
            for (String arg: serverArgs)
                System.out.printf("\t%s: %s\n",arg, getArgDescription(arg));
            System.out.println();
        } else {
            for (String arg: runtimeArgs)
                System.out.printf("\t%s: %s\n",arg, getArgDescription(arg));
        }
    }

    /**
     * Gets the description of the given program argument. All added arguments _MUST_ be also listed in the
     * corresponding args array to maintain a consistent user feedback. If the argument is not in the array an empty
     * string is returned in attempt to not destroy any formatting that implements this method.
     *
     * @param arg The argument to get the description of. Arguments _MUST_ be listed in one of the arg arrays.
     * @return The description of the argument. Returns empty string if arg is not listed in an args array.
     */
    private static String getArgDescription(String arg) {
        switch (arg) {
            case "start":    return "Starts the JamChat Server, begins accepting client connections.";
            case "version":  return "Prints the JamChat Server version.";
            case "debug":    return "Starts the JamChat Server in debug mode; also uses verbose features.";
            case "verbose":  return "Starts the JamChat Server in verbose mode.";
            case "stop":     return "Disconnects all clients and safely shuts down the server.";
            case "restart":  return "Disconnects all clients and restarts the server.";
            case "clients":  return "Prints the information of all connected clients.";
            case "kill":     return "Kills the given client's connection. A client username must be given.";
            case "uptime":   return "Prints the server uptime to the terminal.";
            default:         return "";
        }
    }

    /**
     * Safely shut down the server. This will cleanly disconnect all connected
     * clients with a shutdown message before setting the `running` variable to false.
     * This means the client while loop finishes and the main thread is allowed to safely
     * and cleanly finish with exit code 0.
     */
    public static void shutdown() {
        System.out.println("Server shutting down!");
        for (Client client : clientList.values())
            client.disconnect("Server shutting down!");
        running = false;
    }

    /**
     * Safely restart the server. Cleanly disconnects all connected clients
     * before closing the server socket. Once all clients are disconnected and
     * the socket is closed, the `run()` method is called to start the server back
     * up again.
     */
    public static void restart() {
        System.out.println("Server restarting!");
        for (Client client : clientList.values())
            client.disconnect("Server shutting down!");
        try {
            clientSocket.closeSocket();
            start();
        } catch (IOException e) {
            log.warning("Server did not fully restart!");
            e.printStackTrace();
        }
    }

    /**
     * Start the server. Begins listening for clients and launches the
     * interactive terminal. Each connecting client will be given its own thread
     * to listen for any output from the client.
     */
    private static void start() {

        if (!(initialize())) {
            log.severe(LogMessages.ERR_INIT);
            shutdown();
            System.exit(0);
        }

        InputHandler handler = new InputHandler();
        System.out.printf("JamChat Server version %s. Copyright (C) 2013 Pete Wicken.\n", getVersion());
        System.out.printf("\nStarting server...\n");

        running = true;
        startTime = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                acceptClients();
            }
        });

        System.out.printf("Done.\n");
        System.out.printf("Now accepting clients on port %d\n\n", Defaults.DEF_PORT);
        while (isRunning()) {
            System.out.print("$ ");
            handler.executeCommand(new Scanner(System.in).nextLine().toLowerCase());
        }

    }

    /**
     * @return The program version number.
     */
    public static String getVersion() {
        return version;
    }

    /**
     * @return The time(ms) the server started.
     */
    public static long getStartTime() {
        return startTime;
    }

    /**
     * Get the server logger.
     *
     * @return Logger com.jamobox.jamchatserver
     */
    public static Logger getLogger() {
        return log;
    }

    /**
     * @return The run status of the server. This is dependant on setRunning being called appropriately.
     */
    public static boolean isRunning() {
        return running;
    }

}
