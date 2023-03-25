//
//  RecipeTableViewCell.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import UIKit

class RecipeTableViewCell: UITableViewCell {

    @IBOutlet var recipeImageView: UIImageView!
    @IBOutlet var recipeTitleLabel: UILabel!
    @IBOutlet var recipeSummaryLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
