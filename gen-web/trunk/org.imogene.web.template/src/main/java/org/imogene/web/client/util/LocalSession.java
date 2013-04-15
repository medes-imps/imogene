package org.imogene.web.client.util;

import java.util.HashSet;
import java.util.Set;

import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

public class LocalSession {
	
	
	private static LocalSession instance = new LocalSession();
	
	
	private ImogActorProxy currentUser;	
	private Set<Integer> edited = new HashSet<Integer>();	
	private ImogJunctionProxy searchCriterions = null;
	private String filteringMessage = null;
	
	
	public static LocalSession get(){
		return instance;
	}
	
	
	public void setCurrentUser(ImogActorProxy actor){
		currentUser = actor;
	}
	
	public ImogActorProxy getCurrentUser(){
		return currentUser;
	}

	public void addToEdited(Integer code){
		edited.add(code);
	}
	
	public void removeFromEdited(Integer code){
		edited.remove(code);
	}
	
	public boolean isEditing(){
		return !edited.isEmpty();
	}
	
	public void clearEdited(){
		edited.clear();
	}

	public ImogJunctionProxy getSearchCriterions() {
		return searchCriterions;
	}
	
	public void setSearchCriterions(ImogJunctionProxy criterions, String message) {
		this.searchCriterions = criterions;
		this.filteringMessage = message;
	}

	public String getFilteringMessage() {
		return filteringMessage;
	}
	
	
	
}
