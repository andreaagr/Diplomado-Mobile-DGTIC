//
//  FavoritesViewController.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import UIKit

class FavoritesViewController: UIViewController {
    
    @IBOutlet var favoritesTableView: UITableView!
    
    var basicRecipeInfoList: [RecipeCD] = []
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    lazy var dataManager = RecipeDataManager(context: context)

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        favoritesTableView.register(UINib(nibName: "RecipeTableViewCell", bundle: nil), forCellReuseIdentifier: "RecipeTableViewCell")
        fetchFavorites()
    }
    
    private func fetchFavorites() {
        basicRecipeInfoList = dataManager.recipes
        favoritesTableView.reloadData()
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

extension FavoritesViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        basicRecipeInfoList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeTableViewCell", for: indexPath) as! RecipeTableViewCell
        cell.recipeTitleLabel.text = basicRecipeInfoList[indexPath.row].title
        cell.recipeImageView.loadFrom(URLAddress: basicRecipeInfoList[indexPath.row].image ?? "")
        cell.recipeSummaryLabel.text = basicRecipeInfoList[indexPath.row].summary?.htmlToString()
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // Ask for recipe info and start detail segue
    }
}
