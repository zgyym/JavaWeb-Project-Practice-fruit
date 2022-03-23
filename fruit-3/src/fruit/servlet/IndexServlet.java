package fruit.servlet;

import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int pageNo = 1;
        HttpSession session = req.getSession();

        String operator = req.getParameter("operator");

        String keyword = null;

        if(!(operator == null || "".equals(operator)) && "search".equals(operator)){
            pageNo = 1;

            keyword = req.getParameter("keyword");

            if(keyword == null || "".equals(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        }else{
            String pageNoStr = req.getParameter("pageNo");
            if(!(pageNoStr == null || "".equals(pageNoStr))){
                pageNo = Integer.parseInt(pageNoStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if(keywordObj != null){
                keyword = (String) keywordObj;
            }else{
                keyword = "";
            }

        }
        // 重新更新当前页的值
        session.setAttribute("pageNo",pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);

        session.setAttribute("fruitList",fruitList);

        int fruitCount = fruitDAO.getFruitCount(keyword);
        int pageCount = (fruitCount + 3 - 1) / 3;

        session.setAttribute("pageCount",pageCount);

        super.processTemplate("index",req, resp);
    }
}
