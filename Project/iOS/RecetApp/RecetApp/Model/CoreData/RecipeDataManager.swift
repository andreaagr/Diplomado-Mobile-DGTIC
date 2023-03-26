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
    private var recipes : [RecipeCD] = []
    private var context : NSManagedObjectContext
    
    init(context: NSManagedObjectContext) {
        self.context = context
    }

    func fetch() -> [RecipeCD] {
        do {
            self.recipes = try self.context.fetch(RecipeCD.fetchRequest())
        } catch {
            print("Error:", error)
        }
        return recipes
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
    }
    
    func removeRecipe(recipe: Recipe) {
        let recipeCD = RecipeCD(context: context)
        recipeCD.id = Int32(recipe.id)
        recipeCD.title = recipe.title
        recipeCD.summary = recipe.summary
        recipeCD.image = recipe.image
        context.delete(recipeCD)
        do {
            try context.save()
        } catch {
            print("Error al eliminar")
        }
    }
}
