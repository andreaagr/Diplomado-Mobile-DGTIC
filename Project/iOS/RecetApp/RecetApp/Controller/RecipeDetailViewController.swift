//
//  RecipeDetailViewController.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import UIKit

class RecipeDetailViewController: UIViewController {
    
    var recipe: Recipe?
    
    @IBOutlet var recipeImageView: UIImageView!
    @IBOutlet var recipeTimeInMinutesLabel: UILabel!
    @IBOutlet var recipeServesLabel: UILabel!
    @IBOutlet var recipePriceLabel: UILabel!
    @IBOutlet var recipeDetailsTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        loadRecipe()
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    private func loadRecipe() {
        recipeImageView.loadFrom(URLAddress: recipe?.image ?? "")
        recipeTimeInMinutesLabel.text = "\(recipe?.readyInMinutes ?? 0) minutes"
        recipePriceLabel.text = "$\(recipe?.pricePerServing ?? 0.0)"
        recipeServesLabel.text = "Serves \(recipe?.servings ?? 0)"
    }
}
