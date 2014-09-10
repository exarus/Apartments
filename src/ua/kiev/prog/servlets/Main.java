package ua.kiev.prog.servlets;

import ua.kiev.prog.Apartment;
import ua.kiev.prog.ApartmentDatabase;
import ua.kiev.prog.ApartmentSelector;

import java.sql.SQLException;

/**
 * Created by User on 12.07.2014.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        try (ApartmentDatabase a = ApartmentDatabase.getInstance()){
            a.add(new Apartment("Святошинський", "вул. Зодчих 11, кв 49<sub>a</sub>",
                    222, (byte) 3, 4550.99));
            a.add(new Apartment("Дарницький", "вул. Драгоманова 51, кв. 93<sub>a</sub>",
                    432, (byte) 5, 9000.40));
            a.add(new Apartment("Святошинський", "вул. Жолудєва 13, кв 1<sub>a</sub>",
                    361, (byte) 3, 6400.11));
            a.add(new Apartment("Святошинський", "б-р. Кольцова 11<sub>a</sub>, кв. 10",
                    151, (byte) 2, 2145.99));
            a.add(new Apartment("Шевченківський", "пр. Перемоги 21, кв. 21 1<sub>a</sub>",
                    411, (byte) 5, 7140.00));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
