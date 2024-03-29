package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TBContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容管理Service
 *
 * @author lijun
 * @version V1.0
 * @package_name: com.taotao.portal.service.impl
 * @date 19-2-21 下午4:19
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_URL_AD1_CID}")
	private String REST_CONTENT_URL_AD1_CID;

	@Override
	public String getAd1List() {
		// 调用服务获得数据
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_URL_AD1_CID);
		// 把json转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToList(json, TBContent.class);
		// 取data属性，内容列表
		List<TBContent> contentList = (List<TBContent>) taotaoResult.getData();
		// 把内容列表转换成AdNode列表
		List<AdNode> resultList = new ArrayList<>();
		for (TBContent tbContent : contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(tbContent.getPic());

			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(tbContent.getPic2());
			node.setAlt(tbContent.getSubTitle());
			node.setHref(tbContent.getUrl());
			resultList.add(node);
		}
		// 把resultList转换成json
		return JsonUtils.objectToJson(resultList);
	}
}
