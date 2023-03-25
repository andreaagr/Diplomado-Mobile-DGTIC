//
//  RecipePresenter.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 23/03/23.
//

import Foundation

class RecipePresenter: ViewToPresenterProtocol {
    var view: PresenterToViewProtocol?
    
    var interactor: PresenterToInteractorProtocol?
    
    var router: PresenterToRouterProtocol?
    
    func startFetchingCategories() {
        interactor?.fetchCategories()
    }
}

extension RecipePresenter: InteractorToPresenterProtocol {
    func categoriesFetchedSuccess(categoryItems: [[RecipeCategory]]) {
        view?.showCategories(categoryItems: categoryItems)
    }
}
