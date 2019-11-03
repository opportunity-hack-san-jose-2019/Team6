//
//  FTUEViewController.swift
//  SEWA Connect
//
//  Created by XIN on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

class FTUEViewController: UIViewController {

    @IBOutlet weak var lookingForHelpButton: UIButton!
    @IBOutlet weak var volunteeringButton: UIButton!
    @IBOutlet weak var adminButton: UIButton!
    
    private var disposeBag = DisposeBag()
    
    var selectedType: SubmissionType!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        registerObservers()
    }
    
    private func registerObservers() {
        lookingForHelpButton.rx.tap
            .subscribe(onNext: { [weak self] (_) in
                self?.performSegue(withIdentifier: "submitInfo", sender: self)
                self?.selectedType = .requestHelp
            })
        .disposed(by: disposeBag)
        
        volunteeringButton.rx.tap
            .subscribe(onNext: { [weak self] (_) in
                self?.performSegue(withIdentifier: "submitInfo", sender: self)
                self?.selectedType = .volunteering
            })
        .disposed(by: disposeBag)

        UserProvider.instance.currentUserObservable.filter({$0 != nil})
            .observeOn(MainScheduler.asyncInstance)
            .subscribe(onNext: { [weak self] (_) in
                self?.navigationController?.dismiss(animated: true, completion: nil)
            })
        .disposed(by: disposeBag)
    }
}
