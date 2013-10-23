package org.imogene.lib.sync;

public class SyncConstants {

	/* system user id */
	public static String SYNC_ID_SYS = "sync-system";

	/* parameters name constants */
	public static String PARAM_CMD = "cmd";

	public static String PARAM_TERMID = "term";

	public static String PARAM_SESSION = "session";

	public static String PARAM_DATA = "data";

	public static String PARAM_TYPE = "sertype";

	public static String PARAM_USER = "user";

	public static String PARAM_PASSWD = "pass";

	/* name of the commands */
	public static String INIT = "init";

	public static String ACK_INIT = "ackinit";

	public static String CLIENT_MODIF = "clmodif";

	public static String ACK_CLIENT_MODIF = "ackmodif";

	public static String REQ_SERVER_MODIF = "reqservmodif";

	public static String SERVER_MODIF = "servermodif";

	public static String ACK_SERVER_MODIF = "ackservmodif";

	public static String SERVER_STATUS_INFO = "statusinfo";

	public static String DIRECT_SEND = "directsend";

	/* error code */
	public static int REQUEST_ERROR = 1;

	public static int SEND_MODIF_ERROR = 2;

	public static int RECEIVE_MODIF_ERROR = 3;

	public static int SESSION_ERROR = 4;

	/* session type */
	public static String XML_TYPE = "xml";

	public static String BYTES_TYPE = "bin";
}
