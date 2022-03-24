package fruit.dao;

import fruit.pojo.Fruit;

import java.sql.SQLException;
import java.util.List;

public interface FruitDAO {
    //查询库存列表
    List<Fruit> getFruitList();

    //新增库存
    boolean addFruit(Fruit fruit);

    //修改库存
    void updateFruit(Fruit fruit);

    //根据名称查询特定库存
    Fruit getFruitByFname(String fname);

    //删除特定库存记录
    boolean delFruit(String fname);

    Fruit getFruitById(int fid);

    void delFruit(int fid);

    List<Fruit> getFruitList(String keyword,int pageNo);


    int getFruitCount(String keyword);
}
