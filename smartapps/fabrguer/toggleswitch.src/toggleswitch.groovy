/**
 *  ToggleSwitch
 *
 *  Copyright 2016 Fabrizio Guerrieri
 *
 */

 // General info about the app (name, author, description, icons...)

definition(
    name: "ToggleSwitch",
    namespace: "fabrguer",
    author: "Fabrizio Guerrieri",
    description: "Toggles a Switch",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

// Instantiates two objects used to represents a Trigger device (the virtual push-button that is "pushed" 
// by the Flic button) and an Actuator (the real SmartThings smart-outlet switch commanding my floor lamp).

preferences {
    section("Trigger") {
        input "virtButton", "capability.momentary", title: "Which Virtual Button?", required: true
    }
    section("Actuator") {
        input "realSwitch", "capability.switch", title: "Which Real Switch?", required: true
    }
}

// Executed once when the app is installed

def installed() {
    initialize()
}

// Executed anytime the app is updated

def updated() {
    log.debug "Updated with settings: ${settings}"

    unsubscribe()
    initialize()
}

// When the virtual button is pushed, the callback function virtButtonHandler is called

def initialize() {
    subscribe(virtButton, "momentary.pushed", virtButtonHandler)
}

// Toggles the realSwitch (reads its current state and inverts it)

def virtButtonHandler(evt) {
    
    if (realSwitch.currentState("switch")?.value == "on") {
        realSwitch.off()
    } else {
        realSwitch.on()
    }

}