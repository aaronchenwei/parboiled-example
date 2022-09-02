package com.example.parboiled.abc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.parboiled.Parboiled;
import org.parboiled.test.ParboiledTest;

public class AbcTest extends ParboiledTest<AbcParser> {

    @Override
    protected void fail(String message) {
        Assertions.fail(message);
    }

    @Override
    protected void assertEquals(Object actual, Object expected) {
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void test() {
        AbcParser parser = Parboiled.createParser(AbcParser.class);
        test(parser.S(), "aabbcc")
            .hasNoErrors()
            .hasParseTree("" +
                "[S] 'aabbcc'\n" +
                "  [OneOrMore] 'aa'\n" +
                "    ['a'] 'a'\n" +
                "    ['a'] 'a'\n" +
                "  [B] 'bbcc'\n" +
                "    ['b'] 'b'\n" +
                "    [Optional] 'bc'\n" +
                "      [B] 'bc'\n" +
                "        ['b'] 'b'\n" +
                "        [Optional]\n" +
                "        ['c'] 'c'\n" +
                "    ['c'] 'c'\n");
    }

    @Test
    public void testFail1() {
        AbcParser parser = Parboiled.createParser(AbcParser.class);
        testWithRecovery(parser.S(), "aabbbcc")
            .hasErrors("" +
                "Invalid input 'b', expected 'c' (line 1, pos 5):\n" +
                "aabbbcc\n" +
                "    ^\n")
            .hasParseTree("" +
                "[S]E 'aabbcc'\n" +
                "  [OneOrMore] 'aa'\n" +
                "    ['a'] 'a'\n" +
                "    ['a'] 'a'\n" +
                "  [B]E 'bbcc'\n" +
                "    ['b'] 'b'\n" +
                "    [Optional]E 'bc'\n" +
                "      [B]E 'bc'\n" +
                "        ['b'] 'b'\n" +
                "        [Optional]\n" +
                "        ['c'] 'c'\n" +
                "    ['c'] 'c'\n"
            );
    }

    @Test
    public void testFail2() {
        AbcParser parser = Parboiled.createParser(AbcParser.class);
        testWithRecovery(parser.S(), "aabcc")
            .hasErrors("" +
                "Invalid input 'c', expected 'b' (line 1, pos 4):\n" +
                "aabcc\n" +
                "   ^\n")
            .hasParseTree("" +
                "[S]E 'aabbcc'\n" +
                "  [OneOrMore] 'aa'\n" +
                "    ['a'] 'a'\n" +
                "    ['a'] 'a'\n" +
                "  [B]E 'bbcc'\n" +
                "    ['b'] 'b'\n" +
                "    [Optional]E 'bc'\n" +
                "      [B]E 'bc'\n" +
                "        ['b']E 'b'\n" +
                "        [Optional]\n" +
                "        ['c'] 'c'\n" +
                "    ['c'] 'c'\n"
            );
    }

}
