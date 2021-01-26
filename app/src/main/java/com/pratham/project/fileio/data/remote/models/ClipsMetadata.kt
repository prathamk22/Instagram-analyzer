package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class ClipsMetadata(
    @SerializedName("additional_audio_info") val additionalAudioInfo: AdditionalAudioInfo? = null,
    @SerializedName("branded_content_tag_info") val brandedContentTagInfo: BrandedContentTagInfo? = null,
    @SerializedName("featured_label") val featuredLabel: Any? = null,
    @SerializedName("is_shared_to_fb") val isSharedToFb: Boolean? = null,
    @SerializedName("mashup_info") val mashupInfo: MashupInfo? = null,
    @SerializedName("music_info") val musicInfo: Any? = null,
    @SerializedName("nux_info") val nuxInfo: Any? = null,
    @SerializedName("original_sound_info") val originalSoundInfo: Any? = null,
    @SerializedName("shopping_info") val shoppingInfo: Any? = null,
    @SerializedName("viewer_interaction_settings") val viewerInteractionSettings: Any? = null
)