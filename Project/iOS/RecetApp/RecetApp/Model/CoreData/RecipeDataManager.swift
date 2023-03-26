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
        fetch()
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
        context.delete(recipe[0])
        do {
            try context.save()
        } catch {
            print("Error al eliminar")
        }
    }
    
    func isFavorite(recipeId: Int) -> Bool {
        return recipes.contains(where: { $0.id == recipeId })
    }
}
