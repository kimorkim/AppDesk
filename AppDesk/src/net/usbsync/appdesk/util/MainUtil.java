package net.usbsync.appdesk.util;

import java.lang.ref.Reference;

import net.usbsync.appdesk.MainDesk;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

@SuppressLint("NewApi")
public class MainUtil {
	public final static String DEBUG_TAG = "MY_DEBUG";
	public final static int MAKE_APP_LISTS = 0x000001;

	private static final char HANGUL_BEGIN_UNICODE = 44032; // °¡
	private static final char HANGUL_LAST_UNICODE = 55203; // ÆR
	private static final char HANGUL_BASE_UNIT = 588;// °¢ÀÚÀ½ ¸¶´Ù °¡Áö´Â ±ÛÀÚ¼ö
	private static final char[] INITIAL_SOUND = { '¤¡', '¤¢', '¤¤', '¤§', '¤¨', '¤©',
			'¤±', '¤²', '¤³', '¤µ', '¤¶', '¤·', '¤¸', '¤¹', '¤º', '¤»', '¤¼', '¤½', '¤¾' };
	private static final char[] CHECK_SOUND = { '¤¡', '¤¢', '¤¤', '¤§', '¤¨', '¤©',
			'¤±', '¤²', '¤³', '¤µ', '¤¶', '¤·', '¤¸', '¤¹', '¤º', '¤»', '¤¼', '¤½', '¤¾',
			'ÆR' };

	public static class Util {

	}

	public static Drawable loadIcon(PackageManager pm, int icon,
			String packageName) {
		if (icon != 0) {
			Drawable dr = pm.getDrawable(packageName, icon,
					MainDesk.nContext.getApplicationInfo());
			if (dr != null) {
				return dr;
			}
		}
		return pm.getDefaultActivityIcon();
	}

	/**
	 * ÇØ´ç ¹®ÀÚ°¡ INITIAL_SOUNDÀÎÁö °Ë»ç.
	 * 
	 * @param searchar
	 * @return
	 */
	public static boolean isInitialSound(char searchar) {
		for (char c : INITIAL_SOUND) {
			if (c == searchar) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInitialSound(InitialValue initialValue) {
		for (char c : INITIAL_SOUND) {
			initialValue.position++;
			if (c == initialValue.initialChar) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ÇØ´ç ¹®ÀÚÀÇ ÀÚÀ½À» ¾ò´Â´Ù.
	 * 
	 * @param c
	 *            °Ë»çÇÒ ¹®ÀÚ
	 * @return
	 */
	public static char getInitialSound(char c) {
		int hanBegin = (c - HANGUL_BEGIN_UNICODE);
		int index = hanBegin / HANGUL_BASE_UNIT;
		return INITIAL_SOUND[index];
	}

	/**
	 * ÇØ´ç ¹®ÀÚ°¡ ÇÑ±ÛÀÎÁö °Ë»ç
	 * 
	 * @param c
	 *            ¹®ÀÚ ÇÏ³ª
	 * @return
	 */
	public static boolean isHangul(char c) {
		return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
	}

	// /**
	// * * °Ë»öÀ» ÇÑ´Ù. ÃÊ¼º °Ë»ö ¿Ïº® Áö¿øÇÔ.
	// *
	// * @param value
	// * : °Ë»ö ´ë»ó ex> ÃÊ¼º°Ë»öÇÕ´Ï´Ù
	// * @param search
	// * : °Ë»ö¾î ex> ¤µ°Ë¤µÇÕ¤¤
	// * @return ¸ÅÄª µÇ´Â°Å Ã£À¸¸é true ¸øÃ£À¸¸é false.
	// */
	// public static boolean matchString(String value, String search) {
	// int t = 0;
	// int seof = value.length() - search.length();
	// int slen = search.length();
	//
	// if (seof < 0)
	// return false; // °Ë»ö¾î°¡ ´õ ±æ¸é false¸¦ ¸®ÅÏÇÑ´Ù.
	// for (int i = 0; i <= seof; i++) {
	// t = 0;
	// while (t < slen) {
	// if (isInitialSound(search.charAt(t)) == true
	// && isHangul(value.charAt(i + t))) {
	// // ¸¸¾à ÇöÀç charÀÌ ÃÊ¼ºÀÌ°í value°¡ ÇÑ±ÛÀÌ¸é
	// if (getInitialSound(value.charAt(i + t)) == search
	// .charAt(t))
	// // °¢°¢ÀÇ ÃÊ¼º³¢¸® °°ÀºÁö ºñ±³ÇÑ´Ù
	// t++;
	// else
	// break;
	// } else {
	// // charÀÌ ÃÊ¼ºÀÌ ¾Æ´Ï¶ó¸é
	// if (value.charAt(i + t) == search.charAt(t))
	// // ±×³É °°ÀºÁö ºñ±³ÇÑ´Ù.
	// t++;
	// else
	// break;
	// }
	// }
	// if (t == slen)
	// return true; // ¸ðµÎ ÀÏÄ¡ÇÑ °á°ú¸¦ Ã£À¸¸é true¸¦ ¸®ÅÏÇÑ´Ù.
	// }
	// return false; // ÀÏÄ¡ÇÏ´Â °ÍÀ» Ã£Áö ¸øÇßÀ¸¸é false¸¦ ¸®ÅÏÇÑ´Ù.
	// }

	public static String getQueryForKorean(String queryText) {
		String tempText = null;
		StringBuilder sb = new StringBuilder();
		InitialValue iv = null;
		if (!queryText.isEmpty()) {
			for (int i = 0; i < queryText.length(); i++) {
				tempText = queryText.substring(i, i + 1);
				iv = new InitialValue(tempText.charAt(0), 0);
				if (isInitialSound(iv)) {
					sb.append("and (substr(AppName," + (i + 1) + ",1) >= '"
							+ tempText + "' AND substr(AppName," + (i + 1)
							+ ",1) < '" + CHECK_SOUND[iv.position] + "')");
				}
				else {
					sb.append("and (substr(txt," + (i + 1) + ",1) == '" + tempText + "'");
				}
			}
		}
		return sb.toString();
	}

	static class InitialValue {
		char initialChar;
		int position;

		public InitialValue(char c, int p) {
			initialChar = c;
			position = p;
		}
	}
}
