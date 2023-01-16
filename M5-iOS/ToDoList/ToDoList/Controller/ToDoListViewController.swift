//
//  ToDoListViewController.swift
//  ToDoList
//
//  Created by Andrea Garcìa on 17/12/22.
//

import UIKit

class ToDoListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    var currentTask : Task?
    var dataManager : TaskDataManager?
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataManager?.countTask() ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "toDoTaskCell", for: indexPath) as! ToDoTaskTableViewCell
        cell.toDoTitle.text = dataManager?.getTask(at: indexPath.row).title
        return cell
    }
    
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let delete = UIContextualAction(style: .destructive, title: "Delete", handler: {_, _, complete in
            
            let alert = UIAlertController(
                title: NSLocalizedString("delete_modal_title", comment: ""),
                message: NSLocalizedString("delete_modal_message", comment: ""),
                preferredStyle: .alert
            )
            alert.addAction(UIAlertAction(title: NSLocalizedString("continue", comment: ""), style: .destructive, handler: { _ in
                do {
                    try self.dataManager?.deleteTask(at: indexPath.row)
                    tableView.deleteRows(at: [indexPath], with: .fade)
                    complete(true)
                } catch {
                    complete(false)
                    let alert = UIAlertController(
                        title: NSLocalizedString("delete_error_modal_title", comment: ""),
                        message: NSLocalizedString("delete_error_modal_message", comment: ""),
                        preferredStyle: .alert
                    )
                    alert.addAction(UIAlertAction(title: "Ok", style: .default))
                    self.present(alert, animated: true)
                }
            }))
            alert.addAction(UIAlertAction(title: NSLocalizedString("cancel", comment: ""), style: .cancel, handler: { _ in complete(false)}))
            self.present(alert, animated: true)
        })
        
        delete.image = UIImage(named: "trash")
        delete.backgroundColor = UIColor(named: "DeleteAction")
        
        let configuration = UISwipeActionsConfiguration(actions: [delete])
        return configuration
    }
    
    @IBOutlet weak var toDoListTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        toDoListTableView.dataSource = self
        toDoListTableView.delegate = self
        dataManager = TaskDataManager(context: context)
        dataManager?.fetch()
        self.searchBar.datePicker(
            target: self,
            doneAction: #selector(doneAction),
            cancelAction: #selector(cancelAction),
            datePickerMode: .date
        )
        searchBar.placeholder = NSLocalizedString("search_notes_placeholder", comment: "")
    }
    

    @IBAction func unwindFromDetail(segue: UIStoryboardSegue) {
        let source = segue.source as! TaskTableViewController
        currentTask = source.toDoDetailTask
        do {
            try context.save()
        } catch {
            print("Error al guardar")
        }
        dataManager?.fetch()
        self.toDoListTableView.reloadData()
    }
    
    @IBOutlet weak var searchBar: UITextField!
    
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showTaskSegue" {
            let destination = segue.destination as! TaskTableViewController
            destination.toDoDetailTask = dataManager?.getTask(at: toDoListTableView.indexPathForSelectedRow!.row)
        }
    }
    
    
    @objc
    func cancelAction() {
        self.searchBar.resignFirstResponder()
    }

    @objc
    func doneAction() {
        if let datePickerView = self.searchBar.inputView as? UIDatePicker {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            let dateString = dateFormatter.string(from: datePickerView.date)
            self.searchBar.text = dateString
            self.dataManager?.getTaskByDate(date: datePickerView.date)
            self.toDoListTableView.reloadData()
            self.searchBar.resignFirstResponder()
        }
    }
    

}
