package some.other;

import apackage.TimeConstants;
import common.Constants;

import static apackage.TimeConstants.Constant;
import static common.Constants.ConstantFromSameModule;

public class ImportingClass {
    public static void main(String[] args) {
        System.out.println(Constant);
        System.out.println(TimeConstants.Constant);

        System.out.println(ConstantFromSameModule);
        System.out.println(Constants.ConstantFromSameModule);
    }
}
