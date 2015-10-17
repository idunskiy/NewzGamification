package mobapply.newzgamification.network;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mobapply.newzgamification.model.NewsItem;
import mobapply.newzgamification.model.NewsItemBuilder;

public final class NetworkHandler {
	public static final String UNIAN_URL = "http://rss.unian.net/site/news_ukr.rss";
	private static final String TAG = "NetworkHandler";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

	private NetworkHandler(){}

	public static void getNewsList(final Response.Listener<List<NewsItem>> listener, Context activity) {
		Response.Listener<String> responseListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "onResponse() called with " + "response = [" + response + "]");
				try {
					listener.onResponse(parse(new ByteArrayInputStream(response.getBytes("UTF8"))));
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "", e);
				}
			}
		};
		StringRequest request = new StringRequest(UNIAN_URL, responseListener ,null);
		VolleySingleton.getInstance(activity).addToRequestQueue(request);
	}

	private static List<NewsItem> parse(InputStream in) {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} catch (XmlPullParserException | IOException | ParseException e ) {
			Log.e(TAG, "", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				Log.e(TAG, "", e);
			}
		}
		return null;
	}
	private static List<NewsItem> readFeed(XmlPullParser parser) throws XmlPullParserException,
			IOException, ParseException {
		List<NewsItem> entries = new ArrayList<>();

		while (!(parser.next() == XmlPullParser.START_TAG
				&& parser.getName().equals("item"))) {
		}
		while (parser.getEventType() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			Log.d(TAG, "readFeed1 " + parser.getName() + " event=" + parser.getEventType());
			// Starts by looking for the entry tag
			if (parser.getName().equals("item")) {
				Log.d(TAG, "readFeed2 item");
				entries.add(readEntry(parser));
				Log.d(TAG, "readFeed3 " + parser.getName() + " event=" + parser.getEventType());
			} else {
				skip(parser);
			}
			parser.nextTag();
			Log.d(TAG, "readFeed4 " + parser.getName() + " event=" + parser.getEventType());
		}
		return entries;
	}
	private static NewsItem readEntry(XmlPullParser parser) throws ParseException, IOException,
			XmlPullParserException {
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		NewsItemBuilder builder = new NewsItemBuilder();
		//<item>
		// <guid isPermaLink="false">1155177</guid>
		parser.nextTag();
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		parser.nextText();
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		parser.nextTag();
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		// <title>Завтра в Донецькій області почнеться відведення озброєння калібром менше 100 мм</title>
		builder.setSummary(parser.nextText());
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		parser.nextTag();
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		// <link>http://www.unian.ua/war/1155177-zavtra-v-donetskiy ... .html</link>
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		builder.setTextFull(parser.nextText());
		parser.nextTag();
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		// <category>Війна</category>
		parser.nextText();
		parser.nextTag();
		Log.d(TAG, "readEntry name=" + parser.getName() + " event=" + parser.getEventType());
		// <enclosure url="http://images.unian.net/....jpg"  length="237400" type="image/jpeg" />
		builder.setImage(parser.getAttributeValue(0));
		parser.nextTag();
		parser.nextTag();
		// <description>Завтра у Донецькій області має розпочатися ....</description>
		parser.nextText();
		parser.nextTag();
		// <pubDate>Sat, 17 Oct 2015 13:45:12 +0300</pubDate>
		builder.setDate(SIMPLE_DATE_FORMAT.parse(parser.nextText()));
		parser.nextTag();
		// </item>
		return builder.createNewsItem();
	}

	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}
}
