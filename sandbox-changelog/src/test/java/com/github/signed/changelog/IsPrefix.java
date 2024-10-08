package com.github.signed.changelog;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsPrefix extends TypeSafeDiagnosingMatcher<String> {

    public static Matcher<String> isAPrefixIn(String goldMaster) {
        return new IsPrefix(goldMaster);
    }

    private final String longer;

    public IsPrefix(String longer) {
        this.longer = longer;
    }

    public String greatestCommonPrefix(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        return a.substring(0, minLength);
    }

    @Override
    protected boolean matchesSafely(String item, Description description) {
        boolean isPrefix = longer.startsWith(item);
        if (!isPrefix) {
            String sharedPrefix = greatestCommonPrefix(item, longer);
            int blub = sharedPrefix.lastIndexOf('\n') + 1;
            String startOfMismatchLine = sharedPrefix.substring(blub);

            int start = sharedPrefix.length();
            int numberOfCharactersAfter = 30;


            String before = sharedPrefix.substring(blub - 30, blub);

            description.appendText(replaceNewLine(before));
            description.appendText("\n");
            description.appendText(replaceNewLine(startOfMismatchLine + "|> " + nextIfThere(start, numberOfCharactersAfter, longer)));
            description.appendText("\n");
            description.appendText(replaceNewLine(startOfMismatchLine + "|> " + nextIfThere(start, numberOfCharactersAfter, item)));
        }
        return isPrefix;
    }

    private String replaceNewLine(String string) {
        return string.replaceAll("\n", "\\\\n");
    }

    private String nextIfThere(int start, int numberOfCharactersAfter, String string) {
        int endIndex = Math.min(string.length(), start + numberOfCharactersAfter);
        return string.substring(start, endIndex);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("being a prefix");
    }
}
