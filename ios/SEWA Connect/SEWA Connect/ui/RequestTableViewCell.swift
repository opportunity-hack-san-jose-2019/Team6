//
//  RequestTableViewCell.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/3/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import UIKit

class RequestTableViewCell: UITableViewCell {

    @IBOutlet weak var requestTypeLabel: UILabel!
    @IBOutlet weak var requestSummary: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
