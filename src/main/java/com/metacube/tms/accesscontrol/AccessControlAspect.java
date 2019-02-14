package com.metacube.tms.accesscontrol;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.metacube.tms.WRSResponseEntity;
import com.metacube.tms.config.ServiceConfig;
import com.metacube.tms.modal.CurrentUser;

@Aspect
@Component
public class AccessControlAspect {

  @Autowired
  ServiceConfig config;

  @Pointcut("@annotation(com.metacube.tms.accesscontrol.AccessControl)")
  private void AccessControl() {

  }

  @Around("AccessControl()")
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {

    CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    AccessControl accessControl = method.getAnnotation(AccessControl.class);

//    NOTE: Map was created to support multiple key parsing using WRSAnnotationExpressionParser.
    Map<String, String> requestArgs = new HashMap<String, String>();
    requestArgs.put("subjectID", currentUser.getUserID().toString());
    requestArgs.put("actionID", accessControl.actionID().toString());
    requestArgs.put("resourceType", accessControl.resourceType().toString());

    String resourceID = WRSAnnotationExpressionParser.getAuthParams(signature.getParameterNames(), pjp.getArgs(),
        accessControl.resourceID());
    requestArgs.put("resourceID", resourceID);

    // HttpServletRequest req = getRequest();
    AuthorizationService authService = new AuthorizationService(config);
    JSONObject authResponse = authService.authorize(requestArgs.get("subjectID"), requestArgs.get("actionID"),
        requestArgs.get("resourceID"), requestArgs.get("resourceType"));

    ResponseEntity obj = (ResponseEntity) pjp.proceed();
    WRSResponseEntity response = new WRSResponseEntity(obj.getStatusCode());
    response.setData(obj.getBody());
    authResponse.getJSONArray("Response");

    JSONArray adviceAttributes = authResponse.getJSONArray("Response").getJSONObject(0).getJSONArray("AssociatedAdvice")
        .getJSONObject(0).getJSONArray("AttributeAssignments");
    for (int i = 0, size = adviceAttributes.length(); i < size; i++) {
      JSONObject objectInArray = adviceAttributes.getJSONObject(i);
      String attribute = objectInArray.get("AttributeId").toString();
      if (attribute.equals("permissions")) {
        Object value = objectInArray.get("Value");
        response.setPermissions(value.toString().split(","));
      }
    }
    return response;
  }

  private HttpServletRequest getRequest() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return requestAttributes.getRequest();
  }

}