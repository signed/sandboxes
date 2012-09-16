package some.other;

import static some.other.ImportingClass.Constants.ReallyImportantConstant;

public class ImportingClass {

    public static void main(String[] args) {
        System.out.println(ReallyImportantConstant);
        System.out.println(Constants.ReallyImportantConstant);
    }

    public static class Constants {
        public static String ReallyImportantConstant = "important";
    }

    public static class ImportantConstants {
    }
}
