package com.github.signed.jee.mxbean;

public class FullyDocumentedMxBean implements FirstMxBean{
    @Override
    public void operationWithoutParameters() {
        System.out.println("Operation without parameters");
    }
}
