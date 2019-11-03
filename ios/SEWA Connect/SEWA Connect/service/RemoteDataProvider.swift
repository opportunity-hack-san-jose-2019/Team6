//
//  RemoteDataProvider.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import Foundation
import Firebase
import FirebaseFirestore
import RxSwift

final class RemoteDataProvider: NSObject {

    static let instance: RemoteDataProvider = RemoteDataProvider()
    
    private var db: Firestore = Firestore.firestore()

    private var requestsDocRef: CollectionReference!
    private var volunteersDocRef: CollectionReference!
    private var requestsListeners: [ListenerRegistration] = []
    
    private var loadedRequestsSubject: BehaviorSubject<[Request]> = BehaviorSubject(value: [])
    private var loadedVolunteersSubject: BehaviorSubject<[Volunteer]> = BehaviorSubject(value: [])

    override init() {
        super.init()
        requestsDocRef = db.collection("requests")
        volunteersDocRef = db.collection("volunteers")
    }
    
    func getRequests() {
        requestsDocRef.getDocuments { [weak self] (snapShot, error) in
            guard let snapShot = snapShot  else { return }
            let documents = snapShot.documents
            self?.setupListens(from: documents)
        }
    }
    
    func post(_ request: Request) {
        let requestMap = request.requestMap()
        requestsDocRef.addDocument(data: requestMap) { (error) in
            if let error = error {
                print(error)
            }
            else {
                DispatchQueue.main.async {
                    UserProvider.instance.save(request.requestor)
                }
            }
        }
    }
    
    func post(_ volunteer: Volunteer) {
//        volunteersDocRef.addDocument(data: volunteer.userMap())
    }
    
    private func setupListens(from docs: [QueryDocumentSnapshot]) {
        for doc in docs {
            db.collection("help_request").document(doc.documentID)
            .addSnapshotListener { documentSnapshot, error in
                guard let document = documentSnapshot else {
                    print("Error fetching document: \(error!)")
                    return
                }
                let source = document.metadata.hasPendingWrites ? "Local" : "Server"
                print("\(source) data: \(document.data() ?? [:])")
            }
        }
    }
}
