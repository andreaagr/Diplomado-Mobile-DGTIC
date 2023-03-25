//
//  RecetAppProtocol.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 23/03/23.
//

import Foundation

protocol PresenterToInteractorProtocol: AnyObject {
    var presenter: InteractorToPresenterProtocol? {get set}
    func fetchCategories()
}

protocol InteractorToPresenterProtocol: AnyObject {
    func categoriesFetchedSuccess(categoryItems: [[RecipeCategory]])
}

protocol PresenterToRouterProtocol: AnyObject {
    static func createModule() -> ViewController
    //func pushToMovieScreen(navigationConroller:UINavigationController)
}

protocol PresenterToViewProtocol: AnyObject{
    func showCategories(categoryItems: [[RecipeCategory]])
    func showError()
}

protocol ViewToPresenterProtocol: AnyObject{
    
    var view: PresenterToViewProtocol? {get set}
    var interactor: PresenterToInteractorProtocol? {get set}
    var router: PresenterToRouterProtocol? {get set}
    
    func startFetchingCategories()
    // func showMovieController(navigationController:UINavigationController)
}
