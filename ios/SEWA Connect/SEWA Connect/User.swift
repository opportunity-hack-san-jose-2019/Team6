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

protocol User {
    var userId: String { get set }
    var type: UserType { get set }
    var email: String { get set }
    var phone: String { get set }
    var location: Location? { get set }
    
    func userMap() -> [String: Any]
}

struct Volunteer: User {

    func userMap() -> [String : Any] {
        return ["userId": userId,
                "type":  type,
                "offeringHelpType": offeringHelpType,
                "email": email,
                "phone": phone,
                "lat": location?.lat ?? "",
                "lon": location?.lon ?? ""] as [String: Any]
    }
        
    var userId: String
    
    var type: UserType
    
    var email: String
    
    var phone: String
    
    var location: Location?
    
    var offeringHelpType: HelpType

}

struct Requestor: User {
    
    func userMap() -> [String : Any] {
        return ["userId": userId,
                "type":  type,
                "email": email,
                "phone": phone,
                "lat": location?.lat ?? "",
                "lon": location?.lon ?? ""] as [String: Any]
    }
        
    var userId: String
    
    var type: UserType
    
    var email: String
    
    var phone: String
    
    var location: Location?
}
