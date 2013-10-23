package org.imogene.lib.sync.server.binary;

import java.util.HashSet;
import java.util.Set;

/**
 * Singleton which permits to register and access to operations 
 * which have to be execute after a binary persistence operation.
 * @author Medes-IMPS
 */
public class BinaryOperationCatcher {

	private static BinaryOperationCatcher instance = new BinaryOperationCatcher();
	
	private Set<BinaryOperation> operations = new HashSet<BinaryOperation>();
	
	public static BinaryOperationCatcher get(){
		return instance;
	}
	
	/**
	 * Count of the registered operations
	 * @return the count
	 */
	public int count(){
		return operations.size();
	}

	/**
	 * register an operation
	 * @param operation the operation to register
	 */
	public void register(BinaryOperation operation){
		operations.add(operation);
	}
	
	/**
	 * Unregister an operation
	 * @param operation the operation to unregister
	 */
	public void unRegister(BinaryOperation operation){
		operations.remove(operation);
	}
	
	public void setOperations(Set<BinaryOperation> pOperations){
		for(BinaryOperation operation : pOperations){
			operations.add(operation);
		}
	}
	
	public Set<BinaryOperation> getOperations(){
		return operations;
	}
	
}
