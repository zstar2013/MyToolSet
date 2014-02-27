package netcom.android.downloader;

public class DownLoadInfo {
	private int threadId;// ������id
	private int startPos;// ��ʼ��
	private int endPos;// ������
	private int completeSize;// ��ɶ�
	private String url;// �����������ʶ

	public DownLoadInfo(int threadId, int startPos, int endPos,
			int completeSize, String url) {
		this.threadId = threadId;
		this.startPos = startPos;
		this.endPos = endPos;
		this.completeSize = completeSize;
		this.url = url;
	}

	public DownLoadInfo() {

	}

	

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}

	public int getCompleteSize() {
		return completeSize;
	}

	public void setCompleteSize(int completeSize) {
		this.completeSize = completeSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "DownloadInfo [threadId=" + threadId + ", startPos="
				+ startPos + ", endPos=" + endPos + ", compeleteSize="
				+ completeSize + "]";

	}

}
