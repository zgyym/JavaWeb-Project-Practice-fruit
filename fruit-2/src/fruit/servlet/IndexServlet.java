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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pageNo = 1;
        String pageNoStr = req.getParameter("pageNo");
        if(!(pageNoStr == null || "".equals(pageNoStr))){
            pageNo = Integer.parseInt(pageNoStr);
        }

        HttpSession session = req.getSession();
        session.setAttribute("pageNo",pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(pageNo);

        session.setAttribute("fruitList",fruitList);

        int fruitCount = fruitDAO.getFruitCount();
        int pageCount = (fruitCount + 3 - 1) / 3;

        session.setAttribute("pageCount",pageCount);

        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html

        //处理模板
        super.processTemplate("index",req, resp);
    }
}
