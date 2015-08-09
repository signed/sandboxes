package com.github.signed.sandboxes.spring.beanvalidation;

import java.util.ArrayList;
import java.util.List;

public class ErrorReportTO {
    public String incident;
    public final List<ErrorTO> errors = new ArrayList<>();
}
