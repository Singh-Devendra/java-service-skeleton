package com.metacube.tms.accesscontrol;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.metacube.tms.enums.ActionType;
import com.metacube.tms.enums.ResourceType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessControl {

  String subjectID() default "";

  String resourceID() default "";

  ResourceType resourceType() default ResourceType.EMPLOYEE;

  ActionType actionID() default ActionType.READ;
  
}