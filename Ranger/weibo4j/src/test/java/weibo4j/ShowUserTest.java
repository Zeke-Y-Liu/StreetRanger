package weibo4j;

import org.testng.annotations.Test;

import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.Log;

public class ShowUserTest {

	@Test
	public void testShowUser() {
		String access_token = "2.00d5evFEnhnsoBc45cd025fencuzbE";//get token code from <code>OAuth4Code.java</code>
//		String uid ="3752152991";
		String uid ="2734116907";
		
		Users um = new Users();
		um.client.setToken(access_token);
		try {
			User user = um.showUserById(uid);
			Log.logInfo(user.toString());
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	
}
