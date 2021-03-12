package ltd.yuhan.erp;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;
import com.alibaba.product.param.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import ltd.yuhan.erp.mapper.GoodsMapper;
import ltd.yuhan.erp.model.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class GoodsCateoryListTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void getGoodsList() throws JsonProcessingException {
        //用来存放抓出来的商品信息
        Goods goods = new Goods();
        //一些暂时拿不到，数据库里要求不为null的先赋为0
        goods.setPrice(BigDecimal.valueOf(0));
        goods.setLonger(BigDecimal.valueOf(0));
        goods.setWide(BigDecimal.valueOf(0));
        goods.setHigh(BigDecimal.valueOf(0));
        goods.setWeight(BigDecimal.valueOf(0));
        //设置appkey和密钥(seckey)
        ApiExecutor apiExecutor = new ApiExecutor("4728391","eCrmFhKuzd");


        AlibabaProductListGetParam alibabaProductListGetParam = new AlibabaProductListGetParam();
        alibabaProductListGetParam.setPageNo(1);
        alibabaProductListGetParam.setPageSize(20);
        SDKResult<AlibabaProductListGetResult> execute = apiExecutor.execute(alibabaProductListGetParam, "0d2e156a-5883-4705-8001-4897f1565337");

        AlibabaProductProductInfo[] resultList = execute.getResult().getResult().getPageResult().getResultList();

        for (AlibabaProductProductInfo product:resultList
             ) {

            String[] images = product.getImage().getImages();
            //商品名称
            String goodTitle = product.getSubject();
            //图片地址，可能有多个，先拿一个
            String imageadress = images[0];
            Long categoryID = product.getCategoryID();
            String categoryName = product.getCategoryName();
            Long productID = product.getProductID();
            //同一个商品可能有多个颜色或者大小，就会有多个SKuinfo,如果只有一种，则skuinfo有可能为null的
            String referencePrice = product.getReferencePrice();
            AlibabaProductProductSaleInfo saleInfo = product.getSaleInfo();
            AlibabaProductProductPriceRange[] priceRanges = saleInfo.getPriceRanges();
            AlibabaProductProductSKUInfo[] skuInfos = product.getSkuInfos();
            if(skuInfos != null) {
                for (AlibabaProductProductSKUInfo sku : skuInfos
                ) {
                    //skuId是唯一的，对应到Goods表中的id
                    Long skuId = sku.getSkuId();
                    //如果sku的价格和product一致，它会为空
                    Double price = sku.getPrice();
                    AlibabaProductSKUAttrInfo[] attributes = sku.getAttributes();
                    StringBuffer goodsIntr = new StringBuffer();
                    //sku的描述，可能有多少，全部抓出来，拼接在goodsintr里面

                    for (AlibabaProductSKUAttrInfo attribute : attributes
                    ) {
                        Long attributeID = attribute.getAttributeID();
                        String attributeName = attribute.getAttributeName();
                        String attributeValue = attribute.getAttributeValue();


                        goodsIntr.append(attributeName);
                        goodsIntr.append(":");
                        goodsIntr.append(attributeValue);
                        goodsIntr.append(",");

                    }
                    System.out.println("------");
                    System.out.println("商品id为" + skuId);
                    System.out.println("商品title为" + goodTitle);
                    System.out.println("商品图片地址为" + imageadress);

                    goods.setPicture(imageadress);
                    //价格均为1688上的参考价格，而goods里的价格为成本，这里全部拼到introduction里
                    if(price != null){
                        System.out.println("商品价格为" + price);
                        goodsIntr.append("1688参考价："+price);

                    }else
                    {
                        System.out.println("商品价格为" +referencePrice);
                        goodsIntr.append("1688参考价："+referencePrice);
                    }

                    System.out.println("商品描述" + goodsIntr.toString());
                    System.out.println("商品类别id为" + categoryID);
                    //对必须的项目进行set
                    goods.setId(skuId);
                    goods.setTitle(goodTitle);
                    goods.setIntroduction(goodsIntr.toString());
                    goods.setCategoryid(Math.toIntExact(categoryID));
                    goodsMapper.insert(goods);

                }
            }else{
                System.out.println("*****");
                System.out.println("商品id为" + productID);
                System.out.println("商品title为" + goodTitle);
                System.out.println("商品图片地址为" + imageadress);
                System.out.println("商品价格为" +referencePrice);
                System.out.println("商品描述" + goodTitle);
                System.out.println("商品类别id为" + categoryID);
                //对必须的项目进行set
                goods.setId(productID);
                goods.setTitle(goodTitle);
                goods.setPicture(imageadress);
                goods.setIntroduction("1688参考价："+referencePrice);
                goods.setCategoryid(Math.toIntExact(categoryID));
                goodsMapper.insert(goods);



            }
        }


    }
}
