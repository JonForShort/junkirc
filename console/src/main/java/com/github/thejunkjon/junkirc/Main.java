package com.github.thejunkjon.junkirc;

import com.github.thejunkjon.junkirc.network.InternetRelayChatConnection;
import org.apache.commons.cli.*;

import java.io.IOException;

import static java.lang.Runtime.getRuntime;

public class Main {

    public static void main(final String[] args) {
        final Options options = new Options();
        options.addOption("h", false, "prints the help information");
        options.addOption("s", true, "Specifies the server to connect to.");
        options.addOption("p", true, "Specifies the port to use.");

        final CommandLineParser commandLineParser = new DefaultParser();
        try {
            final CommandLine commandLine = commandLineParser.parse(options, args, true);
            if (commandLine.hasOption("h")) {
                showHelp(options);
            } else if (!commandLine.hasOption("s")) {
                showHelp(options);
            } else if (!commandLine.hasOption("p")) {
                showHelp(options);
            } else {
                final InternetRelayChatConnection.OnMessageReceivedListener onMessageReceivedListener = System.out::println;
                final InternetRelayChatConnection connection = new InternetRelayChatConnection.Builder(
                        commandLine.getOptionValue("s"),
                        Integer.valueOf(commandLine.getOptionValue("p")),
                        onMessageReceivedListener).build();
                connection.open();
                getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to connect to server. Reason: " + e.getMessage());
        }
    }

    private static void showHelp(Options options) {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java: ", options);
    }
}