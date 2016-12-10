package com.bao.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bao.controller.msg.DataTableReqInfo;
import com.bao.controller.msg.DataTableRespInfo;
import com.bao.framework.ResponseEntity;
import com.bao.mapper.ProductMapper;
import com.bao.model.Product;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	private static final String PHOTO_PATH = "/Users/longie/";

	@Autowired
	private ProductMapper mapper;

	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public DataTableRespInfo getInfo(@RequestBody String aoData) throws Exception {
		try {
			JSONArray jsonArray = new JSONArray(URLDecoder.decode(aoData, "utf8").replaceAll("aoData=", ""));
			JSONObject jsonObject;
			DataTableReqInfo dataTableReqInfo = new DataTableReqInfo();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				if ("iDisplayLength".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setiDisplayLength(jsonObject.getInt("value"));
				}
				if ("iDisplayStart".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setiDisplayStart(jsonObject.getInt("value"));
				}
				if ("sEcho".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setsEcho(jsonObject.getInt("value"));
				}
				if ("sSearch".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setsSearch(jsonObject.getString("value"));
				}
			}
			List<Map<String, Object>> resultList = mapper.selectAll(dataTableReqInfo);
			DataTableRespInfo dataTableRespInfo = new DataTableRespInfo();
			dataTableRespInfo.setAaData(resultList);
			int count = mapper.countAllProducts(dataTableReqInfo);
			dataTableRespInfo.setiTotalDisplayRecords(count);
			dataTableRespInfo.setiTotalRecords(count);
			dataTableRespInfo.setsEcho(dataTableReqInfo.getsEcho());
			return dataTableRespInfo;
		} catch (Exception e) {
			logger.error("error to get all info", e);
			throw e;
		}
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestParam("picUrl") MultipartFile file, HttpServletRequest request) {

		try {
			String id = request.getParameter("id");
			String price = request.getParameter("price");
			String content = request.getParameter("content");
			String name = request.getParameter("name");
			String stock = request.getParameter("stock");
			String modelType = request.getParameter("modelType");
			String colourType = request.getParameter("colourType");

			if (StringUtils.isBlank(name)) {
				return ResponseEntity.error("产品名称不能为空", null);
			}
			if (StringUtils.isBlank(price) || Double.valueOf(price) < 0) {
				return ResponseEntity.error("价格不能为空或者不能小于0", null);
			}
			if (StringUtils.isBlank(content)) {
				return ResponseEntity.error("内存不能小于0", null);
			}
			if (StringUtils.isBlank(stock) || Integer.parseInt(stock) < 0) {
				return ResponseEntity.error("库存不能小于0", null);
			}
			if (StringUtils.isBlank(modelType)) {
				return ResponseEntity.error("型号不能为空", null);
			}
			if (StringUtils.isBlank(colourType)) {
				return ResponseEntity.error("色号不能小于0", null);
			}
			Product product = new Product();
			product.setId(id == null ? 0L : Long.parseLong(id));
			product.setName(name);
			product.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
			product.setContent(content);
			product.setModelType(modelType);
			product.setColourType(colourType);
			product.setStock(Integer.parseInt(stock));
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				File imageFile = new File(PHOTO_PATH + UUID.randomUUID().toString());
				if (!imageFile.getParentFile().exists()) {
					imageFile.getParentFile().mkdirs();
				}
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imageFile));
				outputStream.write(bytes);
				outputStream.close();
				product.setPicUrl(imageFile.getPath());
			} else {
				if (id == null) {
					return ResponseEntity.error("产品图片未上传", null);
				}
			}
			return ResponseEntity.success(mapper.saveOrUpdate(product));
		} catch (Exception e) {
			logger.error("error to saveOrUpdate product", e);
			return ResponseEntity.error("保存或更新失败", e);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		try {
			return ResponseEntity.success(mapper.deleteById(Long.parseLong(id)));
		} catch (Exception e) {
			logger.error("error to delete product", e);
			return ResponseEntity.error("删除产品失败", e);
		}
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInfoById(@PathVariable("id") String id) {
		return ResponseEntity.success(mapper.selectById(Long.parseLong(id)));
	}

}
