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

	private static final char HANGUL_BEGIN_UNICODE = 44032; // ��
	private static final char HANGUL_LAST_UNICODE = 55203; // �R
	private static final char HANGUL_BASE_UNIT = 588;// ������ ���� ������ ���ڼ�
	private static final char[] INITIAL_SOUND = { '��', '��', '��', '��', '��', '��',
			'��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��' };
	private static final char[] CHECK_SOUND = { '��', '��', '��', '��', '��', '��',
			'��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��',
			'�R' };

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
	 * �ش� ���ڰ� INITIAL_SOUND���� �˻�.
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
	 * �ش� ������ ������ ��´�.
	 * 
	 * @param c
	 *            �˻��� ����
	 * @return
	 */
	public static char getInitialSound(char c) {
		int hanBegin = (c - HANGUL_BEGIN_UNICODE);
		int index = hanBegin / HANGUL_BASE_UNIT;
		return INITIAL_SOUND[index];
	}

	/**
	 * �ش� ���ڰ� �ѱ����� �˻�
	 * 
	 * @param c
	 *            ���� �ϳ�
	 * @return
	 */
	public static boolean isHangul(char c) {
		return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
	}

	// /**
	// * * �˻��� �Ѵ�. �ʼ� �˻� �Ϻ� ������.
	// *
	// * @param value
	// * : �˻� ��� ex> �ʼ��˻��մϴ�
	// * @param search
	// * : �˻��� ex> ���ˤ��դ�
	// * @return ��Ī �Ǵ°� ã���� true ��ã���� false.
	// */
	// public static boolean matchString(String value, String search) {
	// int t = 0;
	// int seof = value.length() - search.length();
	// int slen = search.length();
	//
	// if (seof < 0)
	// return false; // �˻�� �� ��� false�� �����Ѵ�.
	// for (int i = 0; i <= seof; i++) {
	// t = 0;
	// while (t < slen) {
	// if (isInitialSound(search.charAt(t)) == true
	// && isHangul(value.charAt(i + t))) {
	// // ���� ���� char�� �ʼ��̰� value�� �ѱ��̸�
	// if (getInitialSound(value.charAt(i + t)) == search
	// .charAt(t))
	// // ������ �ʼ����� ������ ���Ѵ�
	// t++;
	// else
	// break;
	// } else {
	// // char�� �ʼ��� �ƴ϶��
	// if (value.charAt(i + t) == search.charAt(t))
	// // �׳� ������ ���Ѵ�.
	// t++;
	// else
	// break;
	// }
	// }
	// if (t == slen)
	// return true; // ��� ��ġ�� ����� ã���� true�� �����Ѵ�.
	// }
	// return false; // ��ġ�ϴ� ���� ã�� �������� false�� �����Ѵ�.
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
