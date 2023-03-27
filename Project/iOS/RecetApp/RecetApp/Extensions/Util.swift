//
//  Util.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 26/03/23.
//

import Foundation
import Lottie
import UIKit

func createCustomAlert(animationName: String, title: String?, message: String?, dismissable: Bool = true) -> UIAlertController {
    
    var animationView: LottieAnimationView?
    
    animationView = .init(name: animationName)
    animationView!.contentMode = .scaleAspectFit
    animationView!.loopMode = .loop
    animationView!.animationSpeed = 0.5
    animationView!.play()
    let alert = UIAlertController(
        title: title,
        message: message,
        preferredStyle: .alert
    )
    alert.view.addSubview(animationView!)
    let height = NSLayoutConstraint(item: alert.view!, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: 300)
    alert.view.addConstraint(height)
    if dismissable {
        alert.addAction(UIAlertAction(title: "Ok", style: .default))
    }
    animationView!.translatesAutoresizingMaskIntoConstraints = false
    animationView?.widthAnchor.constraint(equalToConstant: 150).isActive = true
    animationView?.heightAnchor.constraint(equalToConstant: 150).isActive = true
    animationView!.centerXAnchor.constraint(equalTo: alert.view.centerXAnchor).isActive = true
    animationView!.centerYAnchor.constraint(equalTo: alert.view.centerYAnchor).isActive = true
    return alert
}

func createActivityIndicator(frame: CGRect? = nil, center: CGPoint? = nil) -> UIActivityIndicatorView {
    
    let activityIndicatorView = UIActivityIndicatorView()
    
    if #available(iOS 13.0, *) {
        activityIndicatorView.style = .large
    } else {
        activityIndicatorView.style = .whiteLarge
    }
    
    if let frame = frame {
        activityIndicatorView.frame = frame
    }
    
    if let center = center {
        activityIndicatorView.center = center
    }
    
    return activityIndicatorView
}
