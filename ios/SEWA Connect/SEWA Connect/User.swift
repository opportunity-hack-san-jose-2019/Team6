//
//  User.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import Foundation

enum UserType {
    case helpSeeker, volunteer, admin
}

struct User {
    let type: UserType
    let email: String?
    let phone: String
    let address: String?
    
}
