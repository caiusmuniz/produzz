package br.com.produzz.facebook;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.Campaign;

public class PagingExample {
	public static final String ACCESS_TOKEN = ExampleConfig.ACCESS_TOKEN;
	public static final Long ACCOUNT_ID = ExampleConfig.ACCOUNT_ID;
	public static final String APP_SECRET = ExampleConfig.APP_SECRET;
	public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET).enableDebug(false);

	public static void main(String[] args) throws APIException {
		AdAccount account = new AdAccount(ACCOUNT_ID, context);
		APINodeList<Campaign> campaigns = account.getCampaigns().requestAllFields().execute();

		while (campaigns != null) {
			for (Campaign campaign : campaigns) {
				System.out.println(campaign);
			}

			campaigns = campaigns.nextPage();
		}
	}
}
