//
//  LoaderView.swift
//  Animations
//
//  Created by Andrea Garcìa on 14/02/23.
//

import UIKit
import Lottie

public class LoaderView: UIView {
    
    var animationView: LottieAnimationView?
    private var animationName: String?
    
    required init(animation: String) {
        self.animationName = animation
        super.init(frame: UIScreen.main.bounds)
        commonInit(name: animationName ?? "fish")
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        commonInit(name: animationName ?? "fish")
    }
    
    
    func commonInit(name: String) {
        animationView = .init(name: name)
        animationView!.frame = CGRect(x: 0, y: 0, width: 400, height: 400)
        animationView!.contentMode = .scaleAspectFit
        animationView!.loopMode = .loop
        animationView!.animationSpeed = 0.5
        self.addSubview(animationView!)
        animationView!.play()
        animationView?.translatesAutoresizingMaskIntoConstraints = false
        animationView?.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
        animationView?.centerYAnchor.constraint(equalTo: centerYAnchor).isActive = true
        animationView?.widthAnchor.constraint(equalToConstant: 280).isActive = true
        animationView?.heightAnchor.constraint(equalToConstant: 108).isActive = true
    }
}
