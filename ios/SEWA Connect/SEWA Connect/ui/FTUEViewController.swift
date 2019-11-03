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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        registerObservers()
    }
    
    private func registerObservers() {
        lookingForHelpButton.rx.tap
            .subscribe(onNext: { [weak self] (_) in
                self?.performSegue(withIdentifier: "submitInfo", sender: self)
            })
        .disposed(by: disposeBag)
        
        volunteeringButton.rx.tap
            .subscribe(onNext: { [weak self] (_) in
                self?.performSegue(withIdentifier: "submitInfo", sender: self)
            })
        .disposed(by: disposeBag)

    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
