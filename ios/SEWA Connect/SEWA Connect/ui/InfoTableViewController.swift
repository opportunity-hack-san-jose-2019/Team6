//
//  InfoTableViewController.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import UIKit
import RxSwift

enum SubmissionType {
    case requestHelp, volunteering
}

class InfoTableViewController: UITableViewController, UITextFieldDelegate {

    var selectedIndexPath: IndexPath?
    var submissionType: SubmissionType!
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var phoneTextField: UITextField!
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var additionaInfo: UITextField!

    private var disposeBag = DisposeBag()
    private var user: User?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
        
        tableView.tableFooterView = UIView()
        
        // Do any additional setup after loading the view.
        UserProvider.instance.currentUserObservable.filter({$0 != nil})
            .observeOn(MainScheduler.asyncInstance)
            .subscribe(onNext: { [weak self] (user) in
                if user?.type == UserType.helpSeeker && self?.submissionType == SubmissionType.requestHelp {
                    self?.emailTextField.text = user?.email
                    self?.phoneTextField.text = user?.phone
                    self?.nameTextField.text = user?.name
                }
            })
            .disposed(by: disposeBag)
        
        UserProvider.instance.submissionTypeSubject
            .subscribe(onNext: { [weak self] (type) in
                self?.submissionType = type
            })
        .disposed(by: disposeBag)

    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return 3
        default:
            return 6
        }
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard indexPath != IndexPath(row: 5, section: 1) else { return }
        if let previouslySelectedIndexPath = selectedIndexPath {
            let previouslySelectedCell = tableView.cellForRow(at: previouslySelectedIndexPath)
            previouslySelectedCell?.accessoryType = .none
        }
        let cell = tableView.cellForRow(at: indexPath)
        cell?.accessoryType = .checkmark
        tableView.deselectRow(at: indexPath, animated: true)
        selectedIndexPath = indexPath
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        let nextTag = textField.tag + 1
        if let nextResponder = tableView.viewWithTag(nextTag) {
            nextResponder.becomeFirstResponder()
            tableView.scrollToRow(at: IndexPath(row: nextTag, section: 1), at: .bottom, animated: true)
        } else {
            textField.resignFirstResponder()
        }
        return true
    }
    
    @IBAction func submit(_ sender: Any) {
        guard
            let index = selectedIndexPath?.item,
            let helpType = HelpType(rawValue: index),
            let email = emailTextField.text,
            let phone = phoneTextField.text,
            let name = nameTextField.text,
            let additionalInfo = additionaInfo.text else { return }

        if submissionType == SubmissionType.requestHelp {
            let requestor = Requestor(userId: UIDevice.current.identifierForVendor!.uuidString, name: name, type: .helpSeeker, email: email, phone: phone, location: nil)
            let now = Date().timeIntervalSince1970
            let request = Request(requestingHelpType: helpType, requestor: requestor, additionInfo: additionalInfo, createdAtUTC: now, lastModifiedAt: now)
            RemoteDataProvider.instance.post(request)
            self.dismiss(animated: true, completion: nil)
        }
        else {
            let volunteer = Volunteer(name: name, userId: UIDevice.current.identifierForVendor!.uuidString, type: .volunteer, email: email, phone: phone, location: nil, offeringHelpType: helpType)
            RemoteDataProvider.instance.post(volunteer)
            self.dismiss(animated: true, completion: nil)
        }
    }
    

    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
