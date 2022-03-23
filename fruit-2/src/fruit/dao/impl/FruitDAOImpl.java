package fruit.dao.impl;


import fruit.dao.FruitDAO;
import fruit.pojo.Fruit;
import myssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }

    @Override
    public boolean addFruit(Fruit fruit) {
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        int count = super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark()) ;
        return count>0;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ? , price = ? , fcount = ? , remark = ? where fid = ? " ;
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    @Override
    public Fruit getFruitByFname(String fname) {
        return super.load("select * from t_fruit where fname like ? ",fname);
    }

    @Override
    public boolean delFruit(String fname) {
        String sql = "delete from t_fruit where fname like ? " ;
        return super.executeUpdate(sql,fname)>0;
    }

    @Override
    public Fruit getFruitById(int fid) {
        return super.load("select * from t_fruit where fid = ? ",fid);
    }

    @Override
    public void delFruit(int fid) {
        super.executeUpdate("delete from t_fruit where fid = ?",fid);
    }

    @Override
    public List<Fruit> getFruitList(int pageNo) {
        String sql = "select * from t_fruit limit ? , 3";
        return super.executeQuery(sql,(pageNo-1)*3);
    }

    @Override
    public int getFruitCount() {
        return ((Long) super.executeComplexQuery("select count(*) from t_fruit")[0]).intValue();
    }
}