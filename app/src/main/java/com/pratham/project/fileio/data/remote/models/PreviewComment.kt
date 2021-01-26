package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class PreviewComment(
    @SerializedName("bit_flags") val bitFlags: Int? = null,
    @SerializedName("comment_like_count") val commentLikeCount: Int? = null,
    @SerializedName("content_type") val contentType: String? = null,
    @SerializedName("created_at") val createdAt: Int? = null,
    @SerializedName("created_at_utc") val createdAtUtc: Int? = null,
    @SerializedName("did_report_as_spam") val didReportAsSpam: Boolean? = null,
    @SerializedName("has_liked_comment") val hasLikedComment: Boolean? = null,
    @SerializedName("has_translation") val hasTranslation: Boolean? = null,
    @SerializedName("is_covered") val isCovered: Boolean? = null,
    @SerializedName("media_id") val mediaId: Long? = null,
    @SerializedName("parent_comment_id") val parentCommentId: Long? = null,
    @SerializedName("pk") val pk: Long? = null,
    @SerializedName("private_reply_status") val privateReplyStatus: Int? = null,
    @SerializedName("share_enabled") val shareEnabled: Boolean? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("text") val text: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("user") val user: UserXXXX? = null,
    @SerializedName("user_id") val userId: Long? = null
)