package weibo4j;

import org.testng.annotations.Test;
import weibo4j.model.WeiboException;

public class SearchTest {

	@Test
	public void testSearchUser() {
		String access_token = "2.00d5evFEnhnsoBc45cd025fencuzbE";//get token code from <code>OAuth4Code.java</code>
		
		Search search = new Search();
		search.client.setToken(access_token);
		try {
			System.out.println(search.searchSuggestionsUsers("阿"));
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
