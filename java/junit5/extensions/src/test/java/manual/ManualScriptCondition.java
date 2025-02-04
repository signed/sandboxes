package manual;

import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;

import java.lang.reflect.AnnotatedElement;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

class ManualScriptCondition implements ExecutionCondition {

    private static final ConditionEvaluationResult ENABLED = ConditionEvaluationResult.enabled(
            "@Disabled is not present");

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        AnnotatedElement element = context.getElement().orElse(null);
        return findAnnotation(element, ManualScript.class)
                .map(annotation -> toResult(element, annotation))
                .orElse(ENABLED);
    }

    private ConditionEvaluationResult toResult(AnnotatedElement element, ManualScript annotation) {
        ScriptStatus value = annotation.value();
        if (ScriptStatus.Primed.equals(value)) {
            return ConditionEvaluationResult.enabled("ManualScript is primed");
        }
        return ConditionEvaluationResult.disabled("ManualScript is not primed");
    }

}
