//
//  RecipeDataManager.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 26/03/23.
//

import Foundation
import CoreData
import UIKit

class RecipeDataManager {
    var recipes : [RecipeCD] = []
    private var context : NSManagedObjectContext
    
    init(context: NSManagedObjectContext) {
        self.context = context
    }

    func fetch() {
        do {
            self.recipes = try self.context.fetch(RecipeCD.fetchRequest())
        } catch {
            print("Error:", error)
        }
    }
    
    func addRecipe(recipe: Recipe) {
        let recipeCD = RecipeCD(context: context)
        recipeCD.title = recipe.title
        recipeCD.summary = recipe.summary
        recipeCD.image = recipe.image
        recipeCD.id = Int32(recipe.id)
        do {
            try context.save()
        } catch {
            print("Error al guardar")
        }
        fetch()
    }
    
    func removeRecipe(recipeId: Int) {
        let recipe = recipes.filter { $0.id == recipeId }
        print(recipes.map { $0.title })
        print("Item for being removed")
        print(recipe.map { $0.title })
        context.delete(recipe[0])
        do {
            try context.save()
        } catch {
            print("Error al eliminar")
        }
    }
}
