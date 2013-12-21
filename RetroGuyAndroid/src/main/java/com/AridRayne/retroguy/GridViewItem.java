package com.AridRayne.retroguy;

	public class GridViewItem {
		private String fileName;
		private String title;
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public GridViewItem(String fileName, String title) {
			this.fileName = fileName;
			this.title = title;
		}
	}
