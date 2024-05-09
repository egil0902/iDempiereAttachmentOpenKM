package org.optum.erp.search.model;
public class QueryResult
{
    private Document node;
    private boolean attachment;
    private String excerpt;
     
    private int score;

	public Document getNode() {
		return node;
	}

	public void setNode(Document node) {
		this.node = node;
	}

	public boolean isAttachment() {
		return attachment;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

   
    
}