//
//  InfoSubmissionViewController.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import UIKit
import RxSwift

class InfoSubmissionViewController: UIViewController {

    var type: SubmissionType!
    private var disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let segueID = segue.identifier else { return }
        switch segueID {
        case "infoSubmissionTableView":
            guard let cv = segue.destination as? InfoTableViewController else { return }
            cv.submissionType = type
        default:
            break
        }
    }

    
}
