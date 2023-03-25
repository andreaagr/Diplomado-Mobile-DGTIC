//
//  HeartButton.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import UIKit

class HeartButton: UIButton {

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */
    private var isLiked = false
    private let unlikedImage = UIImage(named: "FavoriteButtonSelected")
    private let likedImage = UIImage(named: "FavoriteButtonNotSelected")

    override public init(frame: CGRect) {
        super.init(frame: frame)

        setImage(unlikedImage, for: .normal)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    public func flipLikedState() {
        isLiked = !isLiked
        animate()
    }

    private func animate() {
        // Step 1
        UIView.animate(withDuration: 0.1, animations: {
            let newImage = self.isLiked ? self.likedImage : self.unlikedImage
            self.transform = self.transform.scaledBy(x: 0.8, y: 0.8)
            self.setImage(newImage, for: .normal)
        }, completion: { _ in
            // Step 2
            UIView.animate(withDuration: 0.1, animations: {
                self.transform = CGAffineTransform.identity
            })
        })
    }
}
