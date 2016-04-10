package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.util.DBHelper;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NodeDao {


    public List<Node> findAllNode() {
        String sql = "select * from w_node";
        return DBHelper.query(sql,new BeanListHandler<>(Node.class));
    }

    public Node finNodeByNodeid(Integer nodeid) {
        String sql = "select * from w_node where id = ?";
        return DBHelper.query(sql,new BeanHandler<>(Node.class),nodeid);
    }
}
