package fruit.contrllers;

import fruit.pojo.Fruit;
import fruit.service.FruitService;
import fruit.service.impl.FruitServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class FruitController{

    private FruitService fruitService = new FruitServiceImpl();


    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark){
        //3、执行更新
        fruitService.updateFruit(new Fruit(fid,fname,price,fcount,remark));
        //4.资源跳转
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
    private String edit(Integer fid,HttpServletRequest request){
        if(fid != null){
            Fruit fruit = fruitService.getFruitById(fid);
            request.setAttribute("fruit",fruit);
            //super.processTemplate("edit",req,resp);
            return "edit";
        }
        return null;
    }

    private String del(Integer fid){

        if(fid != null){
            fruitService.delFruit(fid);
            //resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return null;
    }
    private String add(String fname,Integer price,Integer fcount,String remark){

        fruitService.addFruit(new Fruit(0,fname,price,fcount,remark));
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

        List<Fruit> fruitList = fruitService.getFruitList(keyword,pageNo);
        session.setAttribute("fruitList",fruitList);

        int pageCount = fruitService.getPageCount(keyword);
        session.setAttribute("pageCount",pageCount);

        //super.processTemplate("index",req, resp);
        return "index";
    }
}
