package ltd.yuhan.erp.service;


import ltd.yuhan.erp.mapper.*;
import ltd.yuhan.erp.model.Goods;
import ltd.yuhan.erp.model.PoDetail;
import ltd.yuhan.erp.model.WarehouseIn;
import ltd.yuhan.erp.model.WarehouseOut;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Component
public class GoodsService {

	
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private WarehouseInMapper warehouseInMapper;
	@Autowired
	private WarehouseOutMapper warehouseOutMapper;
	@Autowired
	private PoDetailMapper poDetailMapper;
	
//	@Autowired
//	private GoodsCategoryMapper categoryMapper;
	
//
//	public List<Goods> getGoodsListByPage(int pageSize, int pageNo){
//		return goodsMapper.getListByPage(pageSize, pageNo);
//	}
//
//
//	public List<Goods> getGoodsListByCategory(int categoryId) {
//		return goodsMapper.getList(categoryId);
//	}
//
//
//	public List<Goods> getGoodsListByMultIds(int... goodsIds) {
//		return goodsMapper.getListByMultIds(goodsIds);
//	}
	

	public Goods getGoods(int id) {
		return goodsMapper.selectByPrimaryKey(id);
	}

	public List<Goods> getGoodsAllList( ) {
		return goodsMapper.selectAll();
	}


	//根据goodId查询库存数量
	public int getGoodsQty (int goodsId){
		int goodQty = 0;
		List<WarehouseIn> warehouseIns = warehouseInMapper.selectByGoodsId(goodsId);
		for (WarehouseIn in:warehouseIns
			 ) {
			goodQty += in.getQty();

		}
		List<WarehouseOut> warehouseOuts = warehouseOutMapper.selectByGoodsId(goodsId);
		for (WarehouseOut out:warehouseOuts
			 ) {
			goodQty -= out.getQty();
		}

		return goodQty;
	}



	//根据goodsId查询在途未到货商品数量
	//Po 状态是0的
	public int getGoodsInTrans (int goodsId){
		int goodsQty = 0;
		List<PoDetail> poDetails = poDetailMapper.selectPoDetailByGoodsId(goodsId);
		for (PoDetail p:poDetails
			 ) {
			goodsQty += p.getQty();
		}

		return goodsQty;
	}

	//根据title和category查询goods
	public List<Goods> getGoodsByTitileAndCategory( String goodTitle,String category) {
		String[] categorys = category.length()==0?new String[0]:category.split(",");
		return goodsMapper.selectByTitileAndCategory(goodTitle,categorys);
	}



	//根据id查询库存量


//
//	@Transactional
//	@Override
//	public void insertGoods(Goods goods, MultipartFile uploadGoodsPic) {
//		String picPath= saveGoodsPic(uploadGoodsPic);
//		if(picPath!=null && !picPath.isEmpty()) {
//
//			goods.setPicture(picPath);
//		}
//		goodsMapper.insert(goods);
//
//		GoodsCategory gcate= categoryMapper.get(goods.getCategoryId());
//		gcate.setGoodsCount(gcate.getGoodsCount()+1);
//		categoryMapper.update(gcate);
//
//		logger.info("inserted new goods - id:" + goods.getId());
//	}
//
//	@Override
//	public void updateGoods(Goods goods, MultipartFile uploadGoodsPic) {
//		String picPath= saveGoodsPic(uploadGoodsPic);
//		if(picPath!=null && !picPath.isEmpty()) {
//
//			goods.setPicture(picPath);
//		}
//		 goodsMapper.update(goods);
//
//		 logger.info("update goods - id:" + goods.getId());
//	}
//
//	@Transactional
//	@Override
//	public void deleteGoods(int id) {
//		Goods g= goodsMapper.get(id);
//		goodsMapper.delete(g.getId());
//
//		GoodsCategory gcate= categoryMapper.get(g.getCategoryId());
//		gcate.setGoodsCount(gcate.getGoodsCount()-1);
//		categoryMapper.update(gcate);
//
//		//如果有图片，则同时删除图片
//		if(g.getPicture()!=null && !g.getPicture().isEmpty()) {
//
//			String picPath= getRequest().getServletContext().getRealPath("/") + g.getPicture();
//			File file = new File(picPath);
//			if(file.exists()) {
//				file.delete();
//			}
//		}
//
//		logger.info("deleted goods - id:" + g.getId());
//	}
//
//	@Override
//	public List<GoodsCategory> getAllGoodsCategoryList(){
//		return categoryMapper.getAll();
//	}
//
//	@Override
//	public void insertGoodsCategory(GoodsCategory goodsCategory) {
//		categoryMapper.insert(goodsCategory);
//	}
//
//	@Override
//	public void updateGoodsCategory(GoodsCategory goodsCategory) {
//		categoryMapper.update(goodsCategory);
//	}
//
//	@Override
//	public void deleteGoodsCategory(int id) {
//		categoryMapper.delete(id);
//	}
//
//
//	private String saveGoodsPic(MultipartFile uploadGoodsPic) {
//
//		if(uploadGoodsPic==null || uploadGoodsPic.isEmpty()) {
//			return null;
//		}
//
//		String fileName = uploadGoodsPic.getOriginalFilename();
//
//		String extName = fileName.substring(fileName.lastIndexOf("."));
//
//        String newFileName=UUID.randomUUID().toString()+extName;
//		File file = new File(getFileSavePath(newFileName));
//		if(!file.exists()) {
//			file.getParentFile().mkdirs();
//		}
//
//
//		try {
//			uploadGoodsPic.transferTo(file);
//			//return file.toURI().toURL().toString();
//			return getUrlPath(file.getAbsolutePath());
//
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//
//
//	private String getFileSavePath(String fileName) {
//        String realPath =getRequest().getServletContext().getRealPath("/uploadimgs/");
//        return realPath + fileName;
//	}
//
//	private String getUrlPath(String filePath) {
//		String rootPath= getRequest().getServletContext().getRealPath("/");
//		return filePath.replace(rootPath, "").replaceAll("\\\\", "/");
//	}
//
//	private HttpServletRequest getRequest() {
//        HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        return request;
//    }
	
	
}
