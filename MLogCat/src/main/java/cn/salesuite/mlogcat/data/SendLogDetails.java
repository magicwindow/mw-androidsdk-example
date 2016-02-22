package cn.salesuite.mlogcat.data;

import java.io.File;

public class SendLogDetails {
	
	public String subject;
	public String body;
	public File attachment;
	public SendLogDetails.AttachmentType attachmentType;

	public static enum AttachmentType {
		None ("text/plain"),
		Zip ("application/zip"),
		Text ("application/*");
		
		private String mimeType;
		
		private AttachmentType(String mimeType) {
			this.mimeType = mimeType;
		}
		
		public String getMimeType() {
			return this.mimeType;
		}
	}

}
