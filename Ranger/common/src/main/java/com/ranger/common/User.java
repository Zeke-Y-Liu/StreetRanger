package com.ranger.common;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import weibo4j.model.Status;

public class User {
	
	private Long id; 					  //1 DB ID
	private String uid;                   //2 用户UID
	private String screenName;            //3 微博昵称
	private String name;                  //4 友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
	private int province;                 //5 省份编码（参考省份编码表）
	private int city;                     //6 城市编码（参考城市编码表）
	private String location;              //7 地址
	private String description;           //8 个人描述
	private String url;                   //9 用户博客地址
	private String profileImageUrl;       //10 自定义图像
	private String userDomain;            //11 用户个性化URL
	private String gender;                //12 性别,m--男，f--女,n--未知
	private int followersCount;           //13 粉丝数
	private int friendsCount;             //14 关注数
	private int statusesCount;            //15 微博数
	private int favouritesCount;          //16 收藏数
	private Date createdAt;               //17 创建时间
	private boolean following;            //18 保留字段,是否已关注(此特性暂不支持)
	private boolean verified;             //19 加V标示，是否微博认证用户
	private int verifiedType;             //20 认证类型
	private boolean allowAllActMsg;       //21 是否允许所有人给我发私信
	private boolean allowAllComment;      //22 是否允许所有人对我的微博进行评论
	private boolean followMe;             //23 此用户是否关注我
	private String avatarLarge;           //24 大头像地址
	private int onlineStatus;             //25 用户在线状态	
	private int biFollowersCount;         //26 互粉数
	private String remark;                //27 备注信息，在查询用户关系时提供此字段。
	private String lang;                  //28 用户语言版本
	private String verifiedReason;		  //39 认证原因
	private String weihao;				  //30 微號
	private String statusId;			  //31 
	private Status status = null;         //32  用户最新一条微博
	
	private List<Tag> tags;
	
	public User() {
		
	}
	
	public User(
			Long id, 
			String uid, 
			String screenName, 
			String name, 
			int province, 
			int city, 
			String location, 
			String description, 
			String url, 
			String profileImageUrl,
			String userDomain, 
			String gender, 
			int followersCount, 
			int friendsCount, 
			int statusesCount, 
			int favouritesCount, 
			Date createdAt, 
			boolean following, 
			boolean verified, 
			int verifiedType, 
			boolean allowAllActMsg, 
			boolean allowAllComment, 
			boolean followMe, 
			String avatarLarge, 
			int onlineStatus, 
			Status status,  
			int biFollowersCount, 
			String remark, 
			String lang, 
			String verifiedReason, 
			String weihao, 
			String statusId, 
			List<Tag> tags) {
		this.id = id;
		this.uid = uid;
		this.screenName = screenName;
		this.name = name;
		this.province = province;
		this.city = city;
		this.location = location;
		this.description = description;
		this.url = url;
		this.profileImageUrl = profileImageUrl;
		this.userDomain = userDomain;
		this.gender = gender;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.statusesCount = statusesCount;
		this.favouritesCount = favouritesCount;
		this.createdAt = createdAt;
		this.following = following;
		this.verified = verified;
		this.verifiedType = verifiedType;
		this.allowAllActMsg = allowAllActMsg;
		this.allowAllComment = allowAllComment;
		this.followMe = followMe;
		this.avatarLarge = avatarLarge;
		this.onlineStatus = onlineStatus;
		this.status = status;
		this.biFollowersCount = biFollowersCount;
		this.remark = remark;
		this.lang = lang;
		this.verifiedReason = verifiedReason;
		this.weihao = weihao;
		this.statusId = statusId;
		this.tags = tags;
	}
	
	@Override
	public int hashCode() {
	     return new HashCodeBuilder(7, 13)
	       .append(uid)
	       .toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		User u = (User) obj;
		return new EqualsBuilder()
			.append(uid, u.getUid())
			.isEquals();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getProvince() {
		return province;
	}


	public void setProvince(int province) {
		this.province = province;
	}


	public int getCity() {
		return city;
	}


	public void setCity(int city) {
		this.city = city;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getProfileImageUrl() {
		return profileImageUrl;
	}


	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}


	public String getUserDomain() {
		return userDomain;
	}


	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getFollowersCount() {
		return followersCount;
	}


	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}


	public int getFriendsCount() {
		return friendsCount;
	}


	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}


	public int getStatusesCount() {
		return statusesCount;
	}


	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}


	public int getFavouritesCount() {
		return favouritesCount;
	}


	public void setFavouritesCount(int favouritesCount) {
		this.favouritesCount = favouritesCount;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public boolean isFollowing() {
		return following;
	}


	public void setFollowing(boolean following) {
		this.following = following;
	}


	public boolean isVerified() {
		return verified;
	}


	public void setVerified(boolean verified) {
		this.verified = verified;
	}


	public int getVerifiedType() {
		return verifiedType;
	}


	public void setVerifiedType(int verifiedType) {
		this.verifiedType = verifiedType;
	}


	public boolean isAllowAllActMsg() {
		return allowAllActMsg;
	}


	public void setAllowAllActMsg(boolean allowAllActMsg) {
		this.allowAllActMsg = allowAllActMsg;
	}


	public boolean isAllowAllComment() {
		return allowAllComment;
	}


	public void setAllowAllComment(boolean allowAllComment) {
		this.allowAllComment = allowAllComment;
	}


	public boolean isFollowMe() {
		return followMe;
	}


	public void setFollowMe(boolean followMe) {
		this.followMe = followMe;
	}


	public String getAvatarLarge() {
		return avatarLarge;
	}


	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}


	public int getOnlineStatus() {
		return onlineStatus;
	}


	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public int getBiFollowersCount() {
		return biFollowersCount;
	}


	public void setBiFollowersCount(int biFollowersCount) {
		this.biFollowersCount = biFollowersCount;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getLang() {
		return lang;
	}


	public void setLang(String lang) {
		this.lang = lang;
	}


	public String getVerifiedReason() {
		return verifiedReason;
	}


	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}


	public String getWeihao() {
		return weihao;
	}


	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}


	public String getStatusId() {
		return statusId;
	}


	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}	
	
}
