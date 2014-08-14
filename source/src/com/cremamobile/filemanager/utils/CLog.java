package com.cremamobile.filemanager.utils;

import com.cremamobile.filemanager.Distribute;

import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

public class CLog {
	private static final String LOGTAG = "UOTP";

	// 디버그
	public static void d(Object obj, String text) {
		if (Distribute.IS_DEBUG) {
			if (obj instanceof String) {
				Log.d(LOGTAG, "[" + obj + "] " + text);
			} else if (obj instanceof Handler) {
				Log.d(LOGTAG, "[Handler] " + text);
			} else if (obj instanceof DialogInterface.OnClickListener) {
				Log.d(LOGTAG, "[Dialog] " + text);
			} else {
				Log.d(LOGTAG, "[" + obj.getClass().getSimpleName() + "] "
						+ text);
			}
		}
	}

	// 에러
	public static void e(Object obj, String text) {
		if (Distribute.IS_DEBUG) {
			if (obj instanceof String) {
				Log.e(LOGTAG, "[" + obj + "] ");
			} else if (obj instanceof Handler) {
				Log.e(LOGTAG, "[Handler] ");
			} else if (obj instanceof DialogInterface.OnClickListener) {
				Log.e(LOGTAG, "[Dialog] ");
			} else {
				Log.e(LOGTAG, "[" + obj.getClass().getSimpleName() + "] "
						+ text);
			}
		}
	}

	// 에러
	public static void e(Object obj, String text, Throwable e) {
		if (Distribute.IS_DEBUG) {
			if (obj instanceof String) {
				Log.e(LOGTAG, "[" + obj + "] " + text, e);
			} else if (obj instanceof Handler) {
				Log.e(LOGTAG, "[Handler] " + text, e);
			} else if (obj instanceof DialogInterface.OnClickListener) {
				Log.e(LOGTAG, "[Dialog] " + text, e);
			} else {
				Log.e(LOGTAG, "[" + obj.getClass().getSimpleName() + "] "
						+ text, e);
			}
		}
	}

	// 정보
	public static void i(Object obj, String text) {
		if (Distribute.IS_DEBUG) {
			if (obj instanceof String) {
				Log.i(LOGTAG, "[" + obj + "] " + text);
			} else if (obj instanceof Handler) {
				Log.i(LOGTAG, "[Handler] " + text);
			} else if (obj instanceof DialogInterface.OnClickListener) {
				Log.i(LOGTAG, "[Dialog] " + text);
			} else {
				Log.i(LOGTAG, "[" + obj.getClass().getSimpleName() + "] "
						+ text);
			}
		}
	}

	// 자세한 정보
	public static void v(Object obj, String text) {
		if (Distribute.IS_DEBUG) {
			if (obj instanceof String) {
				Log.v(LOGTAG, "[" + obj + "] " + text);
			} else if (obj instanceof Handler) {
				Log.v(LOGTAG, "[Handler] " + text);
			} else if (obj instanceof DialogInterface.OnClickListener) {
				Log.v(LOGTAG, "[Dialog] " + text);
			} else {
				Log.v(LOGTAG, "[" + obj.getClass().getSimpleName() + "] "
						+ text);
			}
		}
	}

	// 경고
	public static void w(Object obj, String text) {
		if (Distribute.IS_DEBUG) {
			if (obj instanceof String) {
				Log.w(LOGTAG, "[" + obj + "] " + text);
			} else if (obj instanceof Handler) {
				Log.w(LOGTAG, "[Handler] " + text);
			} else if (obj instanceof DialogInterface.OnClickListener) {
				Log.w(LOGTAG, "[Dialog] " + text);
			} else {
				Log.w(LOGTAG, "[" + obj.getClass().getSimpleName() + "] "
						+ text);
			}
		}
	}

}
