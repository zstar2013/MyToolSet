package netcom.android.downloader;

public class LoadInfo {
	
	public int fileSize;
	public int complete;
	private String urlString;
	public LoadInfo(int fileSize,int complete,String urlString){
		this.fileSize=fileSize;
		this.complete=complete;
		this.urlString=urlString;
	}
	
	public LoadInfo(){
		
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete
		              + ", urlstring=" + urlString + "]";

	}

}
