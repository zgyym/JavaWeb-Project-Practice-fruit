package fruit.contrllers;

import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;
import fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class FruitController{

    private FruitDAO fruitDAO = new FruitDAOImpl();


    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark){
        //3、执行更新
        FruitDAO fruitDAO = new FruitDAOImpl();
        fruitDAO.updateFruit(new Fruit(fid,fname,price,fcount,remark));
        //4.资源跳转
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
    private String edit(Integer fid,HttpServletRequest request){
        if(fid != null){
            FruitDAO fruitDAO = new FruitDAOImpl();
            Fruit fruit = fruitDAO.getFruitById(fid);
            request.setAttribute("fruit",fruit);
            //super.processTemplate("edit",req,resp);
            return "edit";
        }
        return null;
    }

    private String del(Integer fid){

        if(fid != null){
            fruitDAO.delFruit(fid);
            //resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return null;
    }
    private String add(String fname,Integer price,Integer fcount,String remark){

        fruitDAO.addFruit(new Fruit(0,fname,price,fcount,remark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    private String index(String operator,String keyword,Integer pageNo,HttpServletRequest request){

        HttpSession session = request.getSession();
        if(pageNo == null){
            pageNo = 1;
        }

        if(!(operator == null || "".equals(operator)) && "search".equals(operator)){
            pageNo = 1;
            keyword = request.getParameter("keyword");

            if(keyword == null || "".equals(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        }else{
            String pageNoStr = request.getParameter("pageNo");
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

        //super.processTemplate("index",req, resp);
        return "index";
    }
}
