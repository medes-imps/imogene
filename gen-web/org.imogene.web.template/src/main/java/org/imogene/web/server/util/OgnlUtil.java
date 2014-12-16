package org.imogene.web.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ognl.Ognl;
import ognl.OgnlException;

public class OgnlUtil {

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * It parses the notification template and evaluates the enclosed OGNL expressions (ie: {creator} or {entity.property}).
	 * @param unformated the template message
	 * @param root the root entity
	 * @return formated message
	 */
	public static String parseForOgnlExpression(String unformated, Object root) {

		StringReader reader = new StringReader(unformated);
		StringBuilder parsedString = new StringBuilder();
		StringBuilder token = new StringBuilder();

		boolean inToken = false;
		int c = -1;

		try {
			while ((c = reader.read()) != -1) {
				if (inToken) {
					token.append((char) c);
					if (c == '}') {
						String parsed = evaluate(token.toString(), root);
						parsedString.append(parsed);
						token = new StringBuilder();
						inToken = false;
					}

				} else {
					if (c == '{') {
						token.append((char) c);
						inToken = true;
					} else {
						parsedString.append((char) c);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parsedString.toString();
	}

	/**
	 * Build text from a template file
	 * @param templateFile the template file
	 * @param root object used by OGNL
	 * @return computed text
	 */
	public static String readTemplate(String templateFile, Object root) {
		InputStream is = root.getClass().getResourceAsStream(templateFile);
		Scanner sc = new Scanner(is);
		sc.useDelimiter("\\A");
		String message = sc.next();
		sc.close();
		if (root != null) {
			message = OgnlUtil.parseForOgnlExpression(message, root);
		}
		return message;
	}

	/**
	 * Evaluate the token as a OGNL expression, using the root object and return the result.
	 * @param token The OGNL expression
	 * @param root The object to use
	 * @return the result of the expression, or the token if it is not a OGNL expression.
	 */
	private static String evaluate(String token, Object root) {
		try {
			Object o = Ognl.getValue(token, root);
			if (o instanceof List && ((List<?>) o).size() > 0) {
				Object res = ((List<?>) o).get(0);
				if (res instanceof Date) {
					return new SimpleDateFormat(DATE_FORMAT).format(res);
				} else {
					return res.toString();
				}
			}
		} catch (OgnlException oe) {
			throw new RuntimeException(oe.getMessage());
		}
		return token;
	}
}
