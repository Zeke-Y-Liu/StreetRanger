package com.ranger.collector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import weibo4j.Tags;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.Tag;
import weibo4j.model.WeiboException;

import com.ranger.common.DataPool;
import com.ranger.common.SetDataPool;
import com.ranger.common.User;
import com.ranger.dao.Dao;

/*
 * 1. hold a set of weibo user id in a pool, for each id, get user's friends' information
 * 2. put all the friend objects into user pool, if the pool is full, flush the data pool to database
 * 3. add the friends' uids to user id pool
 * 4. for each id, get user's friends' info recursively
 */

public class TimelineCollector implements Collector {
	static Logger log = Logger.getLogger(TimelineCollector.class.getName());
	
	private Timeline tlm = new Timeline();
	private Tags tm = new Tags();
	private Dao dao;
	
	public TimelineCollector(Dao dao, String accessToken) {
		if("".equals(StringUtils.trimToEmpty(accessToken))) {
			log.error("access token is not valid.");
			return;
		}
		tlm.client.setToken(accessToken);
		tm.client.setToken(accessToken);
		this.dao = dao;
	}
	
	// hold a set of user to be flushed to database
	private DataPool<User> userPool = new SetDataPool<User>(100);

	// for scheduler to make decision easily, each call on this method, only fire one weibo request
	// either get user's info by latest statuses from weibo
	// 1. pick up one user from user's list, get tags for that user and populate it
	// 2. if the user list is empty, pick up a bunch of user by latest statuses 
	// 3. loop the above steps recursively
	@Override
	public void collect() {
		System.out.println("EEEEE");
		if(userPool.hasNext()) {
			System.out.println("FFFFF");
			com.ranger.common.User user =  userPool.next();
			List<Tag> wbTags = null;
			try {
				wbTags = tm.getTags(user.getUid());
			} catch (WeiboException e) {
				log.error(e);
				e.printStackTrace();
				// in case exception, ignore, proceed with next one
				return;
			}
			List<com.ranger.common.Tag> tags = new ArrayList<com.ranger.common.Tag>();
			for(Tag t : wbTags) {
				com.ranger.common.Tag tag = new com.ranger.common.Tag(null, t.getId(), t.getValue(), t.getWeight(), null);
				tags.add(tag);
			}
			user.setTags(tags);
			System.out.println("GGGGG");
		} else {
			System.out.println("TimelineCollector:accessToken=" + tlm.client.getToken());
			try {
				StatusWapper status = tlm.getPublicTimeline(10,0);//max count is 200
				System.out.println("AAAAAA");
				for(Status s : status.getStatuses()){
					System.out.println("BBBBB");
					weibo4j.model.User u = s.getUser();
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
					System.out.println("CCCCC");
					// duplicated check in db
					if(dao.loadUserByUid(u.getId())==null) {
						userPool.add(user);
					} else {
						log.warn("duplicated user, uid=" + u.getId());
					}
				}
				System.out.println("DDDDDD");
			} catch (WeiboException e) {
				log.error(e);
				e.printStackTrace();
				return;
			}
		}
	}
	
	public int flush2DB() {
		int count = userPool.size();
		dao.batchInsertUser(userPool.dumpOut());
		return count;
	}

	/*
	 * return the number of the users in pool
	 * used to determine if we exceed the restriction of 1000 request per hour
	 */
//	public int getUserPoolSize() {
//		return userPool.size();
//		
//	}

	/*
	 * (non-Javadoc)
	 * @see com.ranger.collector.Collector#isNeedToFlush()
	 */
	
	@Override
	public boolean isReadyToFlush() {
		return !userPool.hasNext();
	}
}
