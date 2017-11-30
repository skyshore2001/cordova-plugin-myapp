package com.daca.myapp;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyApp extends CordovaPlugin {
	public static final String TAG = "MyApp";

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if ("getAppVersion".equals(action)) {
			final String cmd = args.getString(0);
			final JSONObject opt = args.getJSONObject(1);
			final CallbackContext cb = callbackContext;

			try {
				JSONObject r = getAppVersion();
				cb.success(r);
			} catch (Exception e) {
				cb.error(e.getMessage());
			}

			/* 异步操作示例
			cordova.getThreadPool().execute(new Runnable() {
				@Override
				public void run() {
					try {
						JSONObject r = getAppVersion();
						cb.success(r);
					} catch (Exception e) {
						cb.error(e.getMessage());
					}
				}
			});
			*/
		}
		else {
			return false;
		}
		return true;
	}

	protected JSONObject getAppVersion() throws Exception {
		PackageManager packageManager = this.cordova.getActivity().getPackageManager();
		JSONObject r = new JSONObject();
		r.put("version", packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName);
		r.put("build", packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionCode);
		// 出错示例：throw new Exception("fail to get version");
		return r;
	}
}