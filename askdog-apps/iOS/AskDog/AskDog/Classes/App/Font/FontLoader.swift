//
//  FontLoader.swift
//  SwiftIconFont
//
//  Created by Sedat Ciftci on 18/03/16.
//  Copyright © 2016 Sedat Gokbek Ciftci. All rights reserved.
//

import UIKit
import CoreText

class FontLoader: NSObject {
    class func loadFont(_ fontName: String) {
        let bundle = Bundle(for: FontLoader.self)
<<<<<<< HEAD
        var fontURL:URL!
=======
        var fontURL = URL()
>>>>>>> fd3490067f65d5b7aa0823f5d949fcfbe6f2baf6
        for filePath : String in bundle.paths(forResourcesOfType: "ttf", inDirectory: nil) {
            let filename = URL(fileURLWithPath: filePath).lastPathComponent
            if filename.lowercased().range(of: fontName.lowercased()) != nil {
                fontURL = URL(fileURLWithPath: filePath)
            }
        }

        let data = try! Data(contentsOf: fontURL)
<<<<<<< HEAD
        let provider = CGDataProvider(data: data as CFData)
        let font = CGFont(provider!);
=======
        let provider = CGDataProvider(data: data)
        let font = CGFont(provider)!
>>>>>>> fd3490067f65d5b7aa0823f5d949fcfbe6f2baf6

        var error: Unmanaged<CFError>?
        if !CTFontManagerRegisterGraphicsFont(font, &error) {
            let errorDescription: CFString = CFErrorCopyDescription(error!.takeUnretainedValue())
            let nsError = error!.takeUnretainedValue() as AnyObject as! NSError
            NSException(name: NSExceptionName.internalInconsistencyException, reason: errorDescription as String, userInfo: [NSUnderlyingErrorKey: nsError]).raise()
        }
    }
}
