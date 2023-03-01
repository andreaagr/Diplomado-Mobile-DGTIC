//
//  Recipe+CoreDataProperties.swift
//  Barman
//
//  Created by Andrea García Ruiz on 01/03/23.
//
//

import Foundation
import CoreData


extension Recipe {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Recipe> {
        return NSFetchRequest<Recipe>(entityName: "Recipe")
    }

    @NSManaged public var name: String?
    @NSManaged public var ingredients: String?
    @NSManaged public var instructions: String?
    @NSManaged public var imagePath: String?

}

extension Recipe : Identifiable {

}
