package com.morrice.ResourceAccount.foundation.model;

import java.io.Serializable;
import java.sql.Timestamp;

public interface IModel extends Serializable {

	public Integer getId();

	public void setId(Integer id);
	
	public Timestamp getCreateDateTime();
	
	public void setCreateDateTime(Timestamp createDateTime);

	public Timestamp getUpdateDateTime();

	public void setUpdateDateTime(Timestamp updateDateTime);
	
}
