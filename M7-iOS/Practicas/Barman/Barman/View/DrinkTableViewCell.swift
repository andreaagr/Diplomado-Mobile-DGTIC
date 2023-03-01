//
//  DrinkTableViewCell.swift
//  Barman
//
//  Created by Andrea García Ruiz on 28/02/23.
//

import UIKit

class DrinkTableViewCell: UITableViewCell {
    @IBOutlet weak var drinkNameLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
