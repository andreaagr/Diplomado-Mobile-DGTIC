//
//  AppConstants.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 22/03/23.
//

import Foundation

let API_SPOONACULAR_URL = "https://api.spoonacular.com/"
let API_KEY = "b46e9574028f468db982ee1e50a72c7a"
let COMPLEX_SEARCH_ENDPOINT = "recipes/complexSearch"
func buildRecipeInfoURL(recipeId: Int) -> String {
    API_SPOONACULAR_URL + "recipes/\(recipeId)/information"
}
