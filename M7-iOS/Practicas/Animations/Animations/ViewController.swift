//
//  ViewController.swift
//  Animations
//
//  Created by Andrea Garcìa on 12/02/23.
//

import UIKit
import Lottie

private var animationView: LottieAnimationView?

class ViewController: UIViewController {
    
    var animation = LoaderView(animation: "computer")

    override func viewDidLoad() {
        super.viewDidLoad()
        animation.frame.size = CGSize(width: 600, height: 600)
        animation.center = self.view.center
        self.view.addSubview(animation)
        self.view.backgroundColor = .purple
        let segmentedControl = UISegmentedControl (items: ["Anim1","Anim2","Anim3"])
        segmentedControl.frame = CGRect(x: 10, y: 40, width:self.view.bounds.width - 20, height: 30)
        segmentedControl.selectedSegmentIndex = 0
        segmentedControl.addTarget(self, action: #selector(segmentedValueChanged(_:)), for: .valueChanged)
        self.view.addSubview(segmentedControl)
    }
    
    @objc func segmentedValueChanged(_ sender: UISegmentedControl) {
        switch sender.selectedSegmentIndex {
        case 0:
            changeImage(name: "computer")
            self.view.backgroundColor = .purple
            break
        case 1:
            changeImage(name: "world")
            self.view.backgroundColor = .blue
            break
        case 2:
            changeImage(name: "coffe")
            self.view.backgroundColor = .red
            break
        default:
            view.backgroundColor = .cyan
            break
        }
    }
    
    func changeImage(name: String) {
        animation.animationView?.pause()
        animation.animationView?.animation = LottieAnimation.named(name)
        animation.animationView?.play()
    }
}

