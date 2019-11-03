//
//  UserProvider.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import Foundation
import RxSwift

final class UserProvider: NSObject {
    
    static let instance: UserProvider = UserProvider()
    private var currentUserSubject: BehaviorSubject<User?> = BehaviorSubject(value: nil)
    var submissionTypeSubject: BehaviorSubject<SubmissionType> = BehaviorSubject(value: .requestHelp)
    // FIXME:
    
    var isAdminSubject: BehaviorSubject<Bool> = BehaviorSubject(value: false)
    
    var currentUserObservable: Observable<User?> {
        return currentUserSubject
    }
    
    override init() {
        super.init()
        loadUser()
    }
    
    func save(_ user: User) {
        UserDefaults.standard.set(user.userMap(), forKey: "storedUser")
        currentUserSubject.onNext(user)
    }
    
    func loadUser() {
        guard let userMap = UserDefaults.standard.object(forKey: "storedUser") as? [String: Any] else { return }
        guard
            let name = userMap["userName"] as? String,
            let userId = userMap["userId"] as? String,
            let type = userMap["type"] as? Int,
            let userType = UserType(rawValue: type),
            let email = userMap["email"] as? String,
            let phone = userMap["phone"] as? String else { return }
//            let lat = userMap["lat"] as? Double,
//            let lon = userMap["lon"] as? Double else { return }
        
        
        if userType == .volunteer {
            guard let helpType = userMap["offeringHelpType"] as? HelpType else { return }
            let volunteer = Volunteer(name: name, userId: userId, type: userType, email: email, phone: phone, location: nil, offeringHelpType: helpType)
            currentUserSubject.onNext(volunteer)
        }
        else {
            let user = Requestor(userId: userId, name: name, type: userType, email: email, phone: phone, location: nil)
            currentUserSubject.onNext(user)
        }
    }    
}
