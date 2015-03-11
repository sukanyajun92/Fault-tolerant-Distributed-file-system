
public class LogLine implements Comparable<LogLine> {
    String messageId;
	String fileName;
	String operation;
	Long TimeInMilli;
	
	
	public LogLine(String messageId,String fileName, String operation, Long timeInMilli) {
		super();
		this.messageId=messageId;
		this.fileName = fileName;
		this.operation = operation;
		TimeInMilli = timeInMilli;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Long getTimeInMilli() {
		return TimeInMilli;
	}
	public void setTimeInMilli(Long timeInMilli) {
		TimeInMilli = timeInMilli;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	  @Override
	  public int compareTo(LogLine o) {
	    return Long.compare(TimeInMilli, o.TimeInMilli);
	  }
	  
	  @Override
	  public String toString() {
	    return messageId + " " + fileName+" "+operation+" "+TimeInMilli;
	  }
}
