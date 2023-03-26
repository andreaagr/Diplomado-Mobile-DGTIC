//
//  RecipeCD+CoreDataProperties.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 26/03/23.
//
//

import Foundation
import CoreData


extension RecipeCD {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<RecipeCD> {
        return NSFetchRequest<RecipeCD>(entityName: "RecipeCD")
    }

    @NSManaged public var id: Int32
    @NSManaged public var title: String?
    @NSManaged public var summary: String?
    @NSManaged public var image: String?

}
