//
//  UIFont+SwiftIconFont.swift
//  SwiftIconFont
//
//  Created by Sedat Ciftci on 18/03/16.
//  Copyright © 2016 Sedat Gokbek Ciftci. All rights reserved.
//

import UIKit

public enum Fonts {
    case hooyingIcon
}

public extension UIFont{

    static func iconFontOfSize(_ font: Fonts, fontSize: CGFloat) -> UIFont {
        let fontName = UIFont.getFontName(font)
        var token: Int = 0
        if (UIFont.fontNames(forFamilyName: fontName).count == 0) {
            dispatch_once(&token) {
                FontLoader.loadFont(fontName)
            }
        }
        return UIFont(name: UIFont.getFontName(font), size: fontSize)!
    }

    class func getFontName(_ font: Fonts) -> String {
        var fontName: String = ""
        switch(font) {
        case Fonts.hooyingIcon:
            fontName = "iconfont"
            break
        }
        return fontName
    }
}

public extension UIImage
{
    public static func iconToImage(_ font: Fonts, iconCode: String, imageSize: CGSize,fontSize: CGFloat) -> UIImage
    {
        let drawText = String.getIcon(font, code: iconCode)
        
        UIGraphicsBeginImageContextWithOptions(imageSize, false, 0)
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = NSTextAlignment.center
        
        drawText!.draw(in: CGRect(x: 0, y: 0, width: imageSize.width, height: imageSize.height), withAttributes: [NSFontAttributeName : UIFont.iconFontOfSize(font, fontSize: fontSize)])
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()

        return image!
    }
}

public extension String {

    public static func getIcon(_ font: Fonts, code: String) -> String? {
        switch font {
        case .hooyingIcon:
            return fontHooyingIconWithCode(code)
        }
    }
    
    public static func fontHooyingIconWithCode(_ code: String) -> String? {
        if let icon = fontHooyingIconArr[code] {
            return icon
        }
        return nil
    }
//
//    public static func fontAwesomeIconWithCode(code: String) -> String? {
//        if let icon = fontAwesomeIconArr[code] {
//            return icon
//        }
//        return nil
//    }
//
//    public static func fontOcticonWithCode(code: String) -> String? {
//        if let icon = octiconArr[code] {
//            return icon
//        }
//        return nil
//    }
//
//    public static func fontIonIconWithCode(code: String) -> String? {
//        if let icon = ioniconArr[code] {
//            return icon
//        }
//        return nil
//    }
//
//    public static func fontIconicIconWithCode(code: String) -> String? {
//        if let icon = iconicIconArr[code] {
//            return icon
//        }
//        return nil
//    }
//
//
//    public static func fontThemifyIconWithCode(code: String) -> String? {
//        if let icon = temifyIconArr[code] {
//            return icon
//        }
//        return nil
//    }
//
//    public static func fontMapIconWithCode(code: String) -> String? {
//        if let icon = mapIconArr[code] {
//            return icon
//        }
//        return nil
//    }
//
//    public static func fontMaterialIconWithCode(code: String) -> String? {
//        if let icon = materialIconArr[code] {
//            return icon
//        }
//        return nil
//    }
}

func replaceString(_ string: NSString) -> NSString {
    if string.lowercased.range(of: "-") != nil {
        return string.replacingOccurrences(of: "-", with: "_") as NSString
    }
    return string
}


func getAttributedString(_ text: NSString, fontSize: CGFloat) -> NSAttributedString {
    let textRange = NSMakeRange(0, text.length)
    let attributedString = NSMutableAttributedString(string: text as String)

    text.enumerateSubstrings(in: textRange, options: .byWords, using: {
        (substring, substringRange, _, _) in
        var splitArr = ["", ""]
        splitArr = substring!.characters.split{$0 == ":"}.map(String.init)
        if splitArr.count == 1{
            return
        }

        let fontPrefix: String  = splitArr[0].lowercased()
        var fontCode: String = splitArr[1]

        if fontCode.lowercased().range(of: "_") != nil {
            fontCode = fontCode.replacingOccurrences(of: "_", with: "-")
        }


        var fontType: Fonts = Fonts.hooyingIcon;
        var fontArr: [String: String] = ["": ""]

        if fontPrefix == "hy" {
            fontType = Fonts.hooyingIcon
            fontArr = fontHooyingIconArr
        }

        if let _ = fontArr[fontCode] {
            attributedString.replaceCharacters(in: substringRange, with: String.getIcon(fontType, code: fontCode)!)
            let newRange = NSRange(location: substringRange.location, length: 1)
            attributedString.addAttribute(NSFontAttributeName, value: UIFont.iconFontOfSize(fontType, fontSize: fontSize), range: newRange)
        }

    })

    return attributedString
}

