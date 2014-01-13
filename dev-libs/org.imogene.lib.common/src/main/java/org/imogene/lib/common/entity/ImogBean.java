package org.imogene.lib.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface describes an Imogene modeled business entity
 * 
 * @author Medes-IMPS
 */
public interface ImogBean extends Serializable {

	/**
	 * Get the unique identifier for this bean.
	 * 
	 * @return The identifier for this bean
	 */
	public String getId();

	/**
	 * Set the unique identifier for this bean.
	 * 
	 * @param id The identifier of this bean
	 */
	public void setId(String id);

	/**
	 * Get the creation date of the bean.
	 * 
	 * return The creation date
	 */
	public Date getCreated();

	/**
	 * Set the creation date of the bean.
	 * 
	 * @param created The creation date
	 */
	public void setCreated(Date created);

	/**
	 * Get the original creator of this bean.
	 * 
	 * @return The creator
	 */
	public String getCreatedBy();

	/**
	 * Set the original creator of this bean.
	 * 
	 * @param createdBy The creator
	 */
	public void setCreatedBy(String createdBy);

	/**
	 * Get the last modification date of the bean.
	 * 
	 * @return The last modification date
	 */
	public Date getModified();

	/**
	 * Set the creation date of the bean.
	 * 
	 * @param modified The creation date
	 */
	public void setModified(Date modified);

	/**
	 * Get the user which has modified this bean.
	 * 
	 * @return The last modifier
	 */
	public String getModifiedBy();

	/**
	 * Set the user which has modified this bean.
	 * 
	 * @param modifiedBy The last modifier
	 */
	public void setModifiedBy(String modifiedBy);

	/**
	 * Get the terminal which has modified this bean.
	 * 
	 * @return The terminal identifier
	 */
	public String getModifiedFrom();

	/**
	 * Set the terminal which has modified this bean.
	 * 
	 * @param modifiedFrom The terminal identifier
	 */
	public void setModifiedFrom(String modifiedFrom);

	/**
	 * Get the upload date of the bean.
	 * 
	 * @return The last uploaded date
	 */
	public Date getUploadDate();

	/**
	 * Set the upload date of the bean.
	 * 
	 * @param upload The last uploaded date
	 */
	public void setUploadDate(Date upload);

	/**
	 * Set the deleted date.
	 * 
	 * @param deleted The deleted date
	 */
	public void setDeleted(Date deleted);

	/**
	 * Get the deleted date.
	 * 
	 * @return The deleted date
	 */
	public Date getDeleted();

	/**
	 * Get the bean version.
	 * 
	 * @return The version
	 */
	public int getVersion();

	/**
	 * Set the bean version.
	 * 
	 * @param version The version
	 */
	public void setVersion(int version);
}
