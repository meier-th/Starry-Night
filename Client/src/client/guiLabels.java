package client;

import java.util.ListResourceBundle;

public class guiLabels extends ListResourceBundle{
    private Object[][] contents = {{"leftHead", "Filter Stars"}, {"rightHead", "Add a Star"},
    {"addButton", "Add"}, {"resetButton", "Reset"}, {"shining", "Shine"},
    {"Earth", "Visible from Earth"}, {"Moon",
    "Visible from Moon"}, {"colour", "Colour"},{"choose", "Choose"}, {"move", "Move Stars"}, 
    {"welcome", "Welcome"}, {"usern", "Create username"}, {"passwrd", "Password"},
    {"sign in", "Sign up"}, {"errPassword", "Incorrect password!"}, {"errPasswordHead", "Access refused"},
    {"white", "white"}, {"yellow", "yellow"}, {"orange", "orange"}, {"red", "red"}, 
    {"filterButton", "Filter"}, {"name", "name"}, {"coordinates", "Coordinates"}, 
    {"cancelButton", "Cancel"},{"errHead",
    "Wrong input!"}, {"errMsgShine", "'Shine' field must contain integer value!"}, 
    {"errMsgName", "Enter name of a new Star!"}, {"errMsgEarth", "Specify visibility from Earth!"}, 
    {"errMsgMoon",
    "Specify visibility from Moon!"},{"errMsgColour", "Choose colour of a new Star!"}, 
    {"addingHead", "Choose location"}, {"errMsgShineValue", "'Shine' value must lay between 0 and 5000!"},
    {"addingText", "Click on the space to set location of the Star."}, {"appName","Client application"},
    {"occUsHead", "Username is occupied"}, {"occUs", "This username is already used. Create another one!"},
    {"usnminput","Create a username!"}, {"noName", "Username not created"}, {"banned", "You are banned, LOL"}, 
    {"banHead", "Banned"}};
    @Override
    protected Object[][] getContents() {
        return this.contents;
    }
    
}
