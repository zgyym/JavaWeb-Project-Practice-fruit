package fruit.servlet;

import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO= new FruitDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if(fidStr != null || !"".equals(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);
            resp.sendRedirect("index");
        }
    }
}
