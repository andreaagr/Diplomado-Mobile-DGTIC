//
//  RecipeInteractor.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 23/03/23.
//

import Foundation

class NoticeInteractor: PresenterToInteractorProtocol {
    var presenter: InteractorToPresenterProtocol?
    
    func fetchCategories() {
        var categories: [[RecipeCategory]] = []
        
        do {
            try categories.append(JSONDecoder().decode([RecipeCategory].self, from: readLocalFile(forName: "meal_types")!))
            try categories.append(JSONDecoder().decode([RecipeCategory].self, from: readLocalFile(forName: "cuisines")!))
            try categories.append(JSONDecoder().decode([RecipeCategory].self, from: readLocalFile(forName: "diets")!))
            presenter?.categoriesFetchedSuccess(categoryItems: categories)
        } catch {
            print(error.localizedDescription)
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
}
