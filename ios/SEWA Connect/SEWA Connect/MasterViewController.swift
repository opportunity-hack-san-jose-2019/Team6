//
//  MasterViewController.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import UIKit
import RxSwift

class MasterViewController: UITableViewController {

    var detailViewController: DetailViewController? = nil
    var requets = [Request]()
    var disposeBag = DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let split = splitViewController {
            let controllers = split.viewControllers
            detailViewController = (controllers[controllers.count-1] as! UINavigationController).topViewController as? DetailViewController
        }
        RemoteDataProvider.instance.getRequests()
        registerObservers()
    }
    
    private func registerObservers() {
        Observable.zip(RemoteDataProvider.instance.loadedRequestsObservable.filter({$0.count != 0}), UserProvider.instance.currentUserObservable.filter({$0 != nil}))
            .subscribe(onNext: { [weak self] (combined) in
                if let user = combined.1, user.type == .helpSeeker {
                    var requestorRequests = [Request]()
                    for request in combined.0 {
                        if request.requestor.userId == user.userId {
                            requestorRequests.append(request)
                        }
                    }
                    self?.requets = requestorRequests
                    self?.tableView.reloadData()
                }
                else {
                    self?.requets = combined.0
                    self?.tableView.reloadData()
                }
            })
        .disposed(by: disposeBag)
    }
    
    @IBAction func ftue(_ sender: Any) {
        presentFTUE()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        clearsSelectionOnViewWillAppear = splitViewController!.isCollapsed
        super.viewWillAppear(animated)
    }

    // MARK: - Segues

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetail" {
            if let indexPath = tableView.indexPathForSelectedRow {
//                let object = objects[indexPath.row] as! NSDate
//                let controller = (segue.destination as! UINavigationController).topViewController as! DetailViewController
//                controller.detailItem = object
//                controller.navigationItem.leftBarButtonItem = splitViewController?.displayModeButtonItem
//                controller.navigationItem.leftItemsSupplementBackButton = true
//                detailViewController = controller
            }
        }
    }

    // MARK: - Table View

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return requets.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "requestCell", for: indexPath) as! RequestTableViewCell
        let request = requets[indexPath.item]
        cell.requestTypeLabel.text = request.requestingHelpType.typeName
        cell.requestSummary.text = "Created by \(request.requestor.name) on \(Date(timeIntervalSince1970: request.createdAtUTC))"
        return cell
    }

    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }

}

