//
//  ViewController.swift
//  Barman
//
//  Created by Andrea García Ruiz on 28/02/23.
//

import UIKit

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
        
    @IBOutlet var drinksTableView: UIView!
    var drinks = [Drink]()
    let cellIdentifier = "DrinkTableViewCell"
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        retrieveDrinks()
        // Do any additional setup after loading the view.
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return drinks.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! DrinkTableViewCell
        let drink = drinks[indexPath.row]
        cell.drinkNameLabel.text = drink.name
        return cell
    }
    
    func retrieveDrinks() {
        guard let laURL = URL(string: "http://janzelaznog.com/DDAM/iOS/drinks.json") else { return }
        do {
            let bytes = try Data(contentsOf: laURL)
            drinks = try JSONDecoder().decode([Drink].self, from: bytes)
        } catch {
            print ("Error al descargar el JSON " + String(describing: error))
        }
    }
}

