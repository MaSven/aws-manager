package space.smarquardt.aws.manager.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ServiceLoader;

import org.apache.commons.cli.*;

import space.smarquardt.aws.manager.sqsinterface.Sqs;

public class Main {

  public static void main(String[] args) {

    Options options = new Options();

    Option body =
        Option.builder()
            .argName("file")
            .required(true)
            .longOpt("body")
            .hasArg()
            .desc("Filepath to body of message")
            .build();
    Option attributes =
        Option.builder()
            .required(true)
            .argName("file")
            .longOpt("attributes")
            .hasArg()
            .desc("Filepath to attribute file")
            .build();
    Option queueUrl =
        Option.builder()
            .required(true)
            .argName("url")
            .longOpt("queueurl")
            .hasArg()
            .desc("URL to the queu")
            .build();
    Option help = Option.builder().argName("h").longOpt("help").desc("Print help").build();
    options.addOption(body).addOption(attributes).addOption(queueUrl).addOption(help);
    CommandLineParser commandLineParser = new CommandParser();

    try {
      CommandLine commandLine = commandLineParser.parse(options, args, false);

      if (commandLine.hasOption("help")) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("directer", options);
        return;
      }
      String bodyFile = commandLine.getOptionValue("body");

      String bodyContent = Files.readString(Paths.get(bodyFile));
      final var attributeFile = Paths.get(commandLine.getOptionValue("attributes"));
      String queueurl = commandLine.getOptionValue("queueurl");

      Sqs sqs =
          ServiceLoader.load(Sqs.class)
              .findFirst()
              .orElseThrow(() -> new RuntimeException("Could not find sqs"));

      System.out.printf(
          "Return message was %d",
          sqs.sendMessage(queueurl, bodyContent, Sqs.generateAttributes(attributeFile))
              .getStatus());

    } catch (ParseException | IOException e) {
      e.printStackTrace();
      HelpFormatter helpFormatter = new HelpFormatter();
      helpFormatter.printHelp("directer", options);
    }
  }
}
