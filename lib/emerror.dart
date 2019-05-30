class EMError {
  /// \~chinese
  /// 无错误
  ///
  /// \~english
  /// no error
  static const int EM_NO_ERROR = 0;

  /// \~chinese
  /// 一般错误，未细分的错误一般通过此errorcode抛出来
  ///
  /// \~english
  /// general error
  static const int GENERAL_ERROR = 1;

  /// \~chinese
  /// 网络异常
  ///
  /// \~english
  /// network not available
  ///
  static const int NETWORK_ERROR = 2;

  /// \~chinese
  /// 服务受限
  ///
  /// \~english
  /// Exceed to service limit
  static const int EXCEED_SERVICE_LIMIT = 4;

  /// \~chinese
  /// appkey不正确
  ///
  /// \~english
  /// app key is invalid
  static const int INVALID_APP_KEY = 100;

  /// \~chinese
  /// 用户id不正确
  ///
  /// \~english
  /// username is invalid
  static const int INVALID_USER_NAME = 101;

  /// \~chinese
  /// 密码不正确
  ///
  /// \~english
  /// password is invalid
  static const int INVALID_PASSWORD = 102;

  /// \~chinese
  /// url不正确
  ///
  /// \~english
  /// url is invalid
  static const int INVALID_URL = 103;

  /// \~chinese
  /// token无效
  ///
  /// \~english
  /// invalid token
  static const int INVALID_TOKEN = 104;

  /// \~chinese
  /// 用户已登录
  ///
  /// \~english
  /// user already login
  static const int USER_ALREADY_LOGIN = 200;

  /// \~chinese
  /// 用户未登录
  ///
  /// \~english
  /// user has not login
  static const int USER_NOT_LOGIN = 201;

  /// \~chinese
  /// 用户id或密码错误
  ///
  /// \~english
  /// username or password is wrong
  static const int USER_AUTHENTICATION_FAILED = 202;

  /// \~chinese
  /// 用户已经存在
  ///
  /// \~english
  /// user already exist
  static const int USER_ALREADY_EXIST = 203;

  /// \~chinese
  /// 不存在此用户
  ///
  /// \~english
  /// user does not exist
  static const int USER_NOT_FOUND = 204;

  /// \~chinese
  /// 参数不合法
  ///
  /// \~english
  /// illegal argument
  static const int USER_ILLEGAL_ARGUMENT = 205;

  /// \~chinese
  /// 账户在另外一台设备登录
  ///
  /// \~english
  /// user already logged on in another device
  static const int USER_LOGIN_ANOTHER_DEVICE = 206;

  /// \~chinese
  /// 账户被删除
  ///
  /// \~english
  /// user has been removed from server
  static const int USER_REMOVED = 207;

  /// \~chinese
  /// 注册失败
  ///
  /// \~english
  /// registration failed
  static const int USER_REG_FAILED = 208;

  /// \~chinese
  /// 更新用户信息失败
  ///
  /// \~english
  /// update user info failed
  static const int USER_UPDATEINFO_FAILED = 209;

  /// \~chinese
  /// 用户没有该操作权限
  ///
  /// \~english
  /// user has no permission for the operation
  static const int USER_PERMISSION_DENIED = 210;

  /// \~chinese
  /// 绑定设备token失败
  ///
  /// \~english
  /// device token binding failed
  static const int USER_BINDDEVICETOKEN_FAILED = 211;

  /// \~chinese
  /// 解绑设备token失败
  ///
  /// \~english
  /// device token unbinding failed
  static const int USER_UNBIND_DEVICETOKEN_FAILED = 212;

  /// \~chinese
  /// 当前帐户已经绑定另外的设备，不允许当前设备自动登录
  ///
  /// \~english
  /// Bind another device and do not allow auto login
  static const int USER_BIND_ANOTHER_DEVICE = 213;

  /// \~chinese
  /// 当前用户登陆的设备数过多
  ///
  /// \~english
  /// User login on too many devices
  static const int USER_LOGIN_TOO_MANY_DEVICES = 214;

  /// \~chinese
  /// 用户在群组和聊天室被禁言
  ///
  /// \~english
  /// User mutes in groups or chatrooms
  static const int USER_MUTED = 215;

  /// \~chinese
  /// 用户修改密码
  ///
  /// \~english
  /// User has changed the password
  static const int USER_KICKED_BY_CHANGE_PASSWORD = 216;

  /// \~chinese
  /// 用户被其他设备踢掉
  ///
  /// \~english
  /// User was kicked by other device or console backend
  static const int USER_KICKED_BY_OTHER_DEVICE = 217;

  /// \~chinese
  /// 无法访问到服务器
  ///
  /// \~english
  /// server is not reachable
  static const int SERVER_NOT_REACHABLE = 300;

  /// \~chinese
  /// 等待服务器响应超时
  ///
  /// \~english
  /// waiting for server response timeout
  static const int SERVER_TIMEOUT = 301;

  /// \~chinese
  /// 服务器繁忙
  ///
  /// \~english
  /// server is busy
  static const int SERVER_BUSY = 302;

  /// \~chinese
  /// 未知的server异常
  ///
  /// \~english
  /// unknown server error
  static const int SERVER_UNKNOWN_ERROR = 303;

  /// \~chinese
  /// 获取dns失败
  ///
  /// \~english
  /// get DNS list failed
  static const int SERVER_GET_DNSLIST_FAILED = 304;

  /// \~chinese
  /// IM功能限制
  ///
  /// \~english
  /// service is restricted
  static const int SERVER_SERVICE_RESTRICTED = 305;

  /// \~chinese
  /// 文件不存在
  ///
  /// \~english
  /// file does not exist
  static const int FILE_NOT_FOUND = 400;

  /// \~chinese
  /// 文件不合法
  ///
  /// \~english
  /// invalid file
  static const int FILE_INVALID = 401;

  /// \~chinese
  /// 文件上传失败
  ///
  /// \~english
  /// file uploading failed
  static const int FILE_UPLOAD_FAILED = 402;

  /// \~chinese
  /// 文件下载失败
  ///
  /// \~english
  /// file downloading failed
  static const int FILE_DOWNLOAD_FAILED = 403;

  /// \~chinese
  /// 文件删除失败
  ///
  /// \~english
  /// file delete failed
  static const int FILE_DELETE_FAILED = 404;

  /// \~chinese
  /// 文件太大
  ///
  /// \~english
  /// file too large
  static const int FILE_TOO_LARGE = 405;

  /// \~chinese
  /// 消息不合法
  ///
  /// \~english
  /// invalid message
  static const int MESSAGE_INVALID = 500;

  /// \~chinese
  /// 消息内容包含非法或敏感词
  ///
  /// \~english
  /// message include illegal content
  static const int MESSAGE_INCLUDE_ILLEGAL_CONTENT = 501;

  /// \~chinese
  /// 消息发送过快，触发限流
  ///
  /// \~english
  /// message is sent too rapidly, triggering rate limit
  static const int MESSAGE_SEND_TRAFFIC_LIMIT = 502;

  /// \~chinese
  /// 消息加解密错误
  ///
  /// \~english
  /// message encryption error
  static const int MESSAGE_ENCRYPTION_ERROR = 503;

  /// \~chinese
  /// 群id不正确
  ///
  /// \~english
  /// invalid group id
  static const int GROUP_INVALID_ID = 600;

  /// \~chinese
  /// 已经加入的群组
  ///
  /// \~english
  /// already joined group
  static const int GROUP_ALREADY_JOINED = 601;

  /// \~chinese
  /// 尚未加入此群组
  ///
  /// \~english
  /// user has not joined the group
  static const int GROUP_NOT_JOINED = 602;

  /// \~chinese
  /// 群组权限不够
  ///
  /// \~english
  /// user has no permission for the operation
  static const int GROUP_PERMISSION_DENIED = 603;

  /// \~chinese
  /// 群成员已满
  ///
  /// \~english
  /// group capacity reached
  static const int GROUP_MEMBERS_FULL = 604;

  /// \~chinese
  /// 群组不存在
  ///
  /// \~english
  /// group does not exist
  static const int GROUP_NOT_EXIST = 605;

  /// \~chinese
  /// 聊天室id不正确
  ///
  /// \~english
  /// invalid chatroom id
  static const int CHATROOM_INVALID_ID = 700;

  /// \~chinese
  /// 已经在此聊天室里
  ///
  /// \~english
  /// user already joined the chatroom
  static const int CHATROOM_ALREADY_JOINED = 701;

  /// \~chinese
  /// 尚未加入此聊天室
  /// \~english
  /// user has not joined the chatroom
  static const int CHATROOM_NOT_JOINED = 702;

  /// \~chinese
  /// 聊天室权限不够
  /// \~english
  /// user has no permission for the operation
  static const int CHATROOM_PERMISSION_DENIED = 703;

  /// \~chinese
  /// 聊天室成员已满
  ///
  /// \~english
  /// chatroom capacity reached
  static const int CHATROOM_MEMBERS_FULL = 704;

  /// \~chinese
  /// 聊天室不存在
  ///
  /// \~english
  /// chatroom does not exist
  static const int CHATROOM_NOT_EXIST = 705;

  /// \~chinese
  ///  callid无效
  ///
  /// \~english
  /// call id is invalid
  static const int CALL_INVALID_ID = 800;

  /// \~chinese
  /// 正在通话中
  ///
  /// \~english
  /// call in process
  static const int CALL_BUSY = 801;

  /// \~chinese
  /// 对方不在线
  ///
  /// \~english
  /// remote is offline
  static const int CALL_REMOTE_OFFLINE = 802;

  /// \~chinese
  /// 建立连接失败
  ///
  /// \~english
  /// establish connection failed
  static const int CALL_CONNECTION_ERROR = 803;

  /// \~chinese
  /// 会议创建失败
  ///
  /// \~ english
  /// conference create failed
  static const int CALL_CONFERENCE_CREATE_FAILED = 804;

  /// \~chinese
  /// 会议取消
  ///
  /// \~ english
  /// conference cancel
  static const int CALL_CONFERENCE_CANCEL = 805;

  /// \~chinese
  /// 已经加入
  ///
  /// \~english
  /// already join
  static const int CALL_ALREADY_JOIN = 806;

  /// \~chinese
  /// 已经 Publish
  ///
  /// \~english
  /// already publish
  static const int CALL_ALREADY_PUB = 807;

  /// \~chinese
  /// 已经 Subscribe
  ///
  /// \~english
  /// already subscribe
  static const int CALL_ALREADY_SUB = 808;

  /// \~chinese
  /// 没有 Session
  ///
  /// \~english
  /// no session
  static const int CALL_NO_SESSION = 809;

  /// \~chinese
  /// 没有 Publish
  ///
  /// \~english
  /// no publish
  static const int CALL_NO_PUBLISH = 810;

  /// \~chinese
  /// 没有 Subscribe
  ///
  /// \~english
  /// no subscribe
  static const int CALL_NO_SUBSCRIBE = 811;

  /// \~chinese
  /// 没有 Stream
  ///
  /// \~english
  /// no stream
  static const int CALL_NO_STREAM = 812;

  /// \~chinese
  /// 会议 Ticket 无效
  ///
  /// \~english
  /// Conference ticket invalid
  static const int CALL_TICKET_INVALID = 813;

  /// \~chinese
  /// 会议 Ticket 过期
  ///
  /// \~english
  /// Conference ticket expired
  static const int CALL_TICKET_EXPIRED = 814;

  /// \~chinese
  /// 会议会话过期
  ///
  /// \~english
  /// Conference session expired
  static const int CALL_SESSION_EXPIRED = 815;

  /// \~chinese
  /// 会议不存在或者已经解散
  ///
  /// \~english
  /// Conference not exist or dismiss
  static const int CALL_CONFERENCE_NO_EXIST = 816;

  /// \~chinese
  /// 无效的摄像头编号
  ///
  /// \~english
  /// invalid camera index
  static const int CALL_INVALID_CAMERA_INDEX = 817;

  /// \~chinese
  /// 无效的会议参数
  ///
  /// \~english
  /// invalid conference params
  static const int CALL_INVALID_PARAMS = 818;

  /// \~chinese
  /// 通话连接超时
  ///
  /// \~english
  /// connection timeout
  static const int CALL_CONNECTION_TIMEOUT = 819;

  /// \~chinese
  /// 通话加入超时
  ///
  /// \~english
  /// Call join timeout
  static const int CALL_JOIN_TIMEOUT = 820;

  /// \~chinese
  /// 通过其他设备加入会议
  ///
  /// \~english
  /// Other device join conference
  static const int CALL_OTHER_DEVICE = 821;

  /// \~chinese
  /// 会议解散
  ///
  /// \~english
  /// Conference dismiss
  static const int CALL_CONFERENCE_DISMISS = 822;

  /// \~chinese
  /// 当前设备不支持推送
  ///
  /// \~english
  /// Current device don't support push.
  static const int PUSH_NOT_SUPPORT = 900;

  /// \~chinese
  /// Push Token绑定失败
  ///
  /// \~english
  /// Push token bind failed.
  static const int PUSH_BIND_FAILED = 901;

  /// \~chinese
  /// Push Token解除绑定失败
  ///
  /// \~english
  /// Push token unbind failed.
  static const int PUSH_UNBIND_FAILED = 902;
}
