//
//  CVSUploadClient.h
//  CVSUpload
//
//  Created by Leigang on 16/3/28.
//  Copyright © 2016年 Leigang. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "VODUpload.h"
#import "UploadFileInfo.h"

@interface VODUploadClient : NSObject<VODUpload>

/**
 配置上传
 */
- (BOOL)init      :(NSString *)accessKeyId
   accessKeySecret:(NSString *)accessKeySecret
       secretToken:(NSString *)secretToken
        expireTime:(NSString *)expireTime
   onUploadSucceed:(OnUploadSucceedListener)onSuccee
    onUploadFailed:(OnUploadFailedListener)onFailed
  onUploadProgress:(OnUploadProgressListener)onProgress
onUploadTokenExpired:(OnUploadTokenExpiredListener)onTokenExpired;

/**
 添加视频上传
 */
- (BOOL)addFile:(NSString *)filePath
       endpoint:(NSString *)endpoint
         bucket:(NSString *)bucket
         object:(NSString *)object;

/**
 删除视频上传
 */
- (BOOL)deleteFile:(NSString *)filePath;

- (NSMutableArray<UploadFileInfo *> *)listFile;

/**
 开始上传
 */
- (void)startUpload;

/**
 取消上传
 */
- (void)stopUpload;

/**
 使用Token恢复上传
 */
- (void)resumeUploadWithToken:(NSString *)accessKeyId
            accessKeySecret:(NSString *)accessKeySecret
                secretToken:(NSString *)secretToken
                 expireTime:(NSString *)expireTime;
@end

