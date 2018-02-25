package br.com.produzz.util;

import org.json.JSONObject;

public class JSONObjectWrapper extends JSONObject {
	/**
	 * @param data
	 */
	public JSONObjectWrapper(final String data) {
		super(data);
	}

	@Override
	public String getString(final String key) {
		if (super.has(key)) {
			return super.getString(key);
		} else {
			return null;
		}
	}

	@Override
	public boolean getBoolean(final String key) {
		if (super.has(key)) {
			return super.getBoolean(key);

		} else {
			return false;
		}
	}
}
