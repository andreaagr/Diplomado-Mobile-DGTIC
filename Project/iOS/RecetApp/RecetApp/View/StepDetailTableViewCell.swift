//
//  InstructionTableViewCell.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 25/03/23.
//

import UIKit

class StepDetailTableViewCell: UITableViewCell, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    @IBOutlet var instructionDetailLabel: UILabel!
    @IBOutlet var ingredientsCollectionView: UICollectionView!
    @IBOutlet var equipmentCollectionView: UICollectionView!
    
    var ingRowItems: [StepTool]?
    var equipRowItems: [StepTool]?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        // Register the xib for collection view cell
        let cellNib = UINib(nibName: "CategoryCollectionViewCell", bundle: nil)
        self.ingredientsCollectionView.register(cellNib, forCellWithReuseIdentifier: "CategoryCollectionViewCell")
        self.equipmentCollectionView.register(cellNib, forCellWithReuseIdentifier: "CategoryCollectionViewCell")
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if collectionView == ingredientsCollectionView {
            return ingRowItems?.count ?? 0
        } else {
            return equipRowItems?.count ?? 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if collectionView == ingredientsCollectionView {
            let cell = ingredientsCollectionView.dequeueReusableCell(withReuseIdentifier: "CategoryCollectionViewCell", for: indexPath) as! CategoryCollectionViewCell
            cell.categoryNameLabel.text = ingRowItems?[indexPath.row].name
            return cell
        } else {
            let cell = equipmentCollectionView.dequeueReusableCell(withReuseIdentifier: "CategoryCollectionViewCell", for: indexPath) as! CategoryCollectionViewCell
            cell.categoryNameLabel.text = equipRowItems?[indexPath.row].name
            return cell
        }
    }
    
    func updateCellWith(rowIng: [StepTool], rowEquip: [StepTool]) {
        self.ingRowItems = rowIng
        self.equipRowItems = rowEquip
        self.ingredientsCollectionView.reloadData()
        self.equipmentCollectionView.reloadData()
    }
}
