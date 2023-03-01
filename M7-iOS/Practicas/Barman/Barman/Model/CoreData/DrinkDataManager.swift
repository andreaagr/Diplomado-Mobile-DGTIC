//
//  DrinkDataManager.swift
//  Barman
//
//  Created by Andrea García Ruiz on 01/03/23.
//

import Foundation
import CoreData

class DrinkDataManager {
    private var recipes : [Recipe] = []
    private var context : NSManagedObjectContext

    init(context: NSManagedObjectContext) {
        self.context = context
    }

    func fetch() -> [Drink] {
        var drinks: [Drink] = []
        do {
            self.recipes = try self.context.fetch(Recipe.fetchRequest())
        } catch {
            print("Error:", error)
        }
        
        recipes.forEach { recipe in
            drinks.append(transformRecipe(recipe: recipe))
        }
        return drinks
    }
    
    func addDrink(drink: Drink) {
        let recipe = Recipe(context: context)
        recipe.name = drink.name
        recipe.ingredients = drink.ingredients
        recipe.instructions = drink.directions
        recipe.imagePath = drink.img
        do {
            try context.save()
        } catch {
            print("Error al guardar")
        }
    }
}

