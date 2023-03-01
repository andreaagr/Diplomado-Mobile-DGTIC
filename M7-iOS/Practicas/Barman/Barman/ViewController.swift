//
//  ViewController.swift
//  Barman
//
//  Created by Andrea García Ruiz on 28/02/23.
//

import UIKit

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
        
    @IBOutlet weak var drinksTableView: UITableView!
    
    var drinks = [Drink]()
    var drink: Drink?
    var newDrink: Drink?
    let cellIdentifier = "DrinkTableViewCell"
    let showDetailSegue = "showDetail"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        retrieveDrinks()
        // Do any additional setup after loading the view.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == showDetailSegue {
            let destination = segue.destination as! DetailViewController
            destination.drinkReceived = drink
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        drink = drinks[indexPath.row]
        performSegue(withIdentifier: showDetailSegue, sender: Self.self)
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
        let configuration = URLSessionConfiguration.ephemeral
        let session = URLSession(configuration: configuration)
        let elReq = URLRequest (url: laURL)
        let task = session.dataTask(with: elReq) { bytes, response, error in
            if error == nil {
                guard let data = bytes else { return }
                do {
                    self.drinks = try JSONDecoder().decode([Drink].self, from: data)
                    DispatchQueue.main.async {
                        self.drinksTableView.reloadData()
                    }
                } catch {
                    print ("Error al descargar el JSON " + String(describing: error))
                }
            }
        }
        task.resume()
    }
    
    @IBAction func unwindFromDetail(segue: UIStoryboardSegue) {
        drinks.append(newDrink!)
        self.drinksTableView.reloadData()
    }
}

