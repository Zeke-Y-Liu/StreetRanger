package weibo4j;

import org.testng.annotations.Test;

import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.Log;



public class GetPublicTimelineTest {

	@Test
	public void testGetPublicTimeline() {
		String access_token = "2.00d5evFEnhnsoBc45cd025fencuzbE";//get token code from <code>OAuth4Code.java</code>
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			StatusWapper status = tm.getPublicTimeline(20,0);//max count is 200
			for(Status s : status.getStatuses()){
//				Log.logInfo(s.toString());
				Log.logInfo(s.getUser().toString());

			}
			System.out.println(status.getNextCursor());
			System.out.println(status.getPreviousCursor());
			System.out.println(status.getTotalNumber());
			System.out.println(status.getHasvisible());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
