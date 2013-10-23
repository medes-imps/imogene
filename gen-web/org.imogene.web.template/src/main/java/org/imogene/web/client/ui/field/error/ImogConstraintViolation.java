package org.imogene.web.client.ui.field.error;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import com.google.web.bindery.requestfactory.shared.BaseProxy;

public class ImogConstraintViolation implements ConstraintViolation<BaseProxy> {

	private String message;
	private String messageTemplate;
	private BaseProxy rootBean;
	private Class<? extends BaseProxy> rootBeanClass;
	private BaseProxy leafBean;
	private Path path;

	public ImogConstraintViolation() {

	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessageTemplate() {
		return messageTemplate;
	}
	
	public void getMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	@Override
	public BaseProxy getRootBean() {
		return rootBean;
	}
	
	public void setRootBean(BaseProxy rootBean) {
		this.rootBean = rootBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<BaseProxy> getRootBeanClass() {
		return (Class<BaseProxy>) rootBeanClass;
	}

	@Override
	public Object getLeafBean() {
		return leafBean;
	}
	
	public void setLeafBean(BaseProxy leafBean) {
		this.leafBean = leafBean;
	}

	@Override
	public Path getPropertyPath() {
		return path;
	}
	
	public void setPropertyPath(Path path) {
		this.path = path;
	}

	@Override
	public Object getInvalidValue() {
		return null;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return null;
	}

}