package ltd.yuhan.erp.service;

import ltd.yuhan.erp.mapper.*;
import ltd.yuhan.erp.model.*;
import ltd.yuhan.erp.model.vo.GoodsInfoVo;
import ltd.yuhan.erp.model.vo.PoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Component
public class PoService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private PoDetailMapper poDetailMapper;
    @Autowired
    private PoMapper poMapper;
    @Autowired
    private SerialNumberMapper numberMapper;
    @Autowired
    private WarehouseInMapper inMapper;

    public List<GoodsCategory> getCategoty(){
        return goodsCategoryMapper.selectAll();
    }

    public List<PoVo> getPoAndDetail(String status, String goodsTitle, String category, String orderId){
        List<Po> pos = poMapper.getPoByTerm(status,goodsTitle,category,orderId);
        List<PoVo> vos = new ArrayList<>();
        for(Po po : pos){
            PoVo vo = new PoVo(po);
            List<Map> details = poDetailMapper.selectPoDetailByPoId(po.getId());
            List<WarehouseIn> ins = inMapper.selectByPoId(Integer.valueOf(po.getId()));
            for(Map map : details){
                long qty =(Integer) map.get("qty");
                long received = 0;
                if (ins.size()>0) {
                    for (WarehouseIn in : ins){
                        if (String.valueOf(in.getGoodsid()).equals(String.valueOf(map.get("goodsId")))) {
                            received = received + in.getQty();
                        }
                    }
                }else {
                    map.put("received", 0);
                }
                map.put("received", received);
                map.put("overQty",(qty-received)<0?0:(qty-received));
            }
            vo.setPoDetails(details);
            vos.add(vo);
        }
        return vos;
    }

    public PoVo savePo(List<GoodsInfoVo> list){
        Date current = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        StringBuffer orderId =new StringBuffer("YH");
        SerialNumber number =  numberMapper.getById(1);
        int num = number.getSerialNum();
        number.setSerialNum(num+1);
        orderId.append(String.format("%4d", num).replace(" ", "0")).append(format.format(current));
        numberMapper.updateById(number);
        Po po = new Po();
        po.setUser("user");
        po.setStatus(0);
        po.setCreatetime(current);
        po.setOrderId(new String(orderId));
        List<PoDetail> details = new ArrayList<>();
        for(GoodsInfoVo vo : list){
            if(vo.getQty()<1) continue;
            PoDetail detail =new PoDetail();
            po.setTotalprice(po.getTotalprice().add(vo.getPrice().multiply(new BigDecimal(vo.getQty()))));
            po.setTotalqty(po.getTotalqty()+vo.getQty());
            detail.setGoodsid(vo.getId());
            detail.setQty(vo.getQty());
            detail.setTotalprice(vo.getPrice().multiply(new BigDecimal(vo.getQty())));
            detail.setCreatetime(current);
            details.add(detail);
        }
        poMapper.insert(po);
        for (PoDetail detail : details){
            detail.setPoid(po.getId());
            poDetailMapper.insert(detail);
        }
        PoVo pv = new PoVo(po);
        return pv;
    }

    public List<Map> getPoDetailVo(String poId){
        List<WarehouseIn> ins = inMapper.selectByPoId(Integer.valueOf(poId));
        List<Map> details = poDetailMapper.selectPoDetailByPoId(poId==null?null:Integer.valueOf(poId));
        for(Map map : details){
            long qty =(Integer) map.get("qty");
            long received = 0;
            if (ins.size()>0) {
                for (WarehouseIn in : ins){
                    if (String.valueOf(in.getGoodsid()).equals(String.valueOf(map.get("goodsId")))) {
                        received = received + in.getQty();
                    }
                }
            }else {
                map.put("received", 0);
            }
            map.put("received", received);
            map.put("overQty",(qty-received)<0?0:(qty-received));
        }
         return details;
    }
    private String buildOrderId(){
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        Date curr = new Date(System.currentTimeMillis());
        return "";
    }

//    public static void main(String[] args) {
//        String str = String.format("%4d", 112).replace(" ", "0");
//        System.out.println(str);
//    }
}
