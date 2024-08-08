package org.optum.erp.dms.component;

import org.adempiere.base.AnnotationBasedProcessFactory;

public class ProcessFactory extends AnnotationBasedProcessFactory{

	@Override
	protected String[] getPackages() {
		return new String[] { "org.optum.erp.dms.process"};
	}

}
