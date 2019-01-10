//
//  HYMusicFileManager.swift
//  AskDog
//
//  Created by Symond on 16/9/23.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import Alamofire

class HYMusicFileManager: NSObject {
    
    //Document目录下建music
    
    let fileManager = FileManager.default;
    let userDefault = UserDefaults(suiteName: "HYMusicFileManager");
    //let docDirs:String = (NSHomeDirectory() + "/Documents/music");
    var docDirs:String = "";
    var beWork:Bool = false;
    
    static let sharedInstance = HYMusicFileManager();
    fileprivate override init(){
        super.init();
        beWork = self.initMusicDir();
        log.ln("music manager is work = \(beWork)")/;
    };
    
    
    fileprivate func initMusicDir() -> Bool{
        
        let dirs:[String] = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true);
        if(dirs.count > 0){
            docDirs = dirs[0] + "/music";
            //print("\(docDirs)");
            log.ln("music dir = \(docDirs)")/;
            
            var isDirectory: ObjCBool = false
            if(true == fileManager.fileExists(atPath: docDirs, isDirectory: &isDirectory)){
                if(false == isDirectory.boolValue){
                    //删除重建文件夹
                    self.deleteDir(dirPath: docDirs);
                    return self.createDir(dirPath: docDirs);
                }else{
                    //是文件夹
                    return true;
                }
            }else{
                //新建文件夹
                return self.createDir(dirPath: docDirs);
            }
        }
        return false;
        
    }
    
    
    /// 把音乐文件信息写入plist中
    ///
    /// - parameter url:  url做key
    /// - parameter path: path做value
    ///
    /// - returns: 成功 true  失败 false
    fileprivate func recordMusicFileInfo(fileUrl urlKey:String,filePath path:URL) -> Void{

        
        userDefault?.set(path, forKey: urlKey);
        userDefault?.synchronize();

    }
    
    func saveMusic(fileName name:String!,fileData data:Data) -> Bool{
        if(false == self.beWork){
            log.ln("music file manager doen not working")/;
            return false;
        }
        
        let savePath = "\(docDirs)/\(name)";
        
        let beCreate = fileManager.createFile(atPath: savePath, contents: data, attributes: nil);
        
        log.ln("music file create \(beCreate) at \(savePath).")/;
        
        
        return beCreate;
    }
    
    fileprivate func createDir(dirPath dir:String!) -> Bool {
        
        do{
            try fileManager.createDirectory(atPath: dir, withIntermediateDirectories: true, attributes: nil);
            return true;
        }catch let err{
            log.error(err)/;
            return false;
        }
    }
    
    fileprivate func deleteDir(dirPath dir:String!) -> Void {
        if(true == fileManager.isDeletableFile(atPath: dir)){
            do{
                try fileManager.removeItem(atPath: dir);
            }catch let err{
                log.error(err)/;
            }
            
        }
    }
    
    
    /// 通过网络URL做为KEY 取本地缓存的音乐文件路径
    ///
    /// - parameter url: 网络URLKEY
    ///
    /// - returns: 本地音乐路径
    func getLocalFilePath(musicUrl url:String) -> String {
        if(false == self.beWork){
            log.ln("music file manager doen not working")/;
            
            return "";
        }
        
        if let u:URL = userDefault?.url(forKey: url){
            return u.path;
        }else{
            return "";
        }
    }
    
    /// 查找指定URL的音乐是否已经缓存
    ///
    /// - parameter url: 要查找的音乐的URL
    ///
    /// - returns: 缓存 true  未缓存 false
    func ifFileHasCached(musicUrl url:String) -> Bool {
        //先看PLIST中有没有
        
        if let u:URL = userDefault?.url(forKey: url){
            //如果查找到 就看实际文件还在不
//            let path:String = u.absoluteString;
//            log.ln("\(u.relativeString)")/;
//            log.ln("\(u.relativePath)")/;
//            log.ln("\(u.path)")/;
            
            var isDirectory: ObjCBool = false
            if(true == fileManager.fileExists(atPath: u.path, isDirectory: &isDirectory)){
                if(true == isDirectory.boolValue){
                    return false;
                }else{
                    return true;
                }
            }else{
                //说明不存在  删除PLIST中的KEY
                userDefault?.removeObject(forKey: url);
            }
        }
        return false;

    }
    
    func downloadMusic(musicUrl url:String,
                       fileName name:String,
                       progressCallBack progressFun:@escaping ((String) -> Void),
                       resultCallBack resultFun:@escaping ((Bool,URL)->Void)) -> Void {
        
        if(false == self.beWork){
            log.ln("music file manager doen not working")/;
            resultFun(false,URL(string: "")!);
            return;
        }
        
        let destination : DownloadRequest.DownloadFileDestination = { _, _ in
            
            let tempURL = URL(string: url);
            
            var exName:String = "";
            if let ex = tempURL?.pathExtension{
                exName = ".\(ex)";
            }
            
            let savePath = "\(self.docDirs)/\(name)\(exName)";
            
            
            log.ln("savePath = \(savePath)")/;
            let fileURL = URL(fileURLWithPath: savePath);
            log.ln("fileURL = \(fileURL)")/;
            return (fileURL, [.removePreviousFile, .createIntermediateDirectories])
        }
        
        Alamofire.download(url,to:destination).downloadProgress { (prog) in
            progressFun(prog.localizedDescription);
            }.responseData { (response) in
                
                switch response.result{
                    
                case .success( _):
                    //_ = self.saveMusic(fileName: name, fileData: value);
                    if let final:URL = response.destinationURL{
                        self.recordMusicFileInfo(fileUrl: url, filePath: final);
                        resultFun(true,final);
                    }
                    break;
                case .failure(let err):
                    log.error(err)/;
                    resultFun(false,URL(string: "")!);
                    break;
                    
                }

//                if(true ==  response.result.isSuccess){
//                    if let data = response.result.value{
//                        _ = self.saveMusic(fileName: name, fileData: data);
//                    }
//                }
                

        }
        
    }
    
//    func downloadMusic(musicUrl url:String!,
//                       fileName name:String!,
//                       progressCallBack progressFun:((String) -> Void),
//                       resultCallBack resultFun:((Bool)->Void)) -> Void {
//        
//        let destination : DownloadRequest.DownloadFileDestination = { _, _ in
//            
//            let savePath = "\(self.docDirs)/\(name)";
//            let fileURL = URL(string: savePath);
//
//            return (fileURL!, [.removePreviousFile, .createIntermediateDirectories])
//        }
//        
//        Alamofire.download(url, to: destination).downloadProgress { (prog) in
//            
//        }.response { (resp) in
//            resp.re
//        }
//        
//    }

}


//NSArray *subpaths;
//BOOL isDir;
//
//NSArray *paths = NSSearchPathForDirectoriesInDomains
//(NSLibraryDirectory, NSUserDomainMask, YES);
//
//if ([paths count] == 1) {
//    
//    NSFileManager *fileManager = [[NSFileManager alloc] init];
//    NSString *fontPath = [[paths objectAtIndex:0] stringByAppendingPathComponent:@"Fonts"];
//    
//    if ([fileManager fileExistsAtPath:fontPath isDirectory:&isDir] && isDir) {
//        subpaths = [fileManager subpathsAtPath:fontPath];
