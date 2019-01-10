//
//  CONTANS.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import Foundation
import UIKit

let DEVICE_WIDTH:CGFloat = UIScreen.main.bounds.size.width;
let DEVICE_HEIGHT:CGFloat = UIScreen.main.bounds.size.height;
let NAV_BAR_HEIGHT:CGFloat = 64;

//let REQUEST_BASE_URL:String = "192.168.1.122:8280";
//let REQUEST_TEXT_BASE_URL:String = "192.168.1.123:60";
//let REQUEST_BASE_URL_SHARED:String = "mdev.askdog.com";

//mdev.askdog.com

let REQUEST_BASE_URL:String = "m.askdog.com";
let REQUEST_TEXT_BASE_URL:String = "m.askdog.com";
let REQUEST_BASE_URL_SHARED:String = "m.askdog.com";

//let DEFAULT_USERNAME:String = "wangzhch@hooying.com";
//let DEFAULT_PASSWORD:String = "584liucui";


//let DEFAULT_USERNAME:String = "zhaody@hooying.com";
//let DEFAULT_PASSWORD:String = "12345678";

let DEFAULT_USERNAME:String = "";
let DEFAULT_PASSWORD:String = "";


//let REQUEST_BASE_URL:String = "askdog.tunnel.qydev.com";
//let REQUEST_TEXT_BASE_URL:String = "askdog.tunnel.qydev.com";

//发布程序时 选false
//let OPEN_ICONSOLE:Bool = true;
let OPEN_ICONSOLE:Bool = false;

//device
//iphone 6/6s
let IS_IPHONE66S_DEV:Bool! = (UIScreen.main.bounds.size.height == 667) ? true : false;
let IS_IPHONE55S_DEV:Bool! = (UIScreen.main.bounds.size.height == 568) ? true : false;
let IS_IPHONE6S_PLUS_DEV:Bool! = (UIScreen.main.bounds.size.height == 736) ? true : false;

enum CornerMaskType  : Int {
    case cornerMaskType_None    
    case cornerMaskType_All
    case cornerMaskType_Top
    case cornerMaskType_Bottom
    case cornerMaskType_Left
    case cornerMaskType_Right
}


//let CATEGORY_IMG_DIC:[String:String] = ["LAW":"law","SCIENCE":"science",
//                                      "EDUCATION":"education","ART":"art",
//                                      "ENTERTAINMENT":"entertainment","CULTURE":"Culture",
//                                      "LIFE":"life","ECONOMIC":"economy","HEALTH":"health","IT":"IT"];


let WIFI_PLAY_KEY:String = "WIFI_PLAY_KEY";
let LOGIN_USERNAME_KEY:String = "LOGIN_USERNAME_KEY";
let LOGIN_PASSWORD_KEY:String = "LOGIN_PASSWORD_KEY";
/// 登陆用户缓存的图片
let LOGIN_USER_HEADER_IMG_KEY:String = "LOGIN_USER_HEADER_IMG_KEY";
let WELCOME_VIEW_SHOW_KEY:String = "WELCOME_VIEW_SHOW_KEY"
let REGISTER_SEND_SMS_COUNT_DOWN_TIME:String = "REGISTER_SEND_SMS_COUNT_DOWN_TIME";
let RESETPWD_SEND_SMS_COUNT_DOWN_TIME:String = "RESETPWD_SEND_SMS_COUNT_DOWN_TIME";


let WEIXIN_APP_ID:String = "wx861de98443c15c52";
let WEIXIN_APP_SECRET:String = "f8e325bbe7c840b875ea8a732139afb4";

let WEIBO_APPKEY            = "691533910"
let WEIBO_SECRECT           = "fd74937e72a3228aa57f60d48afbd679"
let WEIBO_RREDIRECTURL      = "http://open.weibo.com/apps/691533910/info/advanced"

let TENCENT_APPID           = "1105654292"
let TENCENT_APPKEY          = "TN776vxPZ5yifLZM"


//NSUserDefaultsKey
let WB_TOKEN                = "wbtoken"                 //sina token
let WB_USERID               = "wbuserID"                //sina userid
let WB_REFRESHTOKEN         = "wbRefreshToken"          //sina refreshToken
let WB_EXPITRATIONDATA      = "wbExpirationDate"        //sina expirationDate

let QQ_TOKEN                = "qqtoken"                 //qq token


//分页加载

let PAGE_SIZE                = "15"

//个性签名字数限制
let Signature_Count          = 40


//支付方式代码
let PAY_WAY_WEIXIN:String = "WXPAY";


//240 242 247 背景灰

//https://modao.cc/app/qGhy9vEo3cjWCuyhbOwJtfA7gmUJdYY
//$(PRODUCT_BUNDLE_IDENTIFIER)
//com.hooying.askdog
//com.askdog.ios
// pod update --verbose --no-repo-update


//http://hovertree.com/h/bjaf/an9x2qsa.htm
//http://www.cocoachina.com/bbs/read.php?tid-328718.html

// 发布确认事项：!!!!!!!!!!!
// id改为com.askdog.ios
// url全部改为外网IP
// 登录默认密码关闭
// 关掉手机LOG功能


//https://itunes.apple.com/us/app/askdog-jing-yan-fen-xiang/id1144422327?l=zh&ls=1&mt=8


//Color
let Color_WhiteTitle                = UIColor(red: 255/255.0, green: 255/255.0, blue: 255/255.0, alpha: 1)
let Color_greyLine                  = UIColor(red: 226/255.0, green: 231/255.0, blue: 242/255.0, alpha: 1)
let Color_greyListText              = UIColor(red: 50/255.0, green: 87/255.0, blue: 121/255.0, alpha: 1)
let Color_greyInputText             = UIColor(red: 90/255.0, green: 99/255.0, blue: 111/255.0, alpha: 1)
let Color_greyHeaderBg              = UIColor(red: 251/255.0, green: 252/255.0, blue: 255/255.0, alpha: 1)
let Coler_Blue                      = UIColor(red: 32/255.0, green: 181/255.0, blue: 255/255.0, alpha: 1)


//let Color_searchBkView              = UIColor(red: 242/255, green: 244/255, blue: 249/255, alpha: 1)
//let Color_cellFooterBtn             = UIColor(red: 62/255, green: 190/255, blue: 253/255, alpha: 1)
//let Color_cellLine                  = UIColor(red: 239/255, green: 240/255, blue: 245/255, alpha: 1)
//let Color_historyHeader              = UIColor(red: 249/255, green: 251/255, blue: 255/255, alpha: 1)


