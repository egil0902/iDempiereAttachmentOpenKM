package org.optum.erp.dms.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import org.compiere.model.IArchiveStore;
import org.compiere.model.MArchive;
import org.compiere.model.MStorageProvider;
import org.compiere.util.CLogger;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.optum.erp.dms.utils.DMSConfig;
import org.optum.erp.dms.utils.OptumUtil;

public class ArchiveOpenKm implements IArchiveStore{

	private final CLogger log = CLogger.getCLogger(getClass());
	@Override
	public byte[] loadLOBData(MArchive archive, MStorageProvider prov) {
		DMSConfig config = OptumUtil.getDMSConfig(prov, archive.getAD_Table_ID(), archive.getRecord_ID());
		String uuid = new String(archive.getByteData());
		return  OptumUtil.getDocument(uuid, config);

	}

	@Override
	public void save(MArchive archive, MStorageProvider prov, byte[] inflatedData) {
		try {
			DMSConfig config = OptumUtil.getDMSConfig(prov, archive.getAD_Table_ID(), archive.getRecord_ID());

			File file = generateFile(archive, inflatedData);

			String dataTitle = file.getName();
			byte[] fileData = new byte[(int)file.length()];
			FileInputStream in = new FileInputStream(file);
			in.read(fileData);
			in.close();
			byte[] data = new OptumUtil().getDMSUUID(fileData, config, dataTitle);

			if (log.isLoggable(Level.FINE))
				log.fine(archive.getName() + " - "  );
			archive.setByteData(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteArchive(MArchive archive, MStorageProvider prov) {
		DMSConfig config = OptumUtil.getDMSConfig(prov, archive.getAD_Table_ID(), archive.getRecord_ID());
		String uuid = new String(archive.getByteData());
		return OptumUtil.isDocsDeleteByUUID(uuid, config);
		
	}

	@Override
	public boolean isPendingFlush() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void flush(MArchive archive, MStorageProvider prov) {
		// TODO Auto-generated method stub

	}
	public File generateFile(MArchive archive, byte[] inflatedData) throws Exception
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
		String archiveName = archive.getName();
		int recordID = archive.getRecord_ID();
		if (Util.isEmpty(archiveName))
		{
			archiveName = "";
			if (recordID > 0)
				archiveName += recordID;
		}
		else
		{
			archiveName = archiveName.trim();
			archiveName = archiveName.replaceAll(" ", "");
			//archiveName = archiveName.replace(DMSConstant.FILE_SEPARATOR, "_");
			if (recordID > 0)
				archiveName = recordID + " " + archiveName;
		}

		String fileName = archiveName + "_" + timeStamp + ".pdf";
		File file = new File(fileName);
		FileOutputStream fileOuputStream = null;
		try
		{
			fileOuputStream = new FileOutputStream(file);
			fileOuputStream.write(inflatedData);
		}
		finally
		{
			if (file != null)
			{
				fileOuputStream.flush();
				fileOuputStream.close();
			}
		}
		return file;
	} // generateFile

}
