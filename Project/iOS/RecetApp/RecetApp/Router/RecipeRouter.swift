//
//  RecipeRouter.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import Foundation
import UIKit

class RecipeRouter: PresenterToRouterProtocol {
    
    static func createModule() -> ViewController {
        let view = mainstoryboard.instantiateViewController(withIdentifier: "MyViewController") as! ViewController
                
        let presenter: ViewToPresenterProtocol & InteractorToPresenterProtocol = RecipePresenter()
        let interactor: PresenterToInteractorProtocol = NoticeInteractor()
        let router:PresenterToRouterProtocol = RecipeRouter()
                
        view.presenter = presenter
        presenter.view = view
        presenter.router = router
        presenter.interactor = interactor
        interactor.presenter = presenter
                
        return view
    }
    
    static var mainstoryboard: UIStoryboard{
        return UIStoryboard(name:"Main", bundle: Bundle.main)
    }
}
