package some.other;

import apackage.TimeConstants;

import static apackage.TimeConstants.Constant;

public class ImportingClass {
    public static void main(String[] args) {
        System.out.println(Constant);
        System.out.println(TimeConstants.Constant);
    }
}
