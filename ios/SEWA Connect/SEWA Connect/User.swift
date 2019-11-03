//
//  User.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import Foundation

enum UserType: Int {
    case helpSeeker, volunteer, admin
}

protocol User {
    var userId: String { get set }
    var name: String { get set }
    var type: UserType { get set }
    var email: String { get set }
    var phone: String { get set }
    var location: Location? { get set }
    
    func userMap() -> [String: Any]
}

struct Volunteer: User {

    func userMap() -> [String : Any] {
        return ["userId": userId,
                "userName": name,
                "type":  type.rawValue,
                "offeringHelpType": offeringHelpType.rawValue,
                "email": email,
                "phone": phone,
                "lat": location?.lat ?? 0.0,
                "lon": location?.lon ?? 0.0] as [String: Any]
    }
     
    var name: String

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
                "userName": name,
                "type":  type.rawValue,
                "email": email,
                "phone": phone,
                "lat": location?.lat ?? 0.0,
                "lon": location?.lon ?? 0.0] as [String: Any]
    }
        
    var userId: String
    
    var name: String
    
    var type: UserType
    
    var email: String
    
    var phone: String
    
    var location: Location?
}
