package dev.bughub.flt_easemob_im

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.chinahrt.flutter_plugin_demo.QueuingEventSink
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class FltEasemobImPlugin(var registrar: Registrar) : MethodCallHandler {

    val loginEventSink = QueuingEventSink()
    val logoutEventSink = QueuingEventSink()

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
            call.method == "initSDK" -> {
                val options = EMOptions()
                // 默认添加好友时，是不需要验证的，改成需要验证
                options.acceptInvitationAlways = false
                // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
                options.serverTransfer = true
                // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
                options.isAutodownload = true
                initSDK(registrar.context(), options, debugMode = true)
            }
            call.method == "createAccount" -> {
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
            call.method == "login" -> {

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
            call.method == "logout" -> {
                val unbindToken = call.argument<Boolean>("unbindToken")?:false
                EMClient.getInstance().logout(unbindToken)
                result.success(null)
            }
            call.method == "logoutAysnc" -> {
                val unbindToken = call.argument<Boolean>("unbindToken")?:false
                EMClient.getInstance().logout(unbindToken,object :EMCallBack{
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
                        logoutEventSink.error(code.toString(), message = (error ?: ""), details = "")
                    }

                })
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }
}
