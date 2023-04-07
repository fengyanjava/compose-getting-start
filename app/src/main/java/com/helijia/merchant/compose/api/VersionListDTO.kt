package com.helijia.merchant.compose.api

/**
 * Created by 星空 on 2023/4/7 14:10
 * Email: xingkong@helijia.com
 */


data class VersionList(
    val widgets: List<WidgetData>?
) {

}

data class WidgetData(
    val data: VersionInfo
)

data class VersionListDTO(
    val success: Boolean,
    val data: VersionList?
)
