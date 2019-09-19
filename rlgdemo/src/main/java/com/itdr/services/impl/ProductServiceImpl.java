package com.itdr.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.services.CategoryService;
import com.itdr.services.ProductService;
import com.itdr.utils.PoToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.os.windows.SECURITY_ATTRIBUTES;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    //根据id查询商品
    @Override
    public ServerResponse cxOne(Integer id) {
        Product p = productMapper.cxOne(id);
        ServerResponse sr = ServerResponse.successRS(200, p);
        return sr;
    }

    //根据商品名模糊查询
    @Override
    public ServerResponse cxMore(String name) {
        List<Product> p = productMapper.cxMore(name);
        ServerResponse sr = ServerResponse.successRS(200, p);
        return sr;
    }

    //商品detail
    @Override
    public ServerResponse detail(Integer productId,Integer is_new,Integer is_hot,Integer is_banner) {
        if (productId==null||productId<0){
            return ServerResponse.defeatedRS("非法的参数");
        }
        Product p = productMapper.detail(productId,is_new,is_hot,is_banner);
        if (p == null) {
            return ServerResponse.defeatedRS("商品不存在");
        }
        if (p.getStatus() == 2) {
            return ServerResponse.defeatedRS("该商品已下架");
        }
        ProductVO productVO= null;
        try {
            productVO = PoToVoUtil.ProductToProductVo(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.successRS(productVO);
    }

    //产品搜索及动态排序List
    @Override
    public ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        if ((categoryId==null||categoryId<0)&&keyword==null||"".equals(keyword)){
            return ServerResponse.defeatedRS("非法参数");
        }
        //分割排序参数
        String[] split=new String [2];
        if (!"".equals(orderBy)){
           split = orderBy.split("_");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> li=productMapper.selectByIdOrName(categoryId,keyword,split[0],split[1]);
        PageInfo pf=new PageInfo(li);
        return ServerResponse.successRS(pf);
    }

    //获取产品分类
    @Override
    public ServerResponse topcategory(Integer sid) {
        if (sid == null || sid < 0) {
            return ServerResponse.defeatedRS("非法参数");
        }
        //根据商品分分类id查询子分类
        List<Category> li = categoryMapper.topcategory(sid);
        if (li.size()==0){
            return ServerResponse.defeatedRS("没有子分类");
        }
        return ServerResponse.successRS(li);
    }

}
