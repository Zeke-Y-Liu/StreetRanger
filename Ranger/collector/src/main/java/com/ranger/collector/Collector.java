package com.ranger.collector;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import weibo4j.Friendships;
import weibo4j.Tags;
import weibo4j.model.Tag;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

import com.ranger.common.DataPool;
import com.ranger.common.SetDataPool;
import com.ranger.common.User;
import com.ranger.util.AccessTokenUtil;

/*
 * 1. hold a set of weibo user id in a pool, for each id, get user's friends' information
 * 2. put all the friend objects into user pool, if the pool is full, flush the data pool to database
 * 3. add the friends' uids to user id pool
 * 4. for each id, get user's friends' info recursively
 */

public class Collector {
	
	static Logger log = Logger.getLogger(Collector.class.getName());
	
	private Friendships fm = new Friendships();
	private Tags tm = new Tags();
	
	public Collector() {
		fm.client.setToken(AccessTokenUtil.getAccessToken());
		tm.client.setToken(AccessTokenUtil.getAccessToken());
	}
	// uid set pool, hold a bunch of uid to be fetch from weibo site.
	private DataPool<String> avaiableUserIdSetPool = new SetDataPool<String>(0);
	
	// hold a set of user to be flushed to database
	private DataPool<User> userPool = new SetDataPool<User>(1000);
		
	//1. get user uid from user uid pool
	//2. get use's friends' info by uid, including tags and put all the friend users into user pool
	//3. put the friends's uid into uid pool for next round
	public void collect() {
		String uid = avaiableUserIdSetPool.takeOne();
		if(uid == null) {
			return;
		}
		try {
			UserWapper users = fm.getFollowersById(uid);
			
			for(weibo4j.model.User u : users.getUsers()){
				User wbUser = new User(null, // database PK
									u.getId(),
									u.getScreenName(),
									u.getName(),
									u.getProvince(),
									u.getCity(),
									u.getLocation(),
									u.getDescription(),
									u.getUrl(),
									u.getProfileImageUrl(),
									u.getUserDomain(),
									u.getGender(),
									u.getFollowersCount(),
									u.getFriendsCount(),
									u.getStatusesCount(),
									u.getFavouritesCount(),
									u.getCreatedAt(),
									u.isFollowing(),
									u.isVerified(),
									u.getVerifiedType(),
									u.isallowAllActMsg(),
									u.isallowAllComment(),
									u.isfollowMe(),
									u.getAvatarLarge(),
									u.getOnlineStatus(),
									u.getBiFollowersCount(),
									u.getRemark(),
									u.getLang(),
									u.getVerifiedReason(),
									u.getWeihao(),
									u.getStatusId(),
									null // tags
									);
				// fetch current user's tags
				List<Tag> tags = tm.getTags(uid);
				List<com.ranger.common.Tag> wbTags = new ArrayList<com.ranger.common.Tag>();
				for(Tag t : tags) {
					com.ranger.common.Tag wbTag = new com.ranger.common.Tag(null, t.getId(), t.getValue(), t.getWeight(), null);
					wbTags.add(wbTag);
				}
				wbUser.setTags(wbTags);
				userPool.add(wbUser);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
			log.info(e);
		}
	}
	
	
	// never used yet, feel free to remove it
	@Deprecated
	public User getUserInfoByUid(String uid) {
		User u = new User();
		// private DataSetPool avaiableUserIdSetPool = new  DataSetPool();
		// private DataSetPool userSetPool = new DataSetPool();
		return u;
	}
	/*
	 * return the number of available user ids
	 */
	public boolean hasAvaiableUserId() {
		 return avaiableUserIdSetPool.size() > 0;
	}
	/*
	 * return the number of the users in pool
	 * used to determine if we exceed the restriction of 1000 request per hour
	 */
	public int getUserPoolSize() {
		return userPool.size();
		
	}
	/*
	 * this method is self-evidence
	 */
	public boolean isUserPoolFull() {
		return userPool.isFull();
	}
}
