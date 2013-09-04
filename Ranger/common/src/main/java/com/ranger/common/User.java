package com.ranger.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

//CREATE TABLE `WB_USER` (
//		  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
//		  `UID` char(50) NOT NULL,
//		  `SCREEN_NAME` varchar(100) NOT NULL,
//		  `NAME` varchar(200) NOT NULL,
//		  `PROVINCE` int(11) DEFAULT '-1',
//		  `CITY` int(11) DEFAULT '-1',
//		  `LOCATION` varchar(200) DEFAULT '',
//		  `DESCRIPTION` varchar(200) DEFAULT '',
//		  `URL` varchar(200) DEFAULT '',
//		  `PROFILE_IMAGE_URL` varchar(200) DEFAULT '',
//		  `USER_DOMAIN` varchar(100) DEFAULT '',
//		  `GENDER` char(1) DEFAULT 'N',
//		  `FOLLOWERS_COUNT` int(11) DEFAULT '0',
//		  `FRIENDS_COUNT` int(11) DEFAULT '0',
//		  `STATUSES_COUNT` int(11) DEFAULT '0',
//		  `FAVOURITES_COUNT` int(11) DEFAULT '0',
//		  `CREATE_AT` datetime DEFAULT NULL,
//		  `FOLLOWING` char(1) DEFAULT 'N',
//		  `VERIFIED` char(1) DEFAULT 'N',
//		  `VERIFIED_TYPE` int(11) DEFAULT '-1',
//		  `ALLOW_ALL_ACT_MSG` char(1) DEFAULT 'N',
//		  `ALLOW_ALL_COMMENT` char(1) DEFAULT 'N',
//		  `FOLLOW_ME` char(1) DEFAULT 'N',
//		  `AVATAR_LARGE` varchar(200) DEFAULT '',
//		  `ONLINE_STATUS` int(11) DEFAULT '-1',
//		  `BI_FOLLOWERS_COUNT` int(11) DEFAULT '0',
//		  `REMARK` varchar(500) DEFAULT '',
//		  `LANG` char(2) DEFAULT 'CN',
//		  `VERIFIED_REASON` varchar(100) DEFAULT '',
//		  `WEIHAO` varchar(50) DEFAULT '',
//		  `STATUS_ID` char(10) DEFAULT '',
//		  PRIMARY KEY (`ID`),
//		  UNIQUE KEY `UID_UNIQUE` (`UID`)
//		) 

public class User implements DataObject {
	
	private Long id; 					  //1 DB ID
	private String uid;                   //2 用户UID
	private String screenName;            //3 微博昵称
	private String name;                  //4 友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
	private Integer province;                 //5 省份编码（参考省份编码表）
	private Integer city;                     //6 城市编码（参考城市编码表）
	private String location;              //7 地址
	private String description;           //8 个人描述
	private String url;                   //9 用户博客地址
	private String profileImageUrl;       //10 自定义图像
	private String userDomain;            //11 用户个性化URL
	private String gender;                //12 性别,m--男，f--女,n--未知
	private Integer followersCount;           //13 粉丝数
	private Integer friendsCount;             //14 关注数
	private Integer statusesCount;            //15 微博数
	private Integer favouritesCount;          //16 收藏数
	private Date createdAt;               //17 创建时间
	private boolean following;            //18 保留字段,是否已关注(此特性暂不支持)
	private boolean verified;             //19 加V标示，是否微博认证用户
	private Integer verifiedType;             //20 认证类型
	private boolean allowAllActMsg;       //21 是否允许所有人给我发私信
	private boolean allowAllComment;      //22 是否允许所有人对我的微博进行评论
	private boolean followMe;             //23 此用户是否关注我
	private String avatarLarge;           //24 大头像地址
	private Integer onlineStatus;             //25 用户在线状态	
	private Integer biFollowersCount;         //26 互粉数
	private String remark;                //27 备注信息，在查询用户关系时提供此字段。
	private String lang;                  //28 用户语言版本
	private String verifiedReason;		  //39 认证原因
	private String weihao;				  //30 微號
	private String statusId;			  //31 
	
	private List<Tag> tags;
	
	public User() {
		
	}
	
	public User(
			Long id, 
			String uid, 
			String screenName, 
			String name, 
			Integer province, 
			Integer city, 
			String location, 
			String description, 
			String url, 
			String profileImageUrl,
			String userDomain, 
			String gender, 
			Integer followersCount, 
			Integer friendsCount, 
			Integer statusesCount, 
			Integer favouritesCount, 
			Date createdAt, 
			boolean following, 
			boolean verified, 
			int verifiedType, 
			boolean allowAllActMsg, 
			boolean allowAllComment, 
			boolean followMe, 
			String avatarLarge, 
			Integer onlineStatus,  
			Integer biFollowersCount, 
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

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
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

	public Integer getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public Integer getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
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

	public Integer getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(Integer verifiedType) {
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

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getBiFollowersCount() {
		return biFollowersCount;
	}

	public void setBiFollowersCount(Integer biFollowersCount) {
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

	@Override
	public List<DataField> getDataFields() {
		List<DataField> dataFieldList = new ArrayList<DataField>(); 		
		DataField dataField = new DataField(DataFieldType.LONG, 1, id);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 2, uid);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 3, screenName);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 4, name);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 5, province);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 6, city);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 7, location);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 8, description);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 9, url);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 10, profileImageUrl);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 11, userDomain);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 12, gender);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 13, followersCount);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 14, friendsCount);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 15, statusesCount);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 16, favouritesCount);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.DATE, 17, createdAt);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.BOOLEAN, 18, following);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.BOOLEAN, 19, verified);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 20, verifiedType);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.BOOLEAN, 21, allowAllActMsg);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.BOOLEAN, 22, allowAllComment);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.BOOLEAN, 23, followMe);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 24, avatarLarge);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 25, onlineStatus);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.INTEGER, 26, biFollowersCount);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 27, remark);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 28, lang);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 29, verifiedReason);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 30, weihao);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 31, statusId);
		dataFieldList.add(dataField);
		return dataFieldList;
	}

	
	
}
