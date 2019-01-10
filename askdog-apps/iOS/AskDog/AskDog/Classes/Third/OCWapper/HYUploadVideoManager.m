//
//  HYUploadVideoManager.m
//  AskDog
//
//  Created by Symond on 16/8/23.
//  Copyright © 2016年 Hooying. All rights reserved.
//

#import "HYUploadVideoManager.h"

//static NSString* const testAccessKeyId = @"mEFBShFTJwtTLFyT";
//static NSString* const testAccessKeySecret = @"Kk8kV2Rx340wuKpAzH8cgyEKIczQex";
//static NSString* const testSecretToken = @"";
//static NSString* const testExpireTime = @"";
//
//static NSString * const kendpoint = @"http://oss-cn-hangzhou.aliyuncs.com";
//static NSString * const kbucketName = @"ad-void-exp-in";
//static NSString * kfileKey = @"1.ios.mp4";
//static int fileSize = 1024000;

@implementation HYUploadVideoManager

+ (HYUploadVideoManager *)sharedManager
{
    static HYUploadVideoManager *sharedManagerInstance = nil;
    static dispatch_once_t predicate;
    dispatch_once(&predicate, ^{
        sharedManagerInstance = [[self alloc] init];
    });
    return sharedManagerInstance;
}

- (id)init
{
    if (self = [super init])
    {
        _upload = [[VODUploadClient alloc] init];
    }
    return self;
}

- (NSString *)createTempFile : (NSString * ) fileName fileSize : (int) size {
    NSString * tempFileDirectory;
    NSString * path = NSHomeDirectory();
    tempFileDirectory = [NSString stringWithFormat:@"%@/%@/%@", path, @"tmp", fileName];
    //NSLog(@"%@", tempFileDirectory);
    NSFileManager * fm = [NSFileManager defaultManager];
    
    if ([fm fileExistsAtPath:tempFileDirectory]){
        [fm removeItemAtPath:tempFileDirectory error:nil];
    }
    
    [fm createFileAtPath:tempFileDirectory contents:nil attributes:nil];
    NSFileHandle * fh = [NSFileHandle fileHandleForWritingAtPath:tempFileDirectory];
    NSMutableData * basePart = [NSMutableData dataWithCapacity:size];
    for (int i = 0; i < size/4; i++) {
        u_int32_t randomBit = arc4random();
        [basePart appendBytes:(void*)&randomBit length:4];
    }
    [fh writeData:basePart];
    [fh closeFile];
    return tempFileDirectory;
}

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
             onUploadTokenExpired:(OnUploadTokenExpiredListener)onTokenExpired
{
    
   // NSString *tempFilePath = [self createTempFile:@"temp_file_test" fileSize:fileSize*5];
    
//    // callback functions.
//    OnUploadSucceedListener testSuccessCallbackFunc = ^(NSString* filePath){
//        NSLog(@"manager: upload success!");
//    };
//    
//    OnUploadFailedListener testFailedCallbackFunc = ^(NSString* filePath, long code, NSString* message){
//        NSLog(@"manager: failed code = %li, error message = %@", code, message);
//        if(code == 1006) {
//            // user cancel, do something...
//        }
//    };
//    
//    OnUploadProgressListener testProgressCallbackFunc = ^(NSString* filePath, long uploadedSize, long totalSize) {
//        NSLog(@"manager: during progress uploadedSize : %li, totalSize : %li", uploadedSize, totalSize);
//    };
//    
//    OnUploadTokenExpiredListener testTokenExpiredCallbackFunc = ^{
//        NSLog(@"manager: token expired.");
//    };
    
    BOOL be = [_upload init:accessKeyId accessKeySecret:accessKeySecret secretToken:secretToken expireTime:expireTime onUploadSucceed:onSuccee onUploadFailed:onFailed onUploadProgress:onProgress onUploadTokenExpired:onTokenExpired];
    
//    BOOL be = [_upload init:accessKeyId accessKeySecret:accessKeySecret secretToken:secretToken expireTime:expireTime onUploadSucceed:onSuccee onUploadFailed:onFailed onUploadProgress:onProgress onUploadTokenExpired:onTokenExpired];
    
   be = [_upload addFile:filePath endpoint:endpoint bucket:bucketName object:fileKey];
    
    [_upload startUpload];
    
    NSLog(@"upload file start");
}

- (void)stopUpload{
    [_upload stopUpload];
    NSLog(@"upload file stoped");
}

- (BOOL)deleteFile:(NSString *)filePath{
    NSLog(@"delete file %@",filePath);
    return [_upload deleteFile:filePath];
}

@end
