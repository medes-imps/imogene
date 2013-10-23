package org.imogene.android.maps.util;

import java.util.ArrayList;

import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class TileSourceUtil {
	
	public static CharSequence[] readableTileSources(ResourceProxy proxy) {
		final ArrayList<ITileSource> list = TileSourceFactory.getTileSources();
		final int size = list.size();
		CharSequence[] result = new CharSequence[size];
		for (int i = 0; i < size; i++) {
			result[i] = list.get(i).localizedName(proxy);
		}
		return result;
	}

}
