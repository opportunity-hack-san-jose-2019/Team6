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
    
    private var loadedRequests = [Request]()
    private var loadedRequestsSubject: BehaviorSubject<[Request]> = BehaviorSubject(value: [])
    private var loadedVolunteersSubject: BehaviorSubject<[Volunteer]> = BehaviorSubject(value: [])
    
    var loadedRequestsObservable: Observable<[Request]> {
        return loadedRequestsSubject
    }
    
    var loadedVolunteersObservable: Observable<[Volunteer]> {
        return loadedVolunteersSubject
    }

    override init() {
        super.init()
        requestsDocRef = db.collection("requests")
        volunteersDocRef = db.collection("volunteers")
    }
    
    func getRequests() {
        requestsDocRef.getDocuments { [weak self] (snapShot, error) in
            guard let snapShot = snapShot  else { return }
            let documents = snapShot.documents
            self?.process(documents)
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
        volunteersDocRef.addDocument(data: volunteer.userMap()) { (error) in
            if let error = error {
                print(error)
            }
            else {
                DispatchQueue.main.async {
                    UserProvider.instance.save(volunteer)
                }
            }
        }
    }
    
    private func process(_ requestDocs: [QueryDocumentSnapshot]) {
        for doc in requestDocs {
            let data = doc.data()
            if
                let info = data["additionInfo"] as? String,
                let createdAt = data["createdAtUTC"] as? TimeInterval,
                let helpTypeNum = data["helpType"] as? Int,
                let helpType = HelpType(rawValue: helpTypeNum),
                let lastModifiedAt = data["lastModifiedAt"] as? TimeInterval,
                let statsNum = data["status"] as? Int,
                let status = Status(rawValue: statsNum),
                let user = data["user"] as? [String: Any],
                let userId = user["userId"] as? String,
                let userName = user["userName"] as? String,
                let email = user["email"] as? String,
                let phone = user["phone"] as? String {
                let requestor = Requestor(userId: userId, name: userName, type: .helpSeeker, email: email, phone: phone, location: nil)
                var request = Request(requestingHelpType: helpType, requestor: requestor, additionInfo: info, createdAtUTC: createdAt, lastModifiedAt: lastModifiedAt)
                request.status = status
                loadedRequests.append(request)
            }
            else {
                continue
            }
        }
        loadedRequestsSubject.onNext(loadedRequests)
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
