package ua.kiev.prog.servlets;

import static java.lang.Byte.parseByte;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

import ua.kiev.prog.Apartment;
import ua.kiev.prog.ApartmentDatabase;
import ua.kiev.prog.ApartmentSelector;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet, that provides the filtered results of apartments.
 *
 * Created by User on 11.07.2014.
 */
public class SelectingServlet extends HttpServlet {

    private static final String HTML_HEAD = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<title>Результаты поиска</title>" +
            "</head>" +
            "<body>" +
            "<table align=\"center\" width=\"100%\">" +
            "  <thead bgcolor=\"#ffcccc\">" +
            "    <tr>" +
            "      <td>Количество комнат</td>" +
            "      <td>Район</td>" +
            "      <td>Адрес</td>" +
            "      <td>Дата размещения</td>" +
            "      <td>Цена</td>" +
            "    </tr>" +
            "  </thead>";

    private static final String HTML_END = "" +
            "</table>" +
            "</body>" +
            "</html>";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(HTML_HEAD);
        ApartmentSelector selector = new ApartmentSelector();
        selector.setMaxPrice(parseDouble(request.getParameter("maxPrice")));
        selector.setMinPrice(parseDouble(request.getParameter("minPrice")));
        selector.setMaxArea(parseFloat(request.getParameter("maxArea")));
        selector.setMinArea(parseFloat(request.getParameter("minArea")));
        selector.setRoomCount(parseByte(request.getParameter("roomCount")));
        selector.setDistrict(request.getParameter("district"));
        ApartmentDatabase db = ApartmentDatabase.getInstance();
        // body
        try {
            List<Apartment> list = db.select(selector);
            printWriter.write("<tbody bgcolor=\"ccffcc\">");
            for (Apartment apartment : list) {
                printWriter.write("<tr>");
                printWriter.write("<td>");
                printWriter.write(String.valueOf(apartment.getRoomCount()));
                printWriter.write("</td><td>");
                printWriter.write(String.valueOf(apartment.getDistrict()));
                printWriter.write("</td><td>");
                printWriter.write(String.valueOf(apartment.getAddress()));
                printWriter.write("</td><td>");
                Date date = apartment.getCreatedDate();
                String formatedDate = DateFormat.getDateInstance().format(date);
                printWriter.write(formatedDate);
                printWriter.write("</td><td>");
                printWriter.write(String.valueOf(apartment.getPrice()));
                printWriter.write("</td></tr>");
            }
            printWriter.write("<tbody>");
        } catch (SQLException e) {
            response.sendError(503, ": Database server not responding.");
        }
        printWriter.write(HTML_END);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }
}
