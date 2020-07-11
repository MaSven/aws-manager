package de.otto.ipanema.awssqstestmessagesender.cli;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.*;

public class CommandParser extends DefaultParser {
  private final ArrayList<String> notParsedArgs = new ArrayList<>();

  public String[] getNotParsedArgs() {
    return notParsedArgs.toArray(new String[notParsedArgs.size()]);
  }

  @Override
  public CommandLine parse(Options options, String[] arguments, boolean stopAtNonOption)
      throws ParseException {
    if (stopAtNonOption) {
      return parse(options, arguments);
    }
    List<String> knownArguments = new ArrayList<>();
    notParsedArgs.clear();
    boolean nextArgument = false;
    for (String arg : arguments) {
      if (options.hasOption(arg) || nextArgument) {
        knownArguments.add(arg);
      } else {
        notParsedArgs.add(arg);
      }

      nextArgument = options.hasOption(arg) && options.getOption(arg).hasArg();
    }
    return super.parse(options, knownArguments.toArray(new String[knownArguments.size()]));
  }
}
