package com.bao.controller.msg;

import com.bao.model.UrlInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by baochunyu on 2016/6/30.
 */
@Data
public class UrlRequest {
    private List<UrlInfo> data;
}
