//
//  ViewController.swift
//  PlayAudio
//
//  Created by Andrea Garcìa on 11/02/23.
//

import UIKit
import AVFoundation

class ViewController: UIViewController {
    var audioPlayer: AVAudioPlayer?
    
    let btnPlay=UIButton(type: .system)
    let btnStop=UIButton(type: .system)
    let sliderDuration=UISlider()
    let sliderVolume=UISlider()
    var timer: Timer?

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        let l1=UILabel()
        l1.text="AudioPlayer"
        l1.font=UIFont.systemFont(ofSize: 24)
        l1.autoresizingMask = .flexibleWidth
        l1.translatesAutoresizingMaskIntoConstraints=true
        l1.frame=CGRect(x: 0, y: 50, width: self.view.frame.width, height: 50)
        l1.textAlignment = .center
        self.view.addSubview(l1)
                
        btnPlay.setTitle("Play", for: .normal)
        btnPlay.autoresizingMask = .flexibleWidth
        btnPlay.translatesAutoresizingMaskIntoConstraints=true
        btnPlay.frame=CGRect(x: 20, y: 100, width: 100, height: 40)
        btnPlay.addTarget(self, action:#selector(btnPlayTouch), for:.touchUpInside)
        self.view.addSubview(btnPlay)
                
        sliderDuration.autoresizingMask = .flexibleWidth
        sliderDuration.translatesAutoresizingMaskIntoConstraints=true
        sliderDuration.frame=CGRect(x: 20, y:150, width: self.view.frame.width-40, height: 50)
        sliderDuration.addTarget(self, action: #selector(sliderDurationChange), for: .valueChanged)
        self.view.addSubview(sliderDuration)
                
        btnStop.setTitle("Stop", for: .normal)
        btnStop.autoresizingMask = .flexibleWidth
        btnStop.translatesAutoresizingMaskIntoConstraints=true
        btnStop.frame=CGRect(x:self.view.frame.width-100, y: 100, width: 100, height: 40)
        btnStop.addTarget(self, action:#selector(btnStopTouch), for:.touchUpInside)
        self.view.addSubview(btnStop)
                
        let l2=UILabel()
        l2.text="Volumen"
        l2.autoresizingMask = .flexibleWidth
        l2.translatesAutoresizingMaskIntoConstraints=true
                l2.frame=CGRect(x: 20, y: 200, width: 100, height: 40)
        self.view.addSubview(l2)

        sliderVolume.autoresizingMask = .flexibleWidth
        sliderVolume.translatesAutoresizingMaskIntoConstraints=true
        sliderVolume.frame=CGRect(x: 20, y: 250, width: self.view.frame.width/2, height: 50)
        sliderVolume.addTarget(self, action: #selector(sliderVolumeChange), for: .valueChanged)
        self.view.addSubview(sliderVolume)
        
        cargarAudio()
    }
    
    @objc func sliderVolumeChange() {
        audioPlayer?.volume = sliderVolume.value
    }
    
    @objc func sliderDurationChange() {
        audioPlayer?.currentTime = TimeInterval(sliderDuration.value)
    }
        
    @objc func btnPlayTouch() {
        audioPlayer?.play()
    }
    
    @objc func btnStopTouch() {
        audioPlayer?.stop()
    }
    
    func cargarAudio() {
        guard let url = Bundle.main.url(forResource: "Dillon-13-36", withExtension: "mp3") else { return }
        do {
            audioPlayer = try AVAudioPlayer(contentsOf: url)
            audioPlayer?.numberOfLoops = -1
            sliderVolume.value = audioPlayer?.volume ?? 0.0
            sliderDuration.maximumValue = Float(audioPlayer?.duration ?? 0.0)
            timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true, block: { aTimer in
                self.sliderDuration.value = Float(self.audioPlayer?.currentTime ?? 0.0)
            })
            audioPlayer?.play()
        } catch {
            print ("Error abriendo el archivo de sonido " + String(describing: error))
        }
    }

}
