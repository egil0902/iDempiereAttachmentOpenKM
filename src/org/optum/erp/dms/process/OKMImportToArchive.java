package org.optum.erp.dms.process;

import org.compiere.model.MArchive;
import org.compiere.model.MStorageProvider;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.optum.erp.dms.utils.DMSConfig;
import org.optum.erp.dms.utils.OptumUtil;

@org.adempiere.base.annotation.Process
public class OKMImportToArchive extends SvrProcess{
	private String fileName;
	private MStorageProvider prov;
	MArchive archive;
	@Override
	protected void prepare() {
		ProcessInfoParameter[] parameters = getParameter();
		for (ProcessInfoParameter para: parameters)
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("AD_Table_ID")) {
				//p_C_DocType_ID = para.getParameterAsInt();
			}
			else if (name.equals("UseEmployeeStartDate")) {
				//p_UseEmployeeStartDate = para.getParameterAsBoolean();
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		
				
		prov=new MStorageProvider(getCtx(), 1000000, get_TrxName());
		DMSConfig config = OptumUtil.getDMSConfig(prov, 291, 200000);
		String uuid=OptumUtil.getExistingUUID("4EVER-LEGAL.pdf", config.getUrl(), config.getRootFolder(), config.getEncoding());
		archive=new MArchive(getCtx(), 0, get_TrxName());
		archive.setAD_Table_ID(291);
		archive.setRecord_ID(200000);
		archive.setByteData(uuid.getBytes());
		archive.setAD_Org_ID(0);
		archive.setName("4EVER-LEGAL.pdf");
		archive.setAD_StorageProvider_ID(prov.get_ID());
		archive.saveEx();
		
		//
		
		//byte[] data = new OptumUtil().getDocument("e621dceb-e5e3-4ddf-a75b-5b8abd47e022",config);
		
		return null;
	}

}
