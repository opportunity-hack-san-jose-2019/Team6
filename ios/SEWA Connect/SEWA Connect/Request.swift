//
//  Request.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//
import Foundation

enum Status: Int {
    case open, assigned, inProgress, fulfilled
}

enum HelpType: Int {
    case disasterRecovery = 0
    case familyServices
    case tutoring
    
    var typeName: String {
        switch self {
        case .disasterRecovery:
            return "Disaster Recovery"
        case .familyServices:
            return "Family Services"
        case .tutoring:
            return "Tutoring"
        }
    }
}

struct Request {
    let requestingHelpType: HelpType
    let requestor: User
    var status: Status = .open
    let additionInfo: String?
    let createdAtUTC: TimeInterval
    let lastModifiedAt: TimeInterval?
    var assignee: User?
    
    func requestMap() -> [String: Any] {
        return ["helpType": requestingHelpType.rawValue,
                "status": status.rawValue,
                "user": requestor.userMap(),
                "assignee": assignee?.userMap() ?? [:],
                "additionInfo": additionInfo ?? "none",
                "createdAtUTC": createdAtUTC,
                "lastModifiedAt": lastModifiedAt ?? createdAtUTC]
    }
}
