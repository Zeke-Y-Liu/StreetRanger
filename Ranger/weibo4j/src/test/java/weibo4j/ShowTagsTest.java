package weibo4j;

import java.util.List;

import org.testng.annotations.Test;

import weibo4j.model.Tag;
import weibo4j.model.WeiboException;
import weibo4j.util.Log;

public class ShowTagsTest {

	@Test
	public void testShowTags() {
		String access_token = "2.00d5evFEnhnsoBc45cd025fencuzbE";//get token code from <code>OAuth4Code.java</code>
		String uid ="2552134982";
		Tags tm = new Tags();
		tm.client.setToken(access_token);
		try {
			List<Tag> wbTags = tm.getTags(uid);
			for(Tag tag : wbTags){
				Log.logInfo(tag.toString());
			}			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	
}
