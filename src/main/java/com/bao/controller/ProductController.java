package com.bao.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bao.controller.msg.DataTableReqInfo;
import com.bao.controller.msg.DataTableRespInfo;
import com.bao.framework.ResponseEntity;
import com.bao.framework.exception.ServiceException;
import com.bao.mapper.ProductMapper;
import com.bao.model.Product;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	private static final String PHOTO_PATH = "/Users/longie/Downloads/";

	private static final String PHOTO_PATH_2 = "/Users/longie/";

	private static final String[] imageTypes = new String[] { "png", "jpg", "jpeg" ,"gif"};

	@Autowired
	private ProductMapper mapper;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String, List<Map<String, Object>>> upload(MultipartHttpServletRequest request) throws Exception {
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
		Collection<List<MultipartFile>> values = multiFileMap.values();
		Iterator<List<MultipartFile>> iterator = values.iterator();
		MultipartFile file = iterator.next().get(0);
		BufferedOutputStream outputStream = null;
		BufferedOutputStream outputStream2 = null;
		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				String uuid = UUID.randomUUID().toString();
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				if (!Arrays.asList(imageTypes).contains(suffix.toLowerCase())) {
					throw new ServiceException(500, "图片上传类型只支持 png,jpg,jpeg");
				}
				String suffixName = uuid + "." + suffix;
				File imageFile = new File(PHOTO_PATH + suffixName);
				File imageFile2 = new File(PHOTO_PATH_2 + suffixName);
				if (!imageFile.getParentFile().exists()) {
					imageFile.getParentFile().mkdirs();
				}
				if (!imageFile2.getParentFile().exists()) {
					imageFile2.getParentFile().mkdirs();
				}
				outputStream = new BufferedOutputStream(new FileOutputStream(imageFile));
				outputStream.write(bytes);
				outputStream2 = new BufferedOutputStream(new FileOutputStream(imageFile2));
				outputStream2.write(bytes);
				Map<String, Object> map = new HashMap<>();
				map.put("name", imageFile.getName());
				map.put("size", imageFile.length());
				map.put("type", suffix);
				map.put("thumbnailUrl", "productImg/" + imageFile.getName());
				list.add(map);
				result.put("files", list);
				return result;
			}
		} catch (Exception e) {
			logger.error("error to saveOrUpdate product", e);
			throw new ServiceException(500, e.getMessage());
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
				if (outputStream2 != null)
					outputStream2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

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
	public ResponseEntity<?> saveOrUpdate(@RequestBody Product product) {

		try {

			if (StringUtils.isBlank(product.getName())) {
				return ResponseEntity.error("产品名称不能为空", null);
			}
			if (StringUtils.isBlank(product.getPrice().toPlainString()) || product.getPrice().doubleValue() < 0) {
				return ResponseEntity.error("价格不能为空或者不能小于0", null);
			}
			if (StringUtils.isBlank(product.getContent())) {
				return ResponseEntity.error("内容不能为空", null);
			}
			if (product.getStock() < 0) {
				return ResponseEntity.error("库存不能小于0", null);
			}
			if (StringUtils.isBlank(product.getModelType())) {
				return ResponseEntity.error("型号不能为空", null);
			}
			if (StringUtils.isBlank(product.getColourType())) {
				return ResponseEntity.error("色号不能为空", null);
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
