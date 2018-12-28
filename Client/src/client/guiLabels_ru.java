package client;

import java.util.ListResourceBundle;

public class guiLabels_ru extends ListResourceBundle{
    
    private Object[][] contents = {{"leftHead", "Фильтр"}, {"rightHead", "Добавление"},
    {"addButton", "Добавить"}, {"resetButton", "Сбросить"}, {"shining", "Сияние"},
    {"Earth", "Видна с Земли"}, {"Moon",
    "Видна с Луны"}, {"colour", "Цвет"},{"choose", "Выберите"}, {"move", "Двигать звезды"}, 
    {"welcome", "Здравствуйте"}, {"usern", "Имя пользователя:"}, {"passwrd", "Пароль"},
    {"sign in", "Войти"}, {"errPassword", "Неверный пароль!"}, {"errPasswordHead", "Отказано в доступе"},
    {"white", "Белый"}, {"yellow", "Желтый"}, {"orange", "Оранжевый"}, {"red", "Красный"}, 
    {"filterButton", "Применить"}, {"name", "Имя"}, {"coordinates", "Координаты"}, 
    {"cancelButton", "Отмена"},{"errHead",
    "Неверный ввод!"}, {"errMsgShine", "'Сияние' должно содержать целочисленные значения!"}, 
    {"errMsgName", "Введите имя новой звезды!"}, {"errMsgEarth", "Укажите видимость с Земли!"}, 
    {"errMsgMoon",
    "Укажите видимость с Луны!"},{"errMsgColour", "Выберите цвет новой звезды!"}, 
    {"addingHead", "Выберите место"}, {"errMsgShineValue", "'Сияние' может принимать значения только между 0 и 5000!"},
    {"addingText", "Кликните на космосе в том месте, где расположится звезда"}, {"appName","Клиентское приложение"},
    {"occUsHead", "Имя занято"}, {"occUs", "Это имя уже занято. Придумайте другое!"},
    {"usnminput","Придумайте имя!"}, {"noName", "Имя пользователя не указано"}, {"banned", "Вас забанили, ЛОЛ"}, 
    {"banHead", "ПОТРАЧЕНО"}};
    
    @Override
    protected Object[][] getContents() {
        return this.contents;
    }
    
}
