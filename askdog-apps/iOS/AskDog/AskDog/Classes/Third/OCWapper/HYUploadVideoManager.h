//
//  HYUploadVideoManager.h
//  AskDog
//
//  Created by Symond on 16/8/23.
//  Copyright © 2016年 Hooying. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <VODUpload/VODUploadClient.h>
#import <VODUpload/VODUpload.h>

@interface HYUploadVideoManager : NSObject
{
    VODUploadClient *_upload;
}


+ (HYUploadVideoManager *)sharedManager;


- (void)uploadVideoUsingAliyunOSS:(NSString *)accessKeyId
                  accessKeySecret:(NSString *)accessKeySecret
                      secretToken:(NSString *)secretToken
                       expireTime:(NSString *)expireTime
                       bucketName:(NSString *)bucketName
                         endpoint:(NSString *)endpoint
                   uploadFilePath:(NSString *)filePath
                    uploadFileKey:(NSString *)fileKey
                  onUploadSucceed:(OnUploadSucceedListener)onSuccee
                   onUploadFailed:(OnUploadFailedListener)onFailed
                 onUploadProgress:(OnUploadProgressListener)onProgress
             onUploadTokenExpired:(OnUploadTokenExpiredListener)onTokenExpired;

- (void)stopUpload;

/**
 删除视频上传
 */
- (BOOL)deleteFile:(NSString *)filePath;

@end
