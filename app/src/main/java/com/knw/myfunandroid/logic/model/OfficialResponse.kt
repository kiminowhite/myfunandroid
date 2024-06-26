package com.knw.myfunandroid.logic.model

//菜单
data class OfficialChaptersResponse(
    val data: List<OfficialChapterItem>,
    val errorCode: Int,
    val errorMsg: String
)

data class OfficialChapterItem(
    val articleList: List<Any>,
    val author: String,
    val children: List<Any>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val type: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)
//数据

data class OfficialResponse(
    val data: OfficialArticleData,
    val errorCode: Int,
    val errorMsg: String
)

data class OfficialArticleData(
    val curPage: Int,
    val datas: List<OfficialArticle>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class OfficialArticle(
    val adminAdd: Boolean,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val isAdminAdd: Boolean,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

