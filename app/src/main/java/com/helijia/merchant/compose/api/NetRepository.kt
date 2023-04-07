package com.helijia.merchant.compose.api

import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by 星空 on 2023/4/7 14:08
 * Email: xingkong@helijia.com
 */

object NetRepository {

    interface Service {

        @GET("/api/magic/v2/pages/artisan-version")
        suspend fun getVersionList(): VersionListDTO
    }

    suspend fun getVersionList(): VersionListDTO {
        return ServiceCreator.create<Service>().getVersionList()
    }
}