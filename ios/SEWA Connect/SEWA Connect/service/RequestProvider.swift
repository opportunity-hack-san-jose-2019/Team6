//
//  RequestProvider.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import Foundation
import RxSwift

final class RequestProvider: NSObject {
    
    static let instance: RequestProvider = RequestProvider()
    private var allRequestsSubject: BehaviorSubject<[Request]> = BehaviorSubject(value: [])
    
    var allRequestsObservable: Observable<[Request]> {
        return allRequestsSubject
    }
    
    override init() {
        super.init()
    }

    func submit(_ request: Request) {
        
    }
    
    func getRequest() {
        
    }
}
