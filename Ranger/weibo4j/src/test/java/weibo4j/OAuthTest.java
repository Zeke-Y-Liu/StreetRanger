package weibo4j;

import org.testng.annotations.Test;

import weibo4j.model.WeiboException;

public class OAuthTest {

	@Test
	public void testAuth() {
		Oauth oauth = new Oauth();
		String state = new Long(System.currentTimeMillis()).toString();
		String code = "";//get token code from <code>OAuth4Code.java</code>
		try {
			code = oauth.authorize(code, state);
		} catch (WeiboException e1) {
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
