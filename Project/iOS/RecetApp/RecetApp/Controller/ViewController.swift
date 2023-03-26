//
//  ViewController.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 21/03/23.
//

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet var searchBar: UISearchBar!
    @IBOutlet var categoriesTableView: UITableView!
    
    var presenter: ViewToPresenterProtocol?
    var categoryItemsList = [[RecipeCategory]]()
    var resultType: ResultType?
    var categorySelected: String?
    var categoryType: Category?
    var keyword: String?
    let sectionTitles = ["Meal types", "Cuisines", "Diets"]
    let showResultsSegue = "showRecipeSearchResults"

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        presenter?.startFetchingCategories()
        // Register the xib for tableview cell
        categoriesTableView.register(UINib(nibName: "CategoryTableViewCell", bundle: nil), forCellReuseIdentifier: "CategoryTableViewCell")
        if !InternetMonitor.instance.internetStatus {
            showInternetError()
        }
        showCategories()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == showResultsSegue {
            let destination = segue.destination as! ResultsViewController
            destination.keyword = keyword
            destination.resultType = resultType
            destination.categorySelected = categorySelected
            destination.categoryType = categoryType
        }
    }
    
    private func readLocalFile(forName name: String) -> Data? {
        do {
            if let bundlePath = Bundle.main.path(forResource: name, ofType: "json"),
                let jsonData = try String(contentsOfFile: bundlePath).data(using: .utf8) {
                return jsonData
            }
        } catch {
            print(error)
        }
        return nil
    }
    
    
    
    // MARK: Ya funciona
    func showCategories() {
        do {
            try categoryItemsList.append(JSONDecoder().decode([RecipeCategory].self, from: readLocalFile(forName: "meal_types")!))
            try categoryItemsList.append(JSONDecoder().decode([RecipeCategory].self, from: readLocalFile(forName: "cuisines")!))
            try categoryItemsList.append(JSONDecoder().decode([RecipeCategory].self, from: readLocalFile(forName: "diets")!))
            
        } catch {
            print(error.localizedDescription)
        }
        
        self.categoriesTableView.reloadData()
    }
    
    func showError() {
        let alert = UIAlertController(
            title: "Algo salió mal 😔",
            message: "Por favor intentalo de nuevo",
            preferredStyle: .alert
        )
        alert.addAction(UIAlertAction(title: "Ok", style: .default))
        self.present(alert, animated: true)
    }
    
    func showInternetError() {
        let alert = UIAlertController(
            title: "No se detectó conexión a internet 📶",
            message: "Algunas funciones podrían no estar disponibles",
            preferredStyle: .alert
        )
        alert.addAction(UIAlertAction(title: "Ok", style: .default))
        self.present(alert, animated: true)
    }
}

extension ViewController: CollectionViewCellDelegate {
    func collectionView(collectionviewcell: CategoryCollectionViewCell?, index: Int, didTappedInTableViewCell: CategoryTableViewCell) {
            if let categoryRow = didTappedInTableViewCell.categoryRowItems {
                resultType = ResultType.CATEGORY
                switch categoryRow.count {
                case 11: categoryType = Category.MEAL_TYPE
                case 17: categoryType = Category.CUISINE
                case 7: categoryType = Category.DIET
                default:
                    categoryType = Category.MEAL_TYPE
                }
                categorySelected = categoryRow[index].name
                performSegue(withIdentifier: showResultsSegue, sender: Self.self)
            }
        
    }
}

extension ViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        3
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if !categoryItemsList.isEmpty {
            return 1
        } else {
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "CategoryTableViewCell", for: indexPath) as! CategoryTableViewCell
        if !categoryItemsList.isEmpty {
            cell.updateCellWith(row: categoryItemsList[indexPath.section])
            cell.cellDelegate = self
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        sectionTitles[section]
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        200
    }
    
}

extension ViewController: UISearchBarDelegate {
    
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        self.searchBar.showsCancelButton = true
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchBar.showsCancelButton = false
        searchBar.text = ""
        searchBar.resignFirstResponder()
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        resultType = ResultType.KEYWORD
        keyword = self.searchBar.text ?? ""
        performSegue(withIdentifier: showResultsSegue, sender: Self.self)
    }
}
