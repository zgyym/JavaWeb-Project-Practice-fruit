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

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if(fidStr != null|| !"".equals(fidStr)){
            int fid = Integer.parseInt(fidStr);
            FruitDAO fruitDAO = new FruitDAOImpl();
            Fruit fruit = fruitDAO.getFruitById(fid);
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);
        }
    }
}
