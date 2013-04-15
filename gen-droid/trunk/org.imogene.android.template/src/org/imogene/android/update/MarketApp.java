package org.imogene.android.update;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Parcel;
import android.os.Parcelable;

public class MarketApp implements Parcelable {

	public static interface Tags {
		// Attributes
		public static final String ATTR_ID = "id";

		// Tags
		public static final String APPLICATION = "application";
		public static final String PACKAGE = "package";
		public static final String LABEL = "label";
		public static final String DESCRIPTION = "description";
		public static final String VERSION_CODE = "versionCode";
		public static final String FILE = "file";
		public static final String SIZE = "size";
	}

	private long mId;
	private String mPackage;
	private String mLabel;
	private String mDescription;
	private String mFile;
	private int mVersionCode;
	private int mSize;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getPackage() {
		return mPackage;
	}

	public void setPackage(String nPackage) {
		mPackage = nPackage;
	}

	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String label) {
		mLabel = label;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public String getFile() {
		return mFile;
	}

	public void setFile(String file) {
		mFile = file;
	}

	public int getVersionCode() {
		return mVersionCode;
	}

	public void setVersionCode(int versionCode) {
		mVersionCode = versionCode;
	}

	public int getSize() {
		return mSize;
	}

	public void setSize(int size) {
		mSize = size;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeInt(mSize);
		dest.writeInt(mVersionCode);
		dest.writeString(mPackage);
		dest.writeString(mLabel);
		dest.writeString(mDescription);
		dest.writeString(mFile);
	}

	public static final Parcelable.Creator<MarketApp> CREATOR = new Creator<MarketApp>() {

		@Override
		public MarketApp[] newArray(int size) {
			return new MarketApp[size];
		}

		@Override
		public MarketApp createFromParcel(Parcel source) {
			MarketApp app = new MarketApp();
			app.setId(source.readLong());
			app.setSize(source.readInt());
			app.setVersionCode(source.readInt());
			app.setPackage(source.readString());
			app.setLabel(source.readString());
			app.setDescription(source.readString());
			app.setFile(source.readString());
			return app;
		}
	};
	
	public static class Parser {
		
		public static MarketApp parse(InputStream is) throws XmlPullParserException, IOException {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(is, null);
			while (parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlPullParser.START_TAG && Tags.APPLICATION.equals(parser.getName())) {
					return parseApplication(parser);
				}
			}
			return null;
		}

		private static MarketApp parseApplication(XmlPullParser parser) throws XmlPullParserException, IOException {
			MarketApp app = new MarketApp();
			app.setId(Long.parseLong(parser.getAttributeValue(null, Tags.ATTR_ID)));
			while (parser.getEventType() != XmlPullParser.END_TAG
					|| !Tags.APPLICATION.equals(parser.getName())) {
				if (parser.next() == XmlPullParser.START_TAG) {
					String name = parser.getName();
					if (Tags.DESCRIPTION.equals(name)) {
						app.setDescription(parser.nextText());
					} else if (Tags.FILE.equals(name)) {
						app.setFile(parser.nextText());
					} else if (Tags.LABEL.equals(name)) {
						app.setLabel(parser.nextText());
					} else if (Tags.PACKAGE.equals(name)) {
						app.setPackage(parser.nextText());
					} else if (Tags.VERSION_CODE.equals(name)) {
						app.setVersionCode(Integer.parseInt(parser.nextText()));
					} else if (Tags.SIZE.equals(name)) {
						app.setSize(Integer.parseInt(parser.nextText()));
					}
				}
			}
			return app;
		}
	}

}
