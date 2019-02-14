package com.metacube.tms.enums;

public enum ResourceType {
  EMPLOYEE("employee", "employee enum");

  private final String value;
  private final String description;

  ResourceType(String value, String description) {
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
