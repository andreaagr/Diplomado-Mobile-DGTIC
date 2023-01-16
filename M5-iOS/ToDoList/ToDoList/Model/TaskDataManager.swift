//
//  TaskDataManager.swift
//  ToDoList
//
//  Created by Andrea Garcìa on 17/12/22.
//

import Foundation
import CoreData

class TaskDataManager {
    private var tasks : [Task] = []
    private var context : NSManagedObjectContext

    init(context: NSManagedObjectContext) {
        self.context = context
    }

    func fetch() {
        do {
            self.tasks = try self.context.fetch(Task.fetchRequest())
        } catch {
            print("Error:", error)
        }
    }
    
    func getTask(at index: Int) -> Task {
        return tasks[index]
    }
    
    func deleteTask(at index: Int) throws {
        self.context.delete(tasks[index])
        tasks.remove(at: index)
        do {
            try self.context.save()
        } catch {
            throw error
        }
    }
    
    func getTaskByDate(date: Date) {
        let fetchRequest: NSFetchRequest<Task> = Task.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "date >= %@", date as NSDate)
        do {
            self.tasks = try self.context.fetch(fetchRequest)
        } catch {
            self.tasks = []
        }
    }
    
    func countTask() -> Int {
        return tasks.count
    }
}
