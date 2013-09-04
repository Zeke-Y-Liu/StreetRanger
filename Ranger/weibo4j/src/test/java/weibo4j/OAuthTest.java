package weibo4j;

import org.testng.annotations.Test;

import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class OAuthTest {

	@Test
	public void testAuth() {
		Oauth oauth = new Oauth();
		String state = new Long(System.currentTimeMillis()).toString();
		String code = null;
		try {
			code = oauth.authorize("code", state);
		} catch (WeiboException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			System.out.println(oauth.getAccessTokenByCode(code));
		} catch (WeiboException e) {
			if(401 == e.getStatusCode()){
			}else{
				e.printStackTrace();
			}
		}
	}
	
}
