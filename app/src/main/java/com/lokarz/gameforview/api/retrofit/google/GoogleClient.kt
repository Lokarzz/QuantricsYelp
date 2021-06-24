package com.lokarz.gameforview.api.retrofit.google

import com.lokarz.gameforview.api.retrofit.BaseClient

class GoogleClient :
    BaseClient<IGoogleService>(baseUrl = "https://www.youtube.com", classService = IGoogleService::class.java)