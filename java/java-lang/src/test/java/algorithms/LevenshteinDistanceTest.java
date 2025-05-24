package algorithms;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LevenshteinDistanceTest {

    @Test
    void distanceOfTwoEqualStrings() {
        assertThat(levenshteinDistance("", "")).isEqualTo(0);
    }

    @Test
    void name() {
        assertThat(levenshteinDistance("", "abc")).isEqualTo(3);
        assertThat(levenshteinDistance("abc", "")).isEqualTo(3);
    }

    @Test
    void replaceCostsOne() {
        assertThat(levenshteinDistance("a", "b")).isEqualTo(1);
    }

    @Test
    void longerExample() {
        assertThat(levenshteinDistance("Tier", "Tor")).isEqualTo(2);
        assertThat(levenshteinDistance("sitting", "kitten")).isEqualTo(3);
        assertThat(levenshteinDistance("Sunday", "Saturday")).isEqualTo(3);
    }

    private final int PriceForKeepAsIs = 0;
    private final int PriceForInsert = 1;
    private final int PriceForRemove = 1;
    private final int PriceForReplace = 1;

    private int levenshteinDistance(String one, String two) {
        int[][] matrix = new int[one.length() + 1][two.length() + 1];

        for (int i = 0; i <= one.length(); i = i + PriceForInsert) {
            matrix[i][0] = i;
        }

        for (int j = 0; j <= two.length(); j = j + PriceForInsert) {
            matrix[0][j] = j;
        }

        char[] oneArray = ("ε" + one).toCharArray();
        char[] twoArray = ("ε" + two).toCharArray();

        for (int i = 1; i <= one.length(); ++i) {
            for (int j = 1; j <= two.length(); ++j) {
                List<Integer> candidates = new ArrayList<>(3);
                int i1 = matrix[i - 1][j - 1];
                if (oneArray[i] == twoArray[j]) {
                    candidates.add(i1 + PriceForKeepAsIs);
                } else {
                    candidates.add(i1 + PriceForReplace);
                }

                candidates.add(matrix[i][j - 1] + PriceForInsert);
                candidates.add(matrix[i - 1][j] + PriceForRemove);
                matrix[i][j] = candidates.stream().min(Comparator.naturalOrder()).get();
            }
        }


        StringBuilder matrixString = new StringBuilder();

        StringBuilder headRow = new StringBuilder();
        headRow.append("  ");
        for (int i = 0; i < twoArray.length; i++) {
            headRow.append(twoArray[i]).append(" ");
        }


        matrixString.append(headRow.toString()).append("\n");

        for (int i = 0; i <= one.length(); ++i) {
            StringBuilder rowBuilder = new StringBuilder();
            rowBuilder.append(oneArray[i]).append(" ");
            for (int j = 0; j <= two.length(); ++j) {
                rowBuilder.append(matrix[i][j] + " ");
            }
            matrixString.append(rowBuilder.toString() + "\n");
        }

        System.out.println(matrixString.toString());

        return matrix[one.length()][two.length()];
    }
}
