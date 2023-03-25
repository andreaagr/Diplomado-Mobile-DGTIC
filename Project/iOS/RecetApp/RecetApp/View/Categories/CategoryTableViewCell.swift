//
//  CategoryTableViewCell.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 23/03/23.
//

import UIKit

class CategoryTableViewCell: UITableViewCell, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    weak var cellDelegate: CollectionViewCellDelegate?
    
    var categoryRowItems: [RecipeCategory]?
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        categoryRowItems?.count ?? 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = categoryCollectionView.dequeueReusableCell(withReuseIdentifier: "CategoryCollectionViewCell", for: indexPath) as! CategoryCollectionViewCell
        cell.categoryNameLabel.text = categoryRowItems?[indexPath.row].name ?? ""
        cell.categoryImageView.loadFrom(URLAddress: categoryRowItems?[indexPath.row].image ?? "")
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let cell = collectionView.cellForItem(at: indexPath) as? CategoryCollectionViewCell
        self.cellDelegate?.collectionView(collectionviewcell: cell, index: indexPath.item, didTappedInTableViewCell: self)
    }

    @IBOutlet weak var categoryCollectionView: UICollectionView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.categoryCollectionView.dataSource = self
        self.categoryCollectionView.delegate = self
        // Register the xib for collection view cell
        let cellNib = UINib(nibName: "CategoryCollectionViewCell", bundle: nil)
        self.categoryCollectionView.register(cellNib, forCellWithReuseIdentifier: "CategoryCollectionViewCell")
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
    
    func updateCellWith(row: [RecipeCategory]) {
        self.categoryRowItems = row
        self.categoryCollectionView.reloadData()
    }
    
}

protocol CollectionViewCellDelegate: AnyObject {
    func collectionView(collectionviewcell: CategoryCollectionViewCell?, index: Int, didTappedInTableViewCell: CategoryTableViewCell)
    // other delegate methods that you can define to perform action in viewcontroller
}
