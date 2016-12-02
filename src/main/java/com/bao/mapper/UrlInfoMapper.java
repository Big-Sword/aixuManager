package com.bao.mapper;

/**
 * @author gupei
 * @date 2016/3/25.
 * @Description Get transform template by hospital Id from DB
 *
 */

import com.bao.model.UrlInfo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UrlInfoMapper {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<UrlInfo> selectAll() {
        return this.sqlSessionTemplate.selectList("selectAll");
    }

    public int update(UrlInfo urlInfo) {
        return this.sqlSessionTemplate.update("updateUrlInfo", urlInfo);
    }

    public UrlInfo findById(Long id) {
        return this.sqlSessionTemplate.selectOne("selectOne", id);
    }

}
