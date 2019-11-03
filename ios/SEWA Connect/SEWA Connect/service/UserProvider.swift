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
        
    }
    
    func loadUser() {
        
    }
    
    func submit(_ user: User) {
        
    }
}
