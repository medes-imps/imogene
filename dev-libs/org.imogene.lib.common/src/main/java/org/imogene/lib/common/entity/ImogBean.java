package org.imogene.lib.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface describes an Imogene modeled business entity
 * @author Medes-IMPS
 */
public interface ImogBean extends Serializable {

	/** Get the unique ID for this bean */
	public String getId();

	/** Set the unique ID for this bean */
	public void setId(String id);

	/** Get the creation date of the bean. */
	public Date getCreated();

	/** Set the creation date of the bean. */
	public void setCreated(Date created);

	/** Get the original creator of this bean. */
	public String getCreatedBy();

	/** Set the original creator of this bean. */
	public void setCreatedBy(String createdBy);

	/** Get the last modification date of the bean. */
	public Date getModified();

	/** Set the creation date of the bean. */
	public void setModified(Date modified);

	/** Get the user which has modified this bean. */
	public String getModifiedBy();

	/** Set the user which has modified this bean. */
	public void setModifiedBy(String modifiedBy);

	/** Get the terminal which has modified this bean. */
	public String getModifiedFrom();

	/** Set the terminal which has modified this bean. */
	public void setModifiedFrom(String modifiedFrom);

	/** Get the upload date of the bean. */
	public Date getUploadDate();

	/** Set the upload date of the bean. */
	public void setUploadDate(Date upload);

	/** Get the bean version */
	public int getVersion();

	/** Set the bean version */
	public void setVersion(int version);
}
