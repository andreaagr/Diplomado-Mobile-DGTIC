//
//  StepDetailTableViewCell.swift
//  RecetApp
//
//  Created by Andrea Garcìa on 25/03/23.
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
        self.ingredientsCollectionView.dataSource = self
        self.ingredientsCollectionView.delegate = self
        self.equipmentCollectionView.dataSource = self
        self.equipmentCollectionView.delegate = self
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
            if !(ingRowItems?[indexPath.row].image.isEmpty ?? false) {
                cell.categoryImageView.loadFrom(URLAddress: API_IMAGE_URL + (ingRowItems?[indexPath.row].image ?? ""))
            }
            return cell
        } else {
            let cell = equipmentCollectionView.dequeueReusableCell(withReuseIdentifier: "CategoryCollectionViewCell", for: indexPath) as! CategoryCollectionViewCell
            cell.categoryNameLabel.text = equipRowItems?[indexPath.row].name
            if !(equipRowItems?[indexPath.row].image.isEmpty ?? false) {
                cell.categoryImageView.loadFrom(URLAddress: API_EQUIPMENT_URL + (ingRowItems?[indexPath.row].image ?? ""))
            }
            return cell
        }
    }
    
    func updateCellWith(rowEquip: [StepTool]) {
        self.equipRowItems = rowEquip
        self.equipmentCollectionView.reloadData()
    }
    
    func updateCellWith(rowIng: [StepTool]) {
        self.ingRowItems = rowIng
        self.ingredientsCollectionView.reloadData()
    }
}
