//
//  HYTextAttachment.swift
//  AskDog
//
//  Created by Symond on 16/8/19.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYTextAttachment: NSTextAttachment {
    
    
    
    //MARK:NSTextAttachmentContainer
    override func attachmentBounds(for textContainer: NSTextContainer?, proposedLineFragment lineFrag: CGRect, glyphPosition position: CGPoint, characterIndex charIndex: Int) -> CGRect {
        return CGRect.zero;
    }

}
