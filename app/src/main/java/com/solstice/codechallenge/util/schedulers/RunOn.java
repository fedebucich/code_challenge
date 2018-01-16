package com.solstice.codechallenge.util.schedulers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by fbucich on 1/14/18.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RunOn {
    SchedulerType value() default SchedulerType.IO;
}
