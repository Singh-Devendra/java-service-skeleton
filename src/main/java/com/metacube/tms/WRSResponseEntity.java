package com.metacube.tms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public class WRSResponseEntity<T> extends ResponseEntity<T> {

  private String[] permissions;

  private T data;
  private Map<String, Object> body = new HashMap<String, Object>();

  public WRSResponseEntity(HttpStatus status) {
    super(status);
  }

  public T getBody() {
    return (T) body;
  }

  public void setBody(Map<String, Object> body) {
    this.body = body;
  }

  public WRSResponseEntity(@Nullable T data, HttpStatus status) {
    super(status);
    this.body.put("data", data);
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.body.put("data", data);
  }

  public String[] getPermissions() {
    return permissions;
  }

  public void setPermissions(String[] permissions) {
    this.body.put("permissions", permissions);
  }

  @Override
  public String toString() {
    return "CustomResponseEntity [permissions=" + Arrays.toString(permissions) + ", data=" + data + ", body=" + body
        + "]";
  }



}
