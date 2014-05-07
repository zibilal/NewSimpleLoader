package com.zibilal.consumeapi.lib.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bmuhamm on 5/7/14.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ColumnCache {
    String columName();
}
