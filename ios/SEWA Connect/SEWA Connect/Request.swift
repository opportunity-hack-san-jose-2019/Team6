//
//  Request.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//


enum HelpType: Int {
    case disasterRecovery = 0
    case familyServices
    case tutoring
}

struct Request {
    let requestingHelpType: HelpType
    let requestor: User
    let additionInfo: String?
}
