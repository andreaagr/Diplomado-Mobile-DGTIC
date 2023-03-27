//
//  ResultsViewController.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import UIKit

class ResultsViewController: UIViewController {
    
    @IBOutlet var resultsTableView: UITableView!
    
    var categoryType: Category?
    var categorySelected: String?
    var keyword: String?
    var resultType: ResultType?
    var results: [Recipe] = []
    private var showDetailSegue = "showResultDetail"
    private var recipeSelected: Recipe?
    lazy var indicatorView = createActivityIndicator(center: self.view.center)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        performSearchRecipeQuery()
        // Do any additional setup after loading the view.
        // Register the xib for tableview cell
        resultsTableView.register(UINib(nibName: "RecipeTableViewCell", bundle: nil), forCellReuseIdentifier: "RecipeTableViewCell")
        self.view.addSubview(indicatorView)
    }
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == showDetailSegue {
            let destination = segue.destination as! RecipeDetailViewController
            destination.recipe = recipeSelected
        }
    }
    
    func performSearchRecipeQuery() {
        guard var URL = URLComponents(string: API_SPOONACULAR_URL + COMPLEX_SEARCH_ENDPOINT) else { return }
        
        var queryItem: URLQueryItem?
        
        if resultType == ResultType.KEYWORD {
            queryItem = URLQueryItem(name: "query", value: keyword)
        } else {
            queryItem = URLQueryItem(name: categoryType!.rawValue, value: categorySelected)
        }
        URL.queryItems = [queryItem!]
        
        builRequest(urlComponents: URL, onSuccess: { data in
            let recipes = try? JSONDecoder().decode(ComplexSearchResponse.self, from: data)
            for recipe in recipes!.results {
                self.getRecipeInformation(recipeId: recipe.id)
            }
        })
    }
    
    func builRequest(urlComponents: URLComponents, onSuccess: @escaping (Data) -> Void) {
        var request = URLRequest(url: (urlComponents.url)!)
        request.httpMethod = "GET"
        request.setValue(API_KEY, forHTTPHeaderField: "x-api-key")
        let configuration = URLSessionConfiguration.ephemeral
        let session = URLSession(configuration: configuration)
        indicatorView.startAnimating()
        let task = session.dataTask(with: request) { bytes, response, error in
            if error == nil {
                guard let data = bytes else { return }
                DispatchQueue.main.async {
                    self.indicatorView.stopAnimating()
                }
                onSuccess(data)
            } else {
                DispatchQueue.main.async {
                    self.indicatorView.stopAnimating()
                    self.showError()
                }
            }
        }
        task.resume()
    }
    
    func getRecipeInformation(recipeId: Int) {
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
                    self.results.append(recipeInfo!)
                    DispatchQueue.main.async {
                        self.resultsTableView.reloadData()
                    }
                }
            }
        }
        task.resume()
    }
    
    private func showError() {
        self.present(
            createCustomAlert(
                animationName: "error_map",
                title: "Algo salió mal",
                message: "Por favor intentalo de nuevo"
            ), animated: true
        )
    }
}

extension ResultsViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        results.count
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        recipeSelected = results[indexPath.row]
        performSegue(withIdentifier: showDetailSegue, sender: Self.self)
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeTableViewCell", for: indexPath) as! RecipeTableViewCell
        cell.recipeImageView.loadFrom(URLAddress: results[indexPath.row].image)
        cell.recipeTitleLabel.text = results[indexPath.row].title
        cell.recipeSummaryLabel.text = results[indexPath.row].summary.htmlToString()
        return cell
    }
}

enum Category: String {
    case MEAL_TYPE = "type"
    case CUISINE = "cuisine"
    case DIET = "diet"
}

enum ResultType {
    case CATEGORY
    case KEYWORD
}
