package com.lokarz.gameforview.model.api.retrofit.youtube

import com.lokarz.gameforview.model.api.retrofit.BaseClient

class YoutubeClient :
    BaseClient<IYoutubeService>(baseUrl = "https://www.youtube.com", classService = IYoutubeService::class.java)