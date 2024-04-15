package ext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestProperties {
    int testNum() default 0;

    int itemCount() default 1;
}
