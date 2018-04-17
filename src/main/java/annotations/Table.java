package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * Nom de la table dans la base. default = NomClasse
     ** */
    String name();

    /**
     * Nom de la sequence dans la base asocie à la table. default = null
     ** */
    String sequence() default "";

    /**
     * Sequence de 4 caracteres former L'id de l'objet table.
     * */
    String predicas() default "";
}
