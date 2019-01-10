//
//  HYSafeCenterListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYSafeCenterListTableViewCell: UITableViewCell {

    @IBOutlet weak var lblLine: UILabel!
    @IBOutlet weak var imgViewArrow: UIImageView!
    @IBOutlet weak var lblContent: UILabel!
    @IBOutlet weak var lblTitle: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
