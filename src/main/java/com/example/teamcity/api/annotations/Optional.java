package com.example.teamcity.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * поля с этой аннотацией не будут генерироваться рандомными или параметризированными значениями
 * необходимо указывать значение вручную(сначала объект, а потом через сеттер проставлять значения)
 */
public @interface Optional {


}
