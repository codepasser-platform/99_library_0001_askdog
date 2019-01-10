//
//  HYLimitText.h
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/8.
//  Copyright © 2016年 Hooying. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "PlaceholderTextView.h"
/**
 对字数有限制的通用输入框,代理方法返回当前输入框信息
 */
@class HYLimitText;

typedef NS_ENUM(char, TextInputType) {
    TextInputTypeTextfield = 0,
    TextInputTypeTextView,
};

@protocol HYLimitTextDelegate <NSObject>
@optional

//超出字数限制代理方法(弹出框提示等等)
- (void)limitTextLimitInputOverStop:(HYLimitText *)textLimitInput;

//返回输入框中的信息
- (void)limitTextLimitInput:(HYLimitText *) textLimitInput text:(NSString *)text;

@end

@interface HYLimitText : NSObject

- (void)addNotTextFile:(UITextField *) textFile;
- (void)addNotTextView:(PlaceholderTextView *) textView;

@property (nonatomic, assign)NSInteger maxLength; //允许最大的输入个数,默认是一个很大的数
@property (nonatomic, assign)id<HYLimitTextDelegate>delegate;


//以下属性便于外部设置字体以及是否是第一响应者，但是不可设置外部代理。否则内部代理失效
@property (nonatomic, strong) UITextField *textField;  ///< 输入框
@property (nonatomic, strong) PlaceholderTextView *textView;  ///< 输入框


@end
