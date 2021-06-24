package com.lokarz.gameforview.util

class Constant {

    class Firestore {
        companion object {
            const val VIDEOS_COLLECTION = "videosCollection"
            const val REVIEW_DOCUMENT = "reviewDocument"
        }
    }

    class Youtube {
        companion object {
            const val YOUTUBE_COM = "www.youtube.com"
            const val YOUTU_BE = "youtu.be"
        }
    }

    class Error {
        companion object {
            const val INVALID_ID = "invalid_id"
            const val ID_ALREADY_ADDED = "id_already_added"
            const val SOMETHING_WENT_WRONG = "something_went_wrong"
            const val REWARD_ALREADY_SHOWN = "reward_already_shown"
            const val NOT_ENOUGH_POINTS = "not_enough_points"

        }
    }

    class Success {
        companion object {
            const val VALID_ID = "valid_id"
            const val REWARD_SUCCESS = "reward_success"
        }
    }
}