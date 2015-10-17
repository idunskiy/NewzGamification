package mobapply.newzgamification.model;

import java.util.Date;

public class NewsItemBuilder {
	private Date date;
	private String summary;
	private String textFull;
	private String image;

	public NewsItemBuilder setDate(Date date) {
		this.date = date;
		return this;
	}

	public NewsItemBuilder setSummary(String summary) {
		this.summary = summary;
		return this;
	}

	public NewsItemBuilder setTextFull(String textFull) {
		this.textFull = textFull;
		return this;
	}

	public NewsItemBuilder setImage(String image) {
		this.image = image;
		return this;
	}

	public NewsItem createNewsItem() {
		return new NewsItem(date, summary, textFull, image);
	}
}