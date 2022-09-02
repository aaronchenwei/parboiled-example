package com.example.parboiled.abc;

import org.parboiled.Parboiled;
import org.parboiled.common.StringUtils;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Scanner;

import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class Main {

    public static void main(String[] args) {
        AbcParser parser = Parboiled.createParser(AbcParser.class);

        while (true) {
            System.out.print("Enter an a^n b^n c^n expression (single RETURN to exit)!\n");
            String input = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(input)) {
                break;
            }

            ParsingResult<?> result = new ReportingParseRunner(parser.S()).run(input);

            if (!result.parseErrors.isEmpty()) {
                System.out.println(ErrorUtils.printParseError(result.parseErrors.get(0)));
            } else {
                System.out.println(printNodeTree(result) + '\n');
            }
        }
    }

}
