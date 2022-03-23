package fruit.contrllers;

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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class FruitController extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();


    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、设置编码
        req.setCharacterEncoding("utf-8");
        //2、获取参数
        String fidStr = req.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");
        //3、执行更新
        FruitDAO fruitDAO = new FruitDAOImpl();
        fruitDAO.updateFruit(new Fruit(fid,fname,price,fcount,remark));
        //4.资源跳转
        //super.processTemplate("index",request,response);
        //上面代码相当于request.getRequestDispatcher("index.html").forward(request,response);
        //此处需要重定向，目的是重新给IndexServlet发请求，重新获取furitList，然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        resp.sendRedirect("fruit.do");
    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if(fidStr != null|| !"".equals(fidStr)){
            int fid = Integer.parseInt(fidStr);
            FruitDAO fruitDAO = new FruitDAOImpl();
            Fruit fruit = fruitDAO.getFruitById(fid);
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);
        }
    }

    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if(fidStr != null || !"".equals(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);
            resp.sendRedirect("fruit.do");
        }
    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        fruitDAO.addFruit(new Fruit(0,fname,price,fcount,remark));

        resp.sendRedirect("fruit.do");
    }

    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



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
