//
//  HYLimitText.m
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/8.
//  Copyright © 2016年 Hooying. All rights reserved.
//

#import "HYLimitText.h"

@interface HYLimitText()<UITextFieldDelegate,UITextViewDelegate>
{
    NSString *contentText;
}
@property (nonatomic, assign)TextInputType type;  ///< 输入类型

@end

@implementation HYLimitText


- (void)addNotTextFile:(UITextField *) textFile {
    self.type = TextInputTypeTextfield;
    self.textField = textFile;
    //添加通知
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(textFiledEditChanged:)
                                                name:UITextFieldTextDidChangeNotification object:self.textField];
}

- (void)addNotTextView:(PlaceholderTextView *) textView {
    self.type = TextInputTypeTextView;
    self.textView = textView;
    //添加通知
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(textFiledEditChanged:)
                                                name:UITextViewTextDidChangeNotification object:self.textView];

}



-(void)textFiledEditChanged:(NSNotification *)obj
{
    
    NSString *toBeString = (self.type == TextInputTypeTextfield ?self.textField.text:self.textView.text);
    NSString *lang = [[UIApplication sharedApplication] textInputMode].primaryLanguage; // 键盘输入模式
    if ([lang isEqualToString:@"zh-Hans"]) { // 简体中文输入，包括简体拼音，健体五笔，简体手写
        UITextRange *selectedRange = [(self.type == TextInputTypeTextfield ?self.textField:self.textView) markedTextRange];
        //高亮部分
        UITextPosition *position = [(self.type == TextInputTypeTextfield ?self.textField:self.textView) positionFromPosition:selectedRange.start offset:0];
        //已输入的文字进行字数统计和限制
        if (!position) {
            if (toBeString.length > self.maxLength) {
                if(self.type == TextInputTypeTextfield)
                    self.textField.text = [toBeString substringToIndex:self.maxLength];
                else
                    self.textView.text = [toBeString substringToIndex:self.maxLength];
                
                if(self.delegate && [self.delegate respondsToSelector:@selector(limitTextLimitInputOverStop:)]){
                    [self.delegate limitTextLimitInputOverStop:self];
                }
            }
            
            if(self.delegate && [self.delegate respondsToSelector:@selector(limitTextLimitInput:text:)]){
                NSString *text;
                
                if(self.type == TextInputTypeTextfield){
                    text  = self.textField.text;
                }
                else{
                    text  = self.textView.text;
                }
                contentText = text;
                [self.delegate limitTextLimitInput:self text:text];
            }
        }
    }
    else{
        if (toBeString.length > self.maxLength) {
            if(self.delegate && [self.delegate respondsToSelector:@selector(limitTextLimitInputOverStop:)]){
                [self.delegate limitTextLimitInputOverStop:self];
            }
            
            if(self.type == TextInputTypeTextfield)
                self.textField.text = [toBeString substringToIndex:self.maxLength];
            else
                self.textView.text = [toBeString substringToIndex:self.maxLength];
        }
        
        if(self.delegate && [self.delegate respondsToSelector:@selector(limitTextLimitInput:text:)]){
            NSString *text;
            
            if(self.type == TextInputTypeTextfield){
                text  = self.textField.text;
            }
            else{
                text  = self.textView.text;
            }
            contentText = text;
            [self.delegate limitTextLimitInput:self text:text];
        }
    }
}


- (void)dealloc
{
    if(self.textField){
        [[NSNotificationCenter defaultCenter] removeObserver:self name:UITextFieldTextDidChangeNotification object:nil];
        self.textField.delegate = nil;
        self.textField = nil;
    }
    if(self.textView){
        [[NSNotificationCenter defaultCenter] removeObserver:self name:UITextViewTextDidChangeNotification object:nil];
        self.textView.delegate = nil;
        self.textView = nil;
    }
}


@end
