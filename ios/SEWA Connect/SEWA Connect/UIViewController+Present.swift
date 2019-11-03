//
//  UIViewController+Present.swift
//  SEWA Connect
//
//  Created by Jiahe Kuang on 11/2/19.
//  Copyright © 2019 JHK_Development. All rights reserved.
//

import UIKit

extension UIViewController {
    func presentRootSplitView() {
        let rootSplitVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "rootSplitContainer")
        self.present(rootSplitVC, animated: false, completion: nil)
    }
    
    func presentFTUE() {
        let ftueVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "FTUEViewController")
        ftueVC.modalPresentationStyle = .fullScreen
        self.present(ftueVC, animated: false, completion: nil)

    }
}
