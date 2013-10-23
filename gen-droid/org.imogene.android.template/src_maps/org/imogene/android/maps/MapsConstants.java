package org.imogene.android.maps;

import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;


public final class MapsConstants {
	
	public static final double DEFAULT_LATIUDE = 43.589390;
	public static final double DEFAULT_LONGITUDE = 1.445196;
	public static final double DEFAULT_NORTH = 43.589390;
	public static final double DEFAULT_WEST = 1.445196;
	public static final double DEFAULT_SOUTH = 43.586204;
	public static final double DEFAULT_EAST = 1.452513;
	
	/**
	 * A radius WGS84 major axis
	 */
    public static final double EARTH_MAJOR_AXIS = 6378137.0;
    
    /**
     * WGS84 semi-major axis
     */
    public static final double EARTH_SEMI_MAJOR_AXIS = 6356752.3142;
	
	/**
	 * latitude extra (must be a double value) to use when trying to visualize
	 * coordinates on a map, or trying to navigate to it
	 * This must be used with {@link MapsConstants.MIME_GPS}
	 */
	public static final String EXTRA_LATITUDE = "latitude";

	/**
	 * longitude extra (must be a double value) to use when trying to visualize
	 * coordinates on a map, or trying to navigate to it
	 * This must be used with {@link MapsConstants.MIME_GPS}
	 */
	public static final String EXTRA_LONGITUDE = "longitude";
	
	/**
	 * location parcelable extra that is returned when asking for a location
	 * using {@link MapsConstants.MIME_GPS}
	 */
	public static final String EXTRA_LOCATION = "location";
	
	/**
	 * Tile source (name) parcelable to precise which tile source to use when
	 * putting an area into the cache
	 * @see ITileSource#name()
	 * @see TileSourceFactory#getTileSource(String)
	 */
	public static final String EXTRA_TILE_SOURCE = "tileSource";
	
	/**
	 * North latitute (double) parcelable of the bounding box to put into the cache 
	 */
	public static final String EXTRA_LAT_NORTH = "latitudeNorth";
	
	/**
	 * South latitute (double) parcelable of the bounding box to put into the cache 
	 */
	public static final String EXTRA_LAT_SOUTH = "latitudeSouth";
	
	/**
	 * East longitude (double) parcelable of the bounding box to put into the cache 
	 */
	public static final String EXTRA_LON_EAST = "longitudeEast";
	
	/**
	 * West longitude (double) parcelable of the bounding box to put into the cache 
	 */
	public static final String EXTRA_LON_WEST = "longitudeWest";
	
	/**
	 * Maximum zoom level to take into account to pre-cache the geographical area
	 */
	public static final String EXTRA_ZOOM_MAX = "zoomMax";
	
	/**
	 * Minimum zoom level to take into account to pre-cache the geographical area
	 */
	public static final String EXTRA_ZOOM_MIN = "zoomMin";
	
	/**
	 * Set to true to activate the visualization of the pre-cached areas
	 */
	public static final String EXTRA_SHOW_PRECACHE = "showPreCache";
	
	/**
	 * Set to true to refresh the actual pre-cached areas
	 */
	public static final String EXTRA_REFRESH_PRECACHE = "refreshPreCache";
	
	/**
	 * Mime type for gps coordinates.
	 */
	public static final String MIME_GPS = "vnd.imogene.%realProjectName%.maps/coordinates";
	
	/**
	 * Filter the available intents to ask for coordinates only using the sensor
	 */
	public static final String CATEGORY_GPS = "org.imogene.map.%realProjectName%.category.GPS";

	/**
	 * Filter the available intents to ask for coordinates from the best sensor available
	 */
	public static final String CATEGORY_BEST = "org.imogene.map.%realProjectName%.category.BEST";

	/**
	 * Filter the available intents to ask for coordinates from the network
	 */
	public static final String CATEGORY_NETWORK = "org.imogene.map.%realProjectName%.category.NETWORK";

	/**
	 * Filter the available intents to ask for coordinates picking a point on a map
	 */
	public static final String CATEGORY_MAP = "org.imogene.map.%realProjectName%.category.MAP";
	
}
