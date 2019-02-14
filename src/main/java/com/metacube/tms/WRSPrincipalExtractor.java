package com.metacube.tms;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import com.metacube.tms.modal.CurrentUser;

@Component
public class WRSPrincipalExtractor implements PrincipalExtractor {

  @Override
  public Object extractPrincipal(Map<String, Object> map) {

    CurrentUser user = null;
    if (map.get("user_id") != null) {
      user = new CurrentUser();
      user.setUserID(new Long((Integer) map.get("user_id")));
      user.setUserUUID((String) map.get("user_uuid"));
      user.setOrganizationID(new Long((Integer) map.get("organization_id")));
      user.setOrganizationUUID((String) map.get("organization_uuid"));
      user.setEmail((String) map.get("email"));
    }
    return user;
  }

}
