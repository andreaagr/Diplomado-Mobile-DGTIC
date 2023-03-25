//
//  CategoryCollectionViewCell.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 23/03/23.
//

import UIKit

class CategoryCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var categoryImageView: UIImageView!
    @IBOutlet weak var categoryNameLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        categoryImageView?.layer.cornerRadius = (categoryImageView?.frame.size.width ?? 0.0) / 2
        categoryImageView?.clipsToBounds = true
    }
}
