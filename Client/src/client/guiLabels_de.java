package client;

import java.util.ListResourceBundle;

public class guiLabels_de extends ListResourceBundle{
    
    private Object[][] contents = {{"leftHead", "filtern die Sterne"}, {"rightHead", "addieren ein Star"},
    {"addButton", "addieren"}, {"resetButton", "zurücksetzen"}, {"shining", "Glanz"},
    {"Earth", "sichtbar von Erde"}, {"Moon",
    "sichtbar von Mond"}, {"colour", "Farbe"},{"choose", "wählen"}, {"move", "bewegen die Sterne"}, 
    {"welcome", "Willkommen"}, {"usern", "Nutzername:"}, {"passwrd", "Passwort"},
    {"sign in", "anmelden"}, {"errPassword", "falsch Passwort!"}, {"errPasswordHead", "Zugriff abgelehnt"},
    {"white", "Weiß"}, {"yellow", "Gelb"}, {"orange", "Orange"}, {"red", "Rot"}, 
    {"filterButton", "filtern"}, {"name", "Name"}, {"coordinates", "Koordinaten"}, 
    {"cancelButton", "stornieren"},{"errHead",
    "falsch Eingang!"}, {"errMsgShine", "'Glanz' muss ganzzahlige Werte enthalten!"}, 
    {"errMsgName", "Geben Sie den Namen eines neuen Sterns ein!"}, {"errMsgEarth", "Spezifiziere Sichtbarkeit von der Erde!"}, 
    {"errMsgMoon",
    "Spezifiziere Sichtbarkeit von Mond!"},{"errMsgColour", "Wähle eine Farbe für einen neuen Stern!"}, 
    {"addingHead", "Wähle einen Ort"}, {"errMsgShineValue", "Der 'Glanz' Wert muss zwischen 0 und 5000 liegen!"},
    {"addingText", "Klicken Sie auf das Feld, um den Standort des Sterns festzulegen"}, {"appName","Clientanwendung"},
    {"occUsHead", "Benutzername ist besetzt"}, {"occUs", "Dieser Benutzername wird bereits verwendet. Erstelle einen andere!"},
    {"usnminput","erstellen Sie einen Benutzernamen!"}, {"noName", "Benutzername nicht erstellt"}, {"banned", "Sie sind verbannt, LOL"}, 
    {"banHead", "verbannt"}};
    
    @Override
    protected Object[][] getContents() {
        return this.contents;
    }
    
}
