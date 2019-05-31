package dev.bughub.flt_easemob_im

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.chinahrt.flutter_plugin_demo.QueuingEventSink
import com.hyphenate.EMCallBack
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMCmdMessageBody
import com.hyphenate.chat.EMMessage
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class FltEasemobImPlugin(var registrar: Registrar) : MethodCallHandler {

    val loginEventSink = QueuingEventSink()
    val logoutEventSink = QueuingEventSink()
    val messageEventSink = QueuingEventSink()
    var messageListener: EMMessageListener? = null

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val plugin = FltEasemobImPlugin(registrar)
            val channel = MethodChannel(registrar.messenger(), "bughub.dev/flt_easemob_im")
            channel.setMethodCallHandler(plugin)



            EventChannel(registrar.messenger(), "bughub.dev/flt_easemob_im/events:login").setStreamHandler(object : EventChannel.StreamHandler {
                override fun onListen(o: Any?, sink: EventChannel.EventSink?) {
                    plugin.loginEventSink.setDelegate(sink)
                }

                override fun onCancel(o: Any?) {
                    plugin.loginEventSink.setDelegate(null)
                }
            })

            EventChannel(registrar.messenger(), "bughub.dev/flt_easemob_im/events:logout").setStreamHandler(object : EventChannel.StreamHandler {
                override fun onListen(o: Any?, sink: EventChannel.EventSink?) {
                    plugin.logoutEventSink.setDelegate(sink)
                }

                override fun onCancel(o: Any?) {
                    plugin.logoutEventSink.setDelegate(null)
                }
            })


        }

        @JvmStatic
        fun initSDK(context: Context, _options: EMOptions, debugMode: Boolean = false) {

            val options = com.hyphenate.chat.EMOptions()

            options.acceptInvitationAlways = _options.acceptInvitationAlways
            options.isAutoAcceptGroupInvitation = _options.autoAcceptGroupInvitation
            options.requireAck = _options.requireReadAck
            options.requireDeliveryAck = _options.requireDeliveryAck
            options.isDeleteMessagesAsExitGroup = _options.deleteMessagesAsExitGroup
            options.autoLogin = _options.enableAutoLogin
            options.dnsUrl = _options.dnsUrl
            options.imPort = _options.imPort
            options.isSortMessageByServerTime = _options.sortMessageByServerTime
            options.isUseFCM = _options.useFCM


            /**
             * NOTE:你需要设置自己申请的账号来使用三方推送功能，详见集成文档
             */
            val builder = com.hyphenate.push.EMPushConfig.Builder(context)
            builder.enableVivoPush() // 需要在AndroidManifest.xml中配置appId和appKey
                    .enableMeiZuPush(_options.pushConfig?.mzAppId, _options.pushConfig?.mzAppKey)
                    .enableMiPush(_options.pushConfig?.miAppId, _options.pushConfig?.miAppKey)
                    .enableOppoPush(_options.pushConfig?.oppoAppKey,
                            _options.pushConfig?.oppoAppSecret)
                    .enableHWPush() // 需要在AndroidManifest.xml中配置appId
                    .enableFCM(_options.pushConfig?.fcmSenderId)
            options.pushConfig = builder.build()

            options.restServer = _options.restServer
            options.usingHttpsOnly = _options.usingHttpsOnly
            options.allowChatroomOwnerLeave(_options.isChatroomOwnerLeaveAllowed)
            options.enableDNSConfig(_options.enableDNSConfig)
            options.setIMServer(_options.imServer)
            options.setAutoDownloadThumbnail(_options.isAutodownload)
            options.autoTransferMessageAttachments = _options.serverTransfer

            //初始化
            EMClient.getInstance().init(context, options)
            EMClient.getInstance().setDebugMode(debugMode)


            Log.i("Plugin", "initSDK finished")
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {

        when {
            call.method == "initSDK" -> {//初始化 SDK
                val options = EMOptions()
                // 默认添加好友时，是不需要验证的，改成需要验证
                options.acceptInvitationAlways = false
                // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
                options.serverTransfer = true
                // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
                options.isAutodownload = true
                initSDK(registrar.context(), options, debugMode = true)
                result.success(null)
            }
            call.method == "addConnectionListener" -> {
                val connectionEventSink = QueuingEventSink()

                EventChannel(registrar.messenger(), "bughub.dev/flt_easemob_im/events:connection").setStreamHandler(object : EventChannel.StreamHandler {
                    override fun onListen(o: Any?, sink: EventChannel.EventSink?) {
                        connectionEventSink.setDelegate(sink)
                    }

                    override fun onCancel(o: Any?) {
                        connectionEventSink.setDelegate(null)
                    }
                })

                EMClient.getInstance().addConnectionListener(object : EMConnectionListener {
                    override fun onConnected() {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onConnected"
                        connectionEventSink.success(eventResult)
                    }

                    override fun onDisconnected(errorCode: Int) {
                        connectionEventSink.error(errorCode.toString(), message = "", details = "")
                    }

                })
                result.success(null)
            }
            call.method == "addMessageListener" -> {

                EventChannel(registrar.messenger(), "bughub.dev/flt_easemob_im/events:message").setStreamHandler(object : EventChannel.StreamHandler {
                    override fun onListen(o: Any?, sink: EventChannel.EventSink?) {
                        messageEventSink.setDelegate(sink)
                    }

                    override fun onCancel(o: Any?) {
                        messageEventSink.setDelegate(null)
                    }
                })

                messageListener = object : EMMessageListener {
                    override fun onMessageRecalled(messages: MutableList<EMMessage>?) {//消息被撤回
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onMessageRecalled"
                        messageEventSink.success(eventResult)
                    }

                    override fun onMessageChanged(message: EMMessage?, change: Any?) {//消息状态变动
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onMessageChanged"
                        messageEventSink.success(eventResult)
                    }

                    override fun onCmdMessageReceived(messages: MutableList<EMMessage>?) {//收到透传消息
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onCmdMessageReceived"
                        messageEventSink.success(eventResult)
                    }

                    override fun onMessageReceived(messages: MutableList<EMMessage>?) {//收到消息
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onMessageReceived"
                        messageEventSink.success(eventResult)
                    }

                    override fun onMessageDelivered(messages: MutableList<EMMessage>?) {//收到已送达回执
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onMessageDelivered"
                        messageEventSink.success(eventResult)
                    }

                    override fun onMessageRead(messages: MutableList<EMMessage>?) {//收到已读回执
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "onMessageRead"
                        messageEventSink.success(eventResult)
                    }

                }
                EMClient.getInstance().chatManager().addMessageListener(messageListener)
                result.success(null)
            }
            call.method == "removeMessageListener" -> {
                messageEventSink.setDelegate(null)
                EMClient.getInstance().chatManager().removeMessageListener(messageListener)
                result.success(null)
            }

            call.method == "createAccount" -> {//注册
                val username = call.argument<String>("username")
                val password = call.argument<String>("password")
                if (TextUtils.isEmpty(username)) {
                    //code message detail
                    result.error("500", "the username is invalid", null)
                    return
                }

                if (TextUtils.isEmpty(password)) {
                    result.error("500", "the password is invalid", null)
                    return
                }
                EMClient.getInstance().createAccount(username, password)
                result.success(null)
            }
            call.method == "login" -> {//登录

                Log.i("Plugin", "login")

                val username = call.argument<String>("username")
                val password = call.argument<String>("password")
                if (TextUtils.isEmpty(username)) {
                    //code message detail
                    result.error("500", "the username is invalid", null)
                    return
                }

                if (TextUtils.isEmpty(password)) {
                    result.error("500", "the password is invalid", null)
                    return
                }

                EMClient.getInstance().login(username, password, object : EMCallBack {
                    override fun onSuccess() {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "success"
                        loginEventSink.success(eventResult)
                    }

                    override fun onProgress(progress: Int, status: String?) {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "progress"
                        eventResult["status"] = "status"
                        loginEventSink.success(eventResult)
                    }

                    override fun onError(code: Int, error: String?) {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "error"
                        loginEventSink.error(code.toString(), message = (error ?: ""), details = "")
                    }
                })
                result.success(null)
            }
            call.method == "logout" -> {//退出登录（同步）
                val unbindToken = call.argument<Boolean>("unbindToken") ?: false
                EMClient.getInstance().logout(unbindToken)
                result.success(null)
            }
            call.method == "logoutAysnc" -> {//退出登录（异步）
                val unbindToken = call.argument<Boolean>("unbindToken") ?: false
                EMClient.getInstance().logout(unbindToken, object : EMCallBack {
                    override fun onSuccess() {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "success"
                        logoutEventSink.success(eventResult)
                    }

                    override fun onProgress(progress: Int, status: String?) {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "progress"
                        eventResult["status"] = "status"
                        logoutEventSink.success(eventResult)
                    }

                    override fun onError(code: Int, error: String?) {
                        val eventResult = HashMap<String, Any>()
                        eventResult["event"] = "error"
                        logoutEventSink.error(code.toString(), message = (error
                                ?: ""), details = "")
                    }

                })
                result.success(null)
            }
            call.method == "createTxtSendMessage" -> {//发送文本消息,支持扩展消息
                val content = call.argument<String>("content")
                val attributes = call.argument<Map<String, Any>>("attributes") ?: mutableMapOf()
                val toChatUsername = call.argument<String>("toChatUsername")
                val chatType = call.argument<String>("chatType")


                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id
                val message = EMMessage.createTxtSendMessage(content, toChatUsername)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                    EMMessage.ChatType.ChatRoom.toString() -> message.chatType = EMMessage.ChatType.ChatRoom
                }

                for (key in attributes.keys) {
                    val value = attributes[key]
                    when (value) {
                        is String -> message.setAttribute(key, value)
                        is Int -> message.setAttribute(key, value)
                        is Long -> message.setAttribute(key, value)
                        is JSONArray -> message.setAttribute(key, value)
                        is JSONObject -> message.setAttribute(key, value)
                    }
                }

                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }
            call.method == "createVoiceSendMessage" -> {//发送语音消息
                val filePath = call.argument<String>("filePath")
                val toChatUsername = call.argument<String>("toChatUsername")
                val chatType = call.argument<String>("chatType")
                val length = call.argument<Int>("length") ?: 0


                //filePath为语音文件路径，length为录音时间(秒)
                val message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                    EMMessage.ChatType.ChatRoom.toString() -> message.chatType = EMMessage.ChatType.ChatRoom
                }
                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }
            call.method == "createVideoSendMessage" -> {//发送视频消息
                val filePath = call.argument<String>("filePath")
                val thumbPath = call.argument<String>("thumbPath")
                val toChatUsername = call.argument<String>("toChatUsername")
                val chatType = call.argument<String>("chatType")
                val length = call.argument<Int>("length") ?: 0


                //filePath为视频本地路径，thumbPath为视频预览图路径，videoLength为视频时间长度
                val message = EMMessage.createVideoSendMessage(filePath, thumbPath, length, toChatUsername)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                    EMMessage.ChatType.ChatRoom.toString() -> message.chatType = EMMessage.ChatType.ChatRoom
                }
                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }
            call.method == "createImageSendMessage" -> {//发送图片消息
                val filePath = call.argument<String>("filePath")
                val sendOriginalImage = call.argument<Boolean>("sendOriginalImage") ?: false
                val toChatUsername = call.argument<String>("toChatUsername")
                val chatType = call.argument<String>("chatType")


                //filePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
                val message = EMMessage.createImageSendMessage(filePath, sendOriginalImage, toChatUsername)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                    EMMessage.ChatType.ChatRoom.toString() -> message.chatType = EMMessage.ChatType.ChatRoom
                }
                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }
            call.method == "createLocationSendMessage" -> {//发送地理位置消息
                val latitude = call.argument<Double>("latitude") ?: 0.0
                val longitude = call.argument<Double>("longitude") ?: 0.0
                val locationAddress = call.argument<String>("locationAddress")
                val toChatUsername = call.argument<String>("toChatUsername")
                val chatType = call.argument<String>("chatType")


                //latitude为纬度，longitude为经度，locationAddress为具体位置内容
                val message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                    EMMessage.ChatType.ChatRoom.toString() -> message.chatType = EMMessage.ChatType.ChatRoom
                }
                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }

            call.method == "createFileSendMessage" -> {//发送文件消息
                val filePath = call.argument<String>("filePath")
                val toChatUsername = call.argument<String>("toChatUsername")
                val chatType = call.argument<String>("chatType")

                val message = EMMessage.createFileSendMessage(filePath, toChatUsername)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                    EMMessage.ChatType.ChatRoom.toString() -> message.chatType = EMMessage.ChatType.ChatRoom
                }
                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }

            call.method == "createSendMessage" -> {//发送透传消息
                val chatType = call.argument<String>("chatType")
                val actions = call.argument<List<String>>("actions") ?: mutableListOf()
                val toChatUsername = call.argument<String>("toChatUsername")

                val message = EMMessage.createSendMessage(EMMessage.Type.CMD)
                when (chatType) {
                    EMMessage.ChatType.GroupChat.toString() -> message.chatType = EMMessage.ChatType.GroupChat
                    EMMessage.ChatType.Chat.toString() -> message.chatType = EMMessage.ChatType.Chat
                }
                for (action in actions) {
                    val messageBody = EMCmdMessageBody(action)
                    message.addBody(messageBody)
                }

                message.to = toChatUsername
                EMClient.getInstance().chatManager().sendMessage(message)
                result.success(null)
            }

            call.method == "getMessages" -> {//获取消息
                val username = call.argument<String>("username")
                val pageSize = call.argument<Int>("pageSize") ?: 0
                val startMsgId = call.argument<String>("startMsgId")
                val conversation = EMClient.getInstance().chatManager().getConversation(username)
                if (TextUtils.isEmpty(startMsgId) || pageSize <= 0) {
                    //获取此会话的所有消息
                    result.success(conversation.allMessages)
                } else {
                    //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
                    //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
                    result.success(conversation.loadMoreMsgFromDB(startMsgId, pageSize))
                }
            }
            call.method == "getUnreadMsgCount" -> {//获取未读消息数量
                val username = call.argument<String>("username")
                val conversation = EMClient.getInstance().chatManager().getConversation(username)
                result.success(conversation.unreadMsgCount)
            }
            call.method == "makeRead" -> {//未读消息数清零
                val username = call.argument<String>("username")
                val messageId = call.argument<String>("messageId")
                if (!TextUtils.isEmpty(username)) {
                    val conversation = EMClient.getInstance().chatManager().getConversation(username)
                    if (TextUtils.isEmpty(messageId))//如果用户名不为空和message为空，则和该用户相关聊天都置为已读
                        conversation.markAllMessagesAsRead()
                    else//如果用户名不为空和message不为空，则该消息置为已读
                        conversation.markMessageAsRead(messageId)
                } else {//用户名为空，则置所有未读消息为已读
                    EMClient.getInstance().chatManager().markAllConversationsAsRead()
                }
                result.success(null)
            }

            call.method == "getMsgCount" -> {//获取消息总数
                val username = call.argument<String>("username")
                val conversation = EMClient.getInstance().chatManager().getConversation(username)
                result.success(arrayListOf(conversation.allMsgCount,conversation.allMessages.size))
            }

            call.method == "recallMessage"->{//撤回消息功能
//                EMClient.getInstance().chatManager().recallMessage()
                result.success("功能未实现")
            }
            else -> result.notImplemented()
        }
    }
}
