//
//  RecipeByIngredients.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 22/03/23.
//

import Foundation

// MARK: - WelcomeElement
struct RecipeByIngredients: Codable {
    let id: Int
    let image: String
    let missedIngredientCount: Int
    let missedIngredients: [ExtendedIngredient]
    let title: String
    let unusedIngredients: [ExtendedIngredient]
    let usedIngredientCount: Int
    let usedIngredients: [ExtendedIngredient]
}
