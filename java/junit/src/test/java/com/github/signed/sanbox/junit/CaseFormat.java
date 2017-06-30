package com.github.signed.sanbox.junit;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;

import static com.google.common.base.Preconditions.checkNotNull;

public enum CaseFormat {
    /**
     * Java variable naming convention, e.g., "lowerCamel".
     */
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        @Override
        String normalizeWord(String word) {
            return firstCharOnlyToUpper(word);
        }
    },

    SENTENCE(CharMatcher.is(' '), " ") {
        @Override
        String normalizeWord(String word) {
            return Ascii.toLowerCase(word);
        }
    };

    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
        this.wordBoundary = wordBoundary;
        this.wordSeparator = wordSeparator;
    }

    /**
     * Converts the specified {@code String str} from this format to the specified {@code format}. A
     * "best effort" approach is taken; if {@code str} does not conform to the assumed format, then
     * the behavior of this method is undefined but we make a reasonable effort at converting anyway.
     */
    public final String to(CaseFormat format, String str) {
        checkNotNull(format);
        checkNotNull(str);
        return (format == this) ? str : convert(format, str);
    }

    /**
     * Enum values can override for performance reasons.
     */
    String convert(CaseFormat format, String s) {
        // deal with camel conversion
        StringBuilder out = null;
        int i = 0;
        int j = -1;
        while ((j = wordBoundary.indexIn(s, ++j)) != -1) {
            if (i == 0) {
                // include some extra space for separators
                out = new StringBuilder(s.length() + 4 * wordSeparator.length());
                out.append(format.normalizeFirstWord(s.substring(i, j)));
            } else {
                out.append(format.normalizeWord(s.substring(i, j)));
            }
            out.append(format.wordSeparator);
            i = j + wordSeparator.length();
        }
        return (i == 0)
                ? format.normalizeFirstWord(s)
                : out.append(format.normalizeWord(s.substring(i))).toString();
    }


    abstract String normalizeWord(String word);

    private String normalizeFirstWord(String word) {
        return (this == LOWER_CAMEL) ? Ascii.toLowerCase(word) : normalizeWord(word);
    }

    private static String firstCharOnlyToUpper(String word) {
        return (word.isEmpty())
                ? word
                : Ascii.toUpperCase(word.charAt(0)) + Ascii.toLowerCase(word.substring(1));
    }
}