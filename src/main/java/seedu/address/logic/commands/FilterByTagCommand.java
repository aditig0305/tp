package seedu.address.logic.commands;


import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonHasTagPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all persons in address book according to their tag.
 */
public class FilterByTagCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Filter by Command has not been implemented yet.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tag is"
          + "the specified tag name (case-insensitive) and displays them as a list with index numbers.\n"
          + "Parameters: t/TAG_NAME + [t/MORE_TAG_NAMES...]\n"
          + "Example: " + COMMAND_WORD + "t/High Risk t/Low Risk";

    private final PersonHasTagPredicate predicate;

    public FilterByTagCommand(PersonHasTagPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
              String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
