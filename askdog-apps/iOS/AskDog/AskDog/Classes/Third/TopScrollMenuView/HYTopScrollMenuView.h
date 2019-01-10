//
//  HYTopScrollMenuView.h
//  textMenu
//
//  Created by Hooying_zhaody on 16/9/19.
//  Copyright © 2016年 Hooying_zhaody. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "SGTopScrollMenu.h"

@class HYTopScrollMenuView;

@protocol HYTopScrollMenuViewDataSource <NSObject>

@required
// view数量
- (NSInteger)numberOfViewsInTopScrollMenuView:(HYTopScrollMenuView *)topScrollMenu;
// titles
- (NSArray *)menuViewTitlesForTopScrollMenuView:(HYTopScrollMenuView *)topScrollMenu;
//views
- (UIView *)TopScrollMenuView:(HYTopScrollMenuView *)topScrollMenu viewAtIndex:(NSInteger)index;


@end


@protocol HYTopScrollMenuViewDelegate <NSObject>
// delegate 方法

- (void)topScrollMenuView:(HYTopScrollMenuView *)topScrollMenu didSelectView:(UIView*)view index:(NSInteger)index;
//- (void)selectButtonForTopScrollMenuView:(HYTopScrollMenuView *)topScrollMenu;

@end


@interface HYTopScrollMenuView : UIView 
@property (nonatomic, strong) UIScrollView *bgScrollView; //底部滚动视图
@property (nonatomic, strong) SGTopScrollMenu *menuView;  //
@property (nonatomic, weak) id<HYTopScrollMenuViewDelegate> delegate;
@property (nonatomic, weak) id<HYTopScrollMenuViewDataSource> dataSoure;

//加载刷新页面
- (void)reloadTopScrollMenuView;

@end
