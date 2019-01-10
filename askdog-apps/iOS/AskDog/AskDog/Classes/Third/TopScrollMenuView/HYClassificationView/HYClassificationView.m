//
//  HYClassificationView.m
//  textMenu
//
//  Created by Hooying_zhaody on 16/9/19.
//  Copyright © 2016年 Hooying_zhaody. All rights reserved.
//

#import "HYClassificationView.h"

#define item_H          25
#define font_Size       14

@interface HYClassificationView()<UICollectionViewDataSource, UICollectionViewDelegateFlowLayout>
@property (nonatomic, strong) UICollectionView * collectionView;
@property (nonatomic, strong) NSMutableArray * dataArr;

@end

@implementation HYClassificationView
{
    NSInteger _index;
}
/**
 *  初始化方法
 *
 *  @param frame 流布局frame
 *  @param items 外部导入的数据源
 *  @param click item点击响应回调block
 *
 *  @return 自定义流布局对象
 */
-(id)initWithFrame:(CGRect)frame andItems:(NSArray *)items index:(NSInteger)index andItemClickBlock:(itemClickBlock)click{
    
    if (self == [super initWithFrame:frame]) {
        _index = index;
        _dataArr = [NSMutableArray arrayWithArray:items];
        _itemClick = click;
        self.userInteractionEnabled = YES;
        [self configBaseView];
    }
    return self;
}

/**
 *  搭建基本视图
 */
- (void)configBaseView{
    
    self.backgroundColor = [UIColor whiteColor];
    
    UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
    layout.minimumLineSpacing = 5;
    layout.minimumInteritemSpacing = 5;
   
    CGRect frame = self.frame;
    frame.origin.x = 10;
    frame.size.width -= 20;
    self.collectionView = [[UICollectionView alloc] initWithFrame:frame collectionViewLayout:layout];
    self.collectionView.dataSource = self;
    self.collectionView.delegate = self;
    self.collectionView.bounces = NO; 
    self.collectionView.backgroundColor = [UIColor whiteColor];
    [self addSubview:self.collectionView];
    
    [self.collectionView registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:@"cell"];
}


#pragma mark -------------> UICollectionView协议方法

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    if (self.dataArr.count > 10) {
        return 10;
    }
    return self.dataArr.count;
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
    CGSize size = CGSizeMake((self.bounds.size.width - 40) /3, item_H);
    return size;
}

- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout insetForSectionAtIndex:(NSInteger)section{
    return UIEdgeInsetsMake(3, 5, 0, 0);
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    UICollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    cell.backgroundColor = [UIColor clearColor];
    cell.contentView.backgroundColor = [UIColor clearColor];
    for (UIView *view in cell.contentView.subviews) {
        [view removeFromSuperview];
    }
   
    CGFloat w = (self.bounds.size.width - 40) /3 -20;

    UILabel *label  = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, w, item_H)];
    label.text  = self.dataArr[indexPath.row];
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont systemFontOfSize:font_Size];
    
    cell.contentView.layer.cornerRadius = item_H/2;
    cell.contentView.layer.borderWidth = 1.0;
    cell.contentView.clipsToBounds = YES;
    [cell.contentView addSubview:label];
    label.center  = cell.contentView.center;

    if (indexPath.row == _index) {
        label.textColor = [UIColor colorWithRed:0/255.0 green:170/255.0 blue:239/255.0 alpha:1.0];
        cell.contentView.layer.borderColor = [UIColor colorWithRed:0/255 green:170.0/255 blue:239.0/255 alpha:1.0].CGColor;
    } else {
        label.textColor = [UIColor colorWithRed:135/255.0 green:146/255.0 blue:164/255.0 alpha:1.0];
        cell.contentView.layer.borderColor = [UIColor colorWithRed:135/255.0 green:146/255.0 blue:164/255.0 alpha:1.0].CGColor;
    }

    return cell;
    
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    /* 响应回调block */
    _index = indexPath.row;
    self.itemClick(indexPath.row);
    [self.collectionView reloadData];
}

@end
