package com.metacube.tms.enums;

public enum ActionType {
  
  READ("read", "Read Action"),
  WRITE("write", "Read Action"),
  EDIT("edit", "Read Action"),
  DELETE("delete", "Read Action");
  
  private final String value;
  private final String description;

  ActionType(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String value() {
    return this.value;
  }

  public String getDescription() {
    return this.description;
  }

  /**
   * Return a string representation of this status code.
   */
  @Override
  public String toString() {
    return this.value;
  }
}

