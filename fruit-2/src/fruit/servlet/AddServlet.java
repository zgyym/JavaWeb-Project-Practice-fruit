package fruit.servlet;

import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        fruitDAO.addFruit(new Fruit(0,fname,price,fcount,remark));

        resp.sendRedirect("index");
    }
}
