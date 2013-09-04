package com.ranger.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//CREATE TABLE `WB_STATUS` (
//		  `ID` bigint(20) NOT NULL AUTO_INCREMENT,  
//		  `STID` char(50) NOT NULL,
//		  `U_ID` bigint(20) DEFAULT NULL,
//		  `CREATE_AT` datetime DEFAULT NULL,
//		  `MID` char(100) DEFAULT NULL,
//		  `ID_STR` bigint(20) DEFAULT -1,  
//		  `TEXT` varchar(500) DEFAULT '',
//		  `SR_ID` bigint(20) DEFAULT -1,
//		  `FAVORRITED` char(1) DEFAULT 'N',
//		  `TRUNCATED` char(1) DEFAULT 'N',
//		  `IN_REPLY_TO_STATUS_ID` bigint(20) DEFAULT -1,  
//		  `IN_REPLY_TO_USER_ID` bigint(20) DEFAULT -1, 
//		  `IN_REPLY_TO_SCREEN_NAME` varchar(100) DEFAULT NULL,
//		  `THUNMB_NAIL_PIC` varchar(200) DEFAULT '',
//		  `B_MIDDLE_PIC` varchar(200) DEFAULT '',
//		  `RETWEETED_STATUS_ID` bigint(20) DEFAULT -1, 
//		  `GEO` char(100) DEFAULT '',
//		  `LATITUDE` DOUBLE DEFAULT 0,
//		  `LONGITUDE` DOUBLE DEFAULT 0,
//		  `REPORTS_COUNT` int(11) DEFAULT 0,
//		  `COMMENTS_COUNT` int(11) DEFAULT 0,
//		  `ANNOTATIONS` varchar(200) DEFAULT NULL,
//		  `M_LEVEL` int(11) DEFAULT 0,
//		  `V_ID` bigint(20) DEFAULT -1,
//		  PRIMARY KEY (`ID`),
//		  UNIQUE KEY `STID_UNIQUE` (`STID`),
//		  CONSTRAINT `U_ID_STATUS` FOREIGN KEY (`U_ID`) REFERENCES `wb_user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
//		  CONSTRAINT `SR_ID` FOREIGN KEY (`SR_ID`) REFERENCES `wb_source` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
//		  CONSTRAINT `IN_REPLY_TO_STATUS_ID` FOREIGN KEY (`IN_REPLY_TO_STATUS_ID`) REFERENCES `wb_status` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
//		  CONSTRAINT `IN_REPLY_TO_USER_ID` FOREIGN KEY (`IN_REPLY_TO_USER_ID`) REFERENCES `wb_user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
//		  CONSTRAINT `RETWEETED_STATUS_ID` FOREIGN KEY (`RETWEETED_STATUS_ID`) REFERENCES `wb_status` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
//		  CONSTRAINT `V_ID` FOREIGN KEY (`V_ID`) REFERENCES `wb_visible` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
//)

public class Status implements DataObject {
	private Long id;					//1 db pk 
	private String stid;				//2 status id
	private Long userId;                //3 作者 id
	private Date createdAt;             //4 status创建时间
	private String mid;                 //5 微博MID
	private Long idstr;                 //6 保留字段，请勿使用                     
	private String text;                //7 微博内容
	private Long sourceId;              //8 微博来源 id
	private boolean favorited;          //9 是否已收藏
	private boolean truncated;          //10 
	private Long inReplyToStatusId;     //11 回复ID
	private Long inReplyToUserId;       //12 回复人ID
	private String inReplyToScreenName; //13 回复人昵称
	private String thumbnailPic;        //14 微博内容中的图片的缩略地址
	private String bmiddlePic;          //15 中型图片
	private String originalPic;         //16 原始图片
	private Long retweetedStatusId;     //17 转发的博文，内容为status，如果不是转发，则没有此字段
	private String geo;                 //18 地理信息，保存经纬度，没有时不返回此字段
	private Double latitude = -1d;      //19 纬度
	private Double longitude = -1d;     //20 经度
	private Integer repostsCount;       //21 转发数
	private Integer commentsCount;      //22 评论数
	private String annotations;         //23 元数据，没有时不返回此字段
	private Integer mlevel;				//24 
	private Long visibleId;				//25
	
	
	public Status() {
		
	}
	
