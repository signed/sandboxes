package some.other;

import static some.other.ImportingClass.Constants.ConstantFromSameModule;

public class ImportingClass {

    public static void main(String[] args) {
        System.out.println(ConstantFromSameModule);
        System.out.println(Constants.ConstantFromSameModule);
    }

    public static class Constants {
        public static String ConstantFromSameModule = "important";
    }

    public static class ImportantConstants {
    }
}
