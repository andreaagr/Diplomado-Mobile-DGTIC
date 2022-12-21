//
//  ToDoTaskTableViewCell.swift
//  ToDoList
//
//  Created by Andrea Garcìa on 17/12/22.
//

import UIKit

class ToDoTaskTableViewCell: UITableViewCell {

    @IBOutlet weak var toDoTitle: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