	public Status( Long id,
				   String stid,
				   Long userId,
				   Date createdAt,
				   String mid,
				   Long idstr,
				   String text,
				   Long sourceId,
				   boolean favorited,
				   boolean truncated,
				   Long inReplyToStatusId,
				   Long inReplyToUserId,
				   String inReplyToScreenName,
				   String thumbnailPic,
				   String bmiddlePic,
				   String originalPic,
				   Long retweetedStatusId,
				   String geo,
				   double latitude,
				   double longitude,
				   Integer repostsCount,
				   Integer commentsCount,
				   String annotations,
				   Integer mlevel,
				   Long visibleId) {
		this.id = id;
		this.stid = stid;
		this.userId = userId;
		this.createdAt = createdAt;
		this.mid = mid;
		this.idstr = idstr;
		this.text = text;
		this.sourceId = sourceId;
		this.favorited = favorited;
		this.truncated = truncated;
		this.inReplyToStatusId = inReplyToStatusId;
		this.inReplyToUserId = inReplyToUserId;
		this.inReplyToScreenName = inReplyToScreenName;
		this.thumbnailPic = thumbnailPic;
		this.bmiddlePic = bmiddlePic;
		this.originalPic = originalPic;
		this.retweetedStatusId = retweetedStatusId;
		this.geo = geo;
		this.latitude = latitude;
		this.longitude = longitude;
		this.repostsCount = repostsCount;
		this.commentsCount = commentsCount;
		this.annotations = annotations;
		this.mlevel = mlevel;
		this.visibleId = visibleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStid() {
		return stid;
	}

	public void setStid(String stid) {
		this.stid = stid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getIdstr() {
		return idstr;
	}

	public void setIdstr(Long idstr) {
		this.idstr = idstr;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public Long getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(Long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	public String getThumbnailPic() {
		return thumbnailPic;
	}

	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public String getBmiddlePic() {
		return bmiddlePic;
	}

	public void setBmiddlePic(String bmiddlePic) {
		this.bmiddlePic = bmiddlePic;
	}

	public String getOriginalPic() {
		return originalPic;
	}

	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

	public Long getRetweetedStatusId() {
		return retweetedStatusId;
	}

	public void setRetweetedStatusId(Long retweetedStatusId) {
		this.retweetedStatusId = retweetedStatusId;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getRepostsCount() {
		return repostsCount;
	}

	public void setRepostsCount(Integer repostsCount) {
		this.repostsCount = repostsCount;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public Integer getMlevel() {
		return mlevel;
	}

	public void setMlevel(Integer mlevel) {
		this.mlevel = mlevel;
	}

	public Long getVisibleId() {
		return visibleId;
	}

	public void setVisibleId(Long visibleId) {
		this.visibleId = visibleId;
	}

	@Override
	public List<DataField> getDataFields() {
		List<DataField> dataFieldList = new ArrayList<DataField>(); 
		
		DataField dataField = new DataField(DataFieldType.LONG, 1, id);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 2, stid);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.LONG, 3, userId);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.DATE, 4, createdAt);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 5, mid);
		dataField = new DataField(DataFieldType.LONG, 6, idstr);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 7, text);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.LONG, 8, sourceId);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.BOOLEAN, 9, favorited);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.BOOLEAN, 10, truncated);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.LONG, 11, inReplyToStatusId);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.LONG, 12, inReplyToUserId);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 13, inReplyToScreenName);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 14, thumbnailPic);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 15, bmiddlePic);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 16, originalPic);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.LONG, 17, retweetedStatusId);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 18, geo);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.DOUBLE, 19, latitude);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.DOUBLE, 20, longitude);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.INTEGER, 21, repostsCount);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.INTEGER, 22, commentsCount);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.STRING, 23, annotations);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.INTEGER, 24, mlevel);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.LONG, 25, visibleId);
		dataFieldList.add(dataField);
		
		return dataFieldList;
	}
		
}