func GetIconIndexWithSelectedIcon(_ icon: String) -> String {
    let text = icon as NSString
    let textRange = NSMakeRange(0, text.length)
    var iconIndex: String = ""
    text.enumerateSubstrings(in: textRange, options: .byWords, using: {
        (substring, substringRange, _, _) in
        var splitArr = ["", ""]
        splitArr = substring!.characters.split{$0 == ":"}.map(String.init)
        if splitArr.count == 1{
            return
        }

        var fontCode: String = splitArr[1]

        if fontCode.lowercased().range(of: "_") != nil {
            fontCode = fontCode.replacingOccurrences(of: "_", with: "-")
        }
        iconIndex = fontCode
    })

    return iconIndex
}

func GetFontTypeWithSelectedIcon(_ icon: String) -> Fonts {
    let text = icon as NSString
    let textRange = NSMakeRange(0, text.length)
    var fontType: Fonts = Fonts.hooyingIcon;

    text.enumerateSubstrings(in: textRange, options: .byWords, using: {
        (substring, substringRange, _, _) in
        var splitArr = ["", ""]
        splitArr = substring!.characters.split{$0 == ":"}.map(String.init)
        if splitArr.count == 1{
            return
        }

        let fontPrefix: String  = splitArr[0].lowercased()
        var fontCode: String = splitArr[1]

        if fontCode.lowercased().range(of: "_") != nil {
            fontCode = fontCode.replacingOccurrences(of: "_", with: "-")
        }


        if fontPrefix == "hy" {
            fontType = Fonts.hooyingIcon
        }

    })

    return fontType
}

// Extensions


public extension UILabel {
    func parseIcon() {
        let text = replaceString(self.text! as NSString)
        self.attributedText = getAttributedString(text, fontSize: self.font!.pointSize)
    }
}

public extension UITextView {
    func parseIcon() {
        let text = replaceString(self.text! as NSString)
        self.attributedText = getAttributedString(text, fontSize: self.font!.pointSize)
    }
}


public extension UITextField {
    func parseIcon() {
        let text = replaceString(self.text! as NSString)
        self.attributedText = getAttributedString(text, fontSize: self.font!.pointSize)
    }
}

public extension UIButton {
    func parseIcon() {
        let text = replaceString((self.titleLabel?.text)! as NSString)
        self.setAttributedTitle(getAttributedString(text, fontSize: (self.titleLabel?.font!.pointSize)!), for: UIControlState())
        self.setAttributedTitle(getAttributedString(text, fontSize: (self.titleLabel?.font!.pointSize)!), for: .highlighted)
    }
}

public extension UIBarButtonItem {
    func setFontIcon(_ font: Fonts, icon: String, fontSize: CGFloat){
        var textAttributes: [String: AnyObject] = [NSFontAttributeName: UIFont.iconFontOfSize(font, fontSize: fontSize)]
        let currentTextAttributes: [String: AnyObject]? = self.titleTextAttributes(for: UIControlState()) as [String : AnyObject]?

        if currentTextAttributes != nil {
            for (key, value) in currentTextAttributes! {
                if key != "NSFont" {
                    textAttributes[key] = value
                }
            }
        }
        self.setTitleTextAttributes(textAttributes, for: UIControlState())
        self.title = String.getIcon(font, code: icon)
    }
}

public extension UITabBarItem {
    func setFontIcon(_ font: Fonts, iconCode: String, imageSize: CGSize, fontSize: CGFloat) {
        self.image = UIImage.iconToImage(font, iconCode: iconCode, imageSize: imageSize, fontSize: fontSize)
    }
}
