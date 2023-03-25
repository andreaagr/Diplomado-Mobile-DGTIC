//
//  RecipeDetailViewController.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import UIKit

class RecipeDetailViewController: UIViewController {
    
    var recipe: Recipe?
    
    @IBOutlet var recipeTitleLabel: UILabel!
    @IBOutlet var recipeImageView: UIImageView!
    @IBOutlet var recipeTimeInMinutesLabel: UILabel!
    @IBOutlet var recipeServesLabel: UILabel!
    @IBOutlet var recipePriceLabel: UILabel!
    @IBOutlet var recipeDetailsTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        loadRecipe()
        let cellNib = UINib(nibName: "InstructionTableViewCell", bundle: nil)
        self.recipeDetailsTableView.register(cellNib, forCellReuseIdentifier: "InstructionTableViewCell")
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
        recipeTitleLabel.text = recipe?.title
        recipeImageView.loadFrom(URLAddress: recipe?.image ?? "")
        recipeTimeInMinutesLabel.text = "\(recipe?.readyInMinutes ?? 0) minutes"
        recipePriceLabel.text = "$\(recipe?.pricePerServing ?? 0.0)"
        recipeServesLabel.text = "Serves \(recipe?.servings ?? 0)"
    }
}

extension RecipeDetailViewController: UITableViewDelegate, UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        recipe?.analyzedInstructions.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        recipe?.analyzedInstructions[section].steps.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = recipeDetailsTableView.dequeueReusableCell(withIdentifier: "InstructionTableViewCell", for: indexPath) as! InstructionTableViewCell
        cell.updateCellWith(row: recipe?.analyzedInstructions[indexPath.section].steps ?? [])
        return cell
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        recipe?.analyzedInstructions[section].name
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        500
    }
}
