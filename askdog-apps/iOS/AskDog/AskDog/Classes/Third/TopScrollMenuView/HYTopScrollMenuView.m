//
//  HYTopScrollMenuView.m
//  textMenu
//
//  Created by Hooying_zhaody on 16/9/19.
//  Copyright © 2016年 Hooying_zhaody. All rights reserved.
//

#import "HYTopScrollMenuView.h"

#define menuView_H          44
#define button_W            49

@interface HYTopScrollMenuView()<SGTopScrollMenuDelegate, UIScrollViewDelegate>
@property (nonatomic, strong) NSMutableArray *viewData;
@property (nonatomic, strong) NSArray *titles;
@end

@implementation HYTopScrollMenuView
{
    CGFloat _view_H;
    CGFloat _view_W;
}

- (NSMutableArray *)viewData {
    if (_viewData == nil) {
        _viewData = [[NSMutableArray alloc] init];
    }
    return _viewData;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _view_W = CGRectGetWidth(frame);
        _view_H = CGRectGetHeight(frame);
    }
    return self;
}

- (nullable instancetype)initWithCoder:(NSCoder *)aDecoder
{
    self = [super initWithCoder:aDecoder];
    if(self){
        [self setupViews];
    }
    return self;
}

//- (id)awakeAfterUsingCoder:(NSCoder *)aDecoder {
//    self = [super awakeAfterUsingCoder:aDecoder];
//    if (self) {
//        _view_W = CGRectGetWidth(self.frame);
//        _view_H = CGRectGetHeight(self.frame);
//    }
//    return self;
//}

//- (void)awakeFromNib {
//    [super awakeFromNib];
//    _view_W = CGRectGetWidth(self.frame);
//    _view_H = CGRectGetHeight(self.frame);
//}

- (void)reloadTopScrollMenuView {
    
    [self.menuView removeFromSuperview];
    [self.bgScrollView removeFromSuperview];
    [self.viewData removeAllObjects];
    [self setupViews];
}


- (void)setupViews {
    _view_W = CGRectGetWidth(self.frame);
    _view_H = CGRectGetHeight(self.frame);
   
//    //menuView
//    self.titles = [self.dataSoure menuViewTitlesForTopScrollMenuView: self];
////    _menuView = [SGTopScrollMenu topScrollMenuWithFrame:CGRectMake(0, 0, _view_W - button_W -1 , menuView_H)];
//    if (self.titles.count > 4) {
//        _menuView = [SGTopScrollMenu topScrollMenuWithFrame:CGRectMake(0, 0, _view_W, menuView_H)];
//        _menuView.scrollTitleArr = self.titles;
//    } else {
//        _menuView = [SGTopScrollMenu topScrollMenuWithFrame:CGRectMake(50, 0, _view_W -100, menuView_H)];
//        _menuView.staticTitleArr = self.titles;
//    }
//    _menuView.topScrollMenuDelegate = self;
//    _menuView.backgroundColor = [UIColor clearColor];
//    [self addSubview:_menuView];
//    
////    UIView *line = [[UIView alloc] initWithFrame:CGRectMake(CGRectGetMaxX(_menuView.frame), 10, 1, 20)];
////    line.backgroundColor = [UIColor grayColor];
////    [self addSubview:line];
////    
////    UIButton *button = [UIButton buttonWithType:UIButtonTypeSystem];
////    button.frame = CGRectMake(CGRectGetMaxX(line.frame), 0, button_W, menuView_H);
////    [button setTitle:@"V" forState:UIControlStateNormal];
////    [button addTarget:self action:@selector(buttonAction) forControlEvents:UIControlEventTouchUpInside];
////    [self addSubview:button];
////    
////    
////    
    //bgScrollView
    NSInteger numerViews = [self.dataSoure numberOfViewsInTopScrollMenuView:self];
    if (numerViews != self.titles.count) {
        NSLog(@"TopScrollMenuView error:  views count != titles count !");
        return;
    }
    
    _bgScrollView = [[UIScrollView alloc] init];
    //_bgScrollView.frame = CGRectMake(0, menuView_H + 1, _view_W, _view_H);
    _bgScrollView.contentSize = CGSizeMake(self.frame.size.width * numerViews, 0);
    _bgScrollView.backgroundColor = [UIColor clearColor];
    _bgScrollView.pagingEnabled = YES;                      // 开启分页
    _bgScrollView.bounces = NO;                             // 没有弹簧效果
    _bgScrollView.showsHorizontalScrollIndicator = NO;      // 隐藏水平滚动条
    _bgScrollView.delegate = self;                          // 设置代理
    _bgScrollView.translatesAutoresizingMaskIntoConstraints = false;
    
    
    
//    [self addConstraint:<#(nonnull NSLayoutConstraint *)#>];
//    
//    navBarView.isHidden = !isShowNavBar();
//    self.view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[navBarView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//    self.view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[navBarView]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//    self.view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[navBarView(64)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
    
    for (NSInteger i = 0 ; i < numerViews; i++ ) {
        UIView *view = [self.dataSoure TopScrollMenuView:self viewAtIndex:i];
        view.frame = CGRectMake(i*_view_W, 0, _view_W, _view_H);
        [_bgScrollView addSubview:view];
        [self.viewData addObject:view];
    }
    
    [self addSubview:_bgScrollView];

}

#pragma mark - - - menuView Left Button
//- (void)buttonAction {
//    if ([self.delegate respondsToSelector:@selector(selectButtonForTopScrollMenuView:)]) {
//        [self.delegate selectButtonForTopScrollMenuView:self];
//    }
//}


#pragma mark - - - SGTopScrollMenu代理方法

- (void)SGTopScrollMenu:(SGTopScrollMenu *)topScrollMenu didSelectTitleAtIndex:(NSInteger)index {
    // 1 计算滚动的位置
    CGFloat offsetX = index * _view_W;
    _bgScrollView.contentOffset = CGPointMake(offsetX, 0);
    if ([self.delegate respondsToSelector:@selector(topScrollMenuView:didSelectView:index:)]) {
        [self.delegate topScrollMenuView:self didSelectView:self.viewData[index] index:index];
    }
}

#pragma mark - UIScrollViewDelegate
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{
    
    // 1.计算滚动到哪一页
    NSInteger index = scrollView.contentOffset.x / scrollView.frame.size.width;
   
    // 2.把对应的标题选中
    UILabel *selLabel = _menuView.allTitleLabel[index];
   
    // 3.让选中的标题居中
    if (self.titles.count > 4) {
        [_menuView scrollTitleLabelSelecteded:selLabel];
        [_menuView scrollTitleLabelSelectededCenter:selLabel];
    } else {
        [_menuView staticTitleLabelSelecteded:selLabel];

    }
    
    if ([self.delegate respondsToSelector:@selector(topScrollMenuView:didSelectView:index:)]) {
        [self.delegate topScrollMenuView:self didSelectView:self.viewData[index] index:index];
    }
}


@end
