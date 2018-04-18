package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * Renomer le nom d'un colone. il est preferable que le nom d'attribute == nomColone
     * @return String
     */
    String name();
}
