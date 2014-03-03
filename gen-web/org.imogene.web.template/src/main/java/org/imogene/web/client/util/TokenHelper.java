package org.imogene.web.client.util;

/**
 * Helper to manage GWT tokens
 * @author Medes-IMPS
 */
public class TokenHelper {

	public static String TK_LIST = "list";
	public static String TK_VIEW = "view";
	public static String TK_NEW = "new";
	public static String TK_XLS = "xls";
	
	public static String TK_CLASSIC = "classic";

	public static EntityToken getToken(String token) {
		String[] params = token.split("/");
		if (params.length == 0)
			return null;
		else {
			if ((params[0].equals("view") || params[0].equals("edit") || params[0].equals("list")) && params.length == 3) {
				return new EntityToken(params[0], params[1], params[2]);

			} else if ((params[0].equals("new") || params[0].equals("list") || params[0].equals("xls")) && params.length == 2) {
				return new EntityToken(params[0], params[1], "");

			} else if (params[0].equals("specific") && params.length == 4) {
				return new EntityToken(params[1], params[2], params[3]);

			} else if (params[0].equals("specific") && params.length == 3) {
				return new EntityToken(params[2], params[2], "");

			} else if (params[0].equals(TK_CLASSIC)) {
				return new EntityToken(TK_CLASSIC, TK_CLASSIC, "");

			} else {
				return null;
			}
		}
	}

	/**
	 * @author Medes-IMPS
	 */
	public static interface ImogToken {
		public String getAction();
	}

	/**
	 * @author Medes-IMPS
	 */
	public static class EntityToken implements ImogToken {

		private String action;

		private String type;

		private String id;

		public EntityToken(String pAction, String pType, String pId) {
			action = pAction;
			id = pId;
			type = pType;
		}

		public String getAction() {
			return action;
		}

		public String getId() {
			return id;
		}

		public String getType() {
			return type;
		}
	}
}
