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
    var recipe: Recipe?
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    let showFavoriteDetail = "showFavoriteDetail"
    lazy var dataManager = RecipeDataManager(context: context)

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        favoritesTableView.register(UINib(nibName: "RecipeTableViewCell", bundle: nil), forCellReuseIdentifier: "RecipeTableViewCell")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        fetchFavorites()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == showFavoriteDetail {
            let destination = segue.destination as! RecipeDetailViewController
            destination.recipe = recipe
        }
    }
    
    private func fetchFavorites() {
        dataManager.fetch()
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
        getRecipeInformation(recipeId: Int(basicRecipeInfoList[indexPath.row].id))
        
    }
    
    private func getRecipeInformation(recipeId: Int) {
        guard let url = URL(string: buildRecipeInfoURL(recipeId: recipeId)) else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.setValue(API_KEY, forHTTPHeaderField: "x-api-key")
        let configuration = URLSessionConfiguration.ephemeral
        let session = URLSession(configuration: configuration)
        let task = session.dataTask(with: request) { bytes, response, error in
            if error == nil {
                guard let data = bytes else { return }
                let recipeInfo = try? JSONDecoder().decode(Recipe.self, from: data)
                if recipeInfo != nil {
                    self.recipe = recipeInfo
                    DispatchQueue.main.async { [self] in
                        performSegue(withIdentifier: self.showFavoriteDetail, sender: Self.self)
                    }
                }
            } else {
                DispatchQueue.main.async {
                    self.present(
                        createCustomAlert(
                            animationName: "error_map",
                            title: "Algo salió mal",
                            message: "Por favor intentalo de nuevo"
                        ), animated: true)
                }
            }
        }
        task.resume()
    }
}
