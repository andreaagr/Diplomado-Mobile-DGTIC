//
//  InstructionTableViewCell.swift
//  RecetApp
//
//  Created by Andrea Garcìa on 25/03/23.
//

import UIKit

class InstructionTableViewCell: UITableViewCell {


    var steps: [Step]?
    @IBOutlet weak var stepTableView: UITableView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }



    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state

        self.stepTableView.dataSource = self
        self.stepTableView.delegate = self
        let cellNib = UINib(nibName: "StepDetailTableViewCell", bundle: nil)
        self.stepTableView.register(cellNib, forCellReuseIdentifier: "StepDetailTableViewCell")

    }

    func updateCellWith(row: [Step]) {
        self.steps = row
        self.stepTableView.reloadData()
    }
}



extension InstructionTableViewCell: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        steps?.count ?? 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        let cell = stepTableView.dequeueReusableCell(withIdentifier: "StepDetailTableViewCell", for: indexPath) as! StepDetailTableViewCell
        cell.instructionDetailLabel.text = steps?[indexPath.section].step
        cell.updateCellWith(rowIng: steps?[indexPath.section].ingredients ?? [], rowEquip: steps?[indexPath.section].equipment ?? [])
        return cell

    }
}
