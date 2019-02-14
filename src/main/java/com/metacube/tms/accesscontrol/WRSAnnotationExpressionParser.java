package com.metacube.tms.accesscontrol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class WRSAnnotationExpressionParser {
  public static String getAuthParams(String[] parameterNames, Object[] args, String key) {
    ExpressionParser parser = new SpelExpressionParser();
    StandardEvaluationContext context = new StandardEvaluationContext();
    Map<String, Object> responsePair = new HashMap<String, Object>();

//    NOTE: To extract multiple keys at once.
//    for (int i = 0; i < parameterNames.length; i++) {
//      System.out.println(parameterNames[i]+args[i]);
//      context.setVariable(parameterNames[i], args[i]);
//    }
//
//    Iterator<Map.Entry<String, String>> iterator = accessControlMap.entrySet().iterator();
//    while (iterator.hasNext()) {
//      Map.Entry<String, String> entry = iterator.next();
//      System.out.println(entry.getKey()+entry.getValue());
//      try {
//        responsePair.put(entry.getKey(), parser.parseExpression(entry.getValue()).getValue(context, Object.class));
//      } catch (Exception e) {
//        responsePair.put(entry.getKey(), entry.getValue());
//        System.out.println("Please handle ME !");
//      }
//    }
//    
//    return responsePair;

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }
    return (String) parser.parseExpression(key).getValue(context, Object.class);
  }
}
