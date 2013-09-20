package com.ranger.collector;

import java.util.ArrayList;
import java.util.Collection;
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
import com.ranger.dao.Dao;
import com.ranger.util.AccessTokenUtil;

/*
 * after OAuth 2.0 API, the Friendships.getFollowersById is NOT allowed to get user's info other than trusted account
 * so this class is deprecated.
 * leave it here for reference
 */
@Deprecated
public class FriendCollector {
	
	static Logger log = Logger.getLogger(Collector.class.getName());
	
	private Friendships fm = new Friendships();
	private Tags tm = new Tags();
	private Dao dao;
	
	public FriendCollector(Dao dao) {
		fm.client.setToken(AccessTokenUtil.getAccessToken());
		tm.client.setToken(AccessTokenUtil.getAccessToken());
		this.dao = dao;
	}
	// uid set pool, hold a bunch of uid to be fetch from weibo site.
	private DataPool<String> avaiableUserIdSetPool = new SetDataPool<String>(0);
	
	// hold a set of user to be flushed to database
	private DataPool<User> userPool = new SetDataPool<User>(100);
	
	// a list of friends of a uid
	private List<User> friends = new ArrayList<User>();
	// for scheduler to make decision easily, each call on this method, only fire one weibo request
	// either get user's friends by uid or get a user's tags by uid.
	// 1. pick up one user from friend list, get tags for that user and put into user pool
	// 2. if the friend list is empty, pick up a uid from uid pool and get friend list for that user
	public void collect() {
		if(!friends.isEmpty()) {
			com.ranger.common.User user =  friends.remove(0);
			List<Tag> wbTags = null;
			try {
				wbTags = tm.getTags(user.getUid());
			} catch (WeiboException e) {
				log.error(e);
				// in case exception, ignore, proceed with next one
				return;
			}
			List<com.ranger.common.Tag> tags = new ArrayList<com.ranger.common.Tag>();
			for(Tag t : wbTags) {
				com.ranger.common.Tag tag = new com.ranger.common.Tag(null, t.getId(), t.getValue(), t.getWeight(), null);
				tags.add(tag);
			}
			user.setTags(tags);
			userPool.add(user);
		} else {
			// String uid = avaiableUserIdSetPool.takeOne();
			String uid = null;
			if(uid == null) {
				return;
			}
			UserWapper wbUsers = null;
			try {
				wbUsers = fm.getFollowersById(uid);
			} catch (WeiboException e) {
				log.error(e);
				// in case exception, ignore, proceed with next one
				return;
			}
			for(weibo4j.model.User u : wbUsers.getUsers()){
				User user = new User(null, // database PK
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
				friends.add(user);
			}
		}
	}
	
	public int flush2DB() {
		int count = userPool.size();
		dao.batchInsertUser(userPool.dumpOut());
		return count;
	}
	
	public void initUidPool(Collection<String> uids) {
		for(String uid : uids) {
			avaiableUserIdSetPool.add(uid);
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
