//
//  TaskTableViewController.swift
//  ToDoList
//
//  Created by Andrea Garcìa on 17/12/22.
//

import UIKit

class TaskTableViewController: UITableViewController, UITextFieldDelegate, UITextViewDelegate {

    @IBOutlet weak var taskTitleTextField: UITextField!
    @IBOutlet weak var taskDatePicker: UIDatePicker!
    @IBOutlet weak var taskNotesTextView: UITextView!
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    var toDoDetailTask : Task?
    
    @IBAction func cancelButtonPressed(_ sender: UIBarButtonItem) {
        let isModal = self.presentingViewController is UINavigationController
        if isModal {
            self.dismiss(animated: true)
        } else {
            navigationController?.popViewController(animated: true)
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destination = segue.destination as! ToDoListViewController
        toDoDetailTask?.title = taskTitleTextField.text
        toDoDetailTask?.date = taskDatePicker.date
        toDoDetailTask?.notes = taskNotesTextView.text
        
        destination.currentTask = toDoDetailTask
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        var perform = false
        if validateText(text: taskTitleTextField.text!) {
            perform = true
        } else {
            let alert = UIAlertController(
                title: NSLocalizedString("empty_title_error_modal_title", comment: ""),
                message: NSLocalizedString("empty_title_error_modal_message", comment: ""),
                preferredStyle: .alert
            )
            alert.addAction(UIAlertAction(title: NSLocalizedString("ok", comment: ""), style: .default))
            self.present(alert, animated: true)
            
        }
        return perform
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        taskTitleTextField.delegate = self
        taskNotesTextView.delegate = self
        if toDoDetailTask != nil {
            taskTitleTextField.text = toDoDetailTask?.title
            taskDatePicker.date = (toDoDetailTask?.date)!
            taskNotesTextView.text = toDoDetailTask?.notes
        } else {
            toDoDetailTask = Task(context: context)
            taskTitleTextField.text = ""
        }
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        let titles = ["title", "date", "notes"]
        return NSLocalizedString(titles[section], comment: "")
    }
    
    // ----------------------------------------------Dismiss keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        return taskTitleTextField.resignFirstResponder()
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        if(text == "\n") {
            textView.resignFirstResponder()
            return false
        }
        return true
    }
}
