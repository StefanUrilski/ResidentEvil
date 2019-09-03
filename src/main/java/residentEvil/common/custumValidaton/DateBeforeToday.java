package residentEvil.common.custumValidaton;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = DateBeforeTodayValidator.class)
public @interface DateBeforeToday {
    String message() default "Date should be before today!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}