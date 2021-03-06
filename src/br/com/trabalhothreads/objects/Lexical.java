package br.com.trabalhothreads.objects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical implements Serializable {


	private static final long serialVersionUID = 1L;

	private Lexical() { 	}

	public static boolean validateWord(String word) {
		try {

			Pattern pt = Pattern.compile("[^\\p{L}\\d]");
			Matcher match = pt.matcher(word);
			while (match.find()) {
				String s = match.group();
				word = word.replaceAll("\\" + s, "");
			}

			try {
				Integer.parseInt(word);
				return true;
			} catch (NumberFormatException nfe) {
				Class.forName("org.hsqldb.jdbcDriver");
				Connection c = DriverManager.getConnection("jdbc:hsqldb:file:/base/base", "SA", "");
				PreparedStatement ps = c.prepareStatement("SELECT * FROM PALAVRA_CORRETA WHERE PALAVRA = '"+word+"'");
				if (ps.executeQuery().next()) {					
					return true;
				} else {
					return false;
				}
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
