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
    
    var currentUserObservable: Observable<User?> {
        return currentUserSubject
    }
    
    override init() {
        super.init()
        loadUser()
    }
    
    func save(_ user: User) {
        UserDefaults.standard.set(user.userMap(), forKey: "storedUser")
    }
    
    func loadUser() {
        guard let userMap = UserDefaults.standard.object(forKey: "storedUser") as? [String: Any] else { return }
        guard
            let userId = userMap["userId"] as? String,
            let userType = userMap["type"] as? UserType,
            let email = userMap["emial"] as? String,
            let phone = userMap["phone"] as? String,
            let lat = userMap["lat"] as? Double,
            let lon = userMap["lon"] as? Double else { return }
        
        
        if userType == .volunteer {
            guard let helpType = userMap["offeringHelpType"] as? HelpType else { return }
            let volunteer = Volunteer(userId: userId, type: userType, email: email, phone: phone, location: Location(lat: lat, lon: lon), offeringHelpType: helpType)
            currentUserSubject.onNext(volunteer)
        }
        else {
            let user = Requestor(userId: userId, type: userType, email: email, phone: phone, location: Location(lat: lat, lon: lon))
            currentUserSubject.onNext(user)
        }
    }
    
    func submit(_ user: User) {
        
    }
}
