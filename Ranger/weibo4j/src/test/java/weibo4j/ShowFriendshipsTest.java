package weibo4j;

import java.util.List;

import org.testng.annotations.Test;

import weibo4j.model.Tag;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.Log;

public class ShowFriendshipsTest {

	@Test
	public void testShowTags() {
		String access_token = "2.00d5evFEnhnsoBc45cd025fencuzbE";//get token code from <code>OAuth4Code.java</code>
		String uid ="3752152991";
		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		Tags tm = new Tags();
		tm.client.setToken(access_token);
		try {
			UserWapper wbUsers = fm.getFollowersById(uid);
			Log.logInfo("[Followers] : " + wbUsers.getUsers().size());
			for(User user : wbUsers.getUsers()){
				Log.logInfo("-------follower--------");
				Log.logInfo(user.toString());
				List<Tag> wbTags = tm.getTags(user.getId());
				for(Tag tag : wbTags){
					Log.logInfo(tag.toString());
				}	
				
				
				
			}			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	
}
