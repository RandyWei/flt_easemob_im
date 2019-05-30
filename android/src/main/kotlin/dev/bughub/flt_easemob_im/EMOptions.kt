package dev.bughub.flt_easemob_im

import android.text.TextUtils
import java.util.ArrayList

class EMOptions {
    /**
     * \~chinese
     * 是否自动接受加好友邀请
     *
     * \~english
     * whether accept friend invitation for user automatically
     */
    var acceptInvitationAlways = true

    /**
     * \~chinese
     * 是否自动接受群组邀请
     *
     * \~english
     * whether accept group invitation automatically
     */
     var autoAcceptGroupInvitation = true

    /**
     * \~chinese
     * 是否需要消息接受方已读确认，缺省true
     *
     * \~english
     * whether receive message read by receiving user event
     */
    var requireReadAck = true

    /**
     * \~chinese
     * 是否需要消息接收方送达确认，缺省false
     *
     * \~english
     * whether receive message delivered to server event
     */
     var requireDeliveryAck = false

     var deleteMessagesAsExitGroup = true

     var isChatroomOwnerLeaveAllowed = true

     var enableAutoLogin = true

     var imServer: String? = null
     var dnsUrl = ""
     var imPort: Int = 0
     var sortMessageByServerTime = true
     var useFCM = true
     var restServer: String? = null
     var usingHttpsOnly = false
     var serverTransfer = true
     var isAutodownload = true
     var enableDNSConfig = true

     var pushConfig: EMPushConfig? = null
}

class EMPushConfig{
     var fcmSenderId: String? = null
     var hwAppId: String? = null
     var miAppId: String? = null
     var miAppKey: String? = null
     var mzAppId: String? = null
     var mzAppKey: String? = null
     var oppoAppKey: String? = null
     var oppoAppSecret: String? = null
     var vivoAppId: String? = null
     var vivoAppKey: String? = null
}
