package com.metacube.tms.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import com.metacube.tms.modal.CurrentUser;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
public class AuditorAwareImpl implements AuditorAware<Long> {


	// TODO return the current user when spring security is enabled
    @Override
    public Optional<Long> getCurrentAuditor() {
    	
    	CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	System.out.println(currentUser);
    	currentUser.getUserID();
        return Optional.of(currentUser.getUserID());
        // Can use Spring Security to return currently logged in user
        // return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
    }
}
