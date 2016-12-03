//package com.bao.controller;
//
//import com.bao.controller.msg.UrlRequest;
//import com.bao.mapper.UrlInfoMapper;
//import com.bao.model.UrlInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * Created by asus on 2016/6/28.
// */
//@RestController
//public class UrlInfoController {
//    @Autowired
//    private UrlInfoMapper mapper;
//
//    @RequestMapping(value = "/urls" , method = RequestMethod.GET)
//    public List<UrlInfo> getInfo(){
//        return mapper.selectAll();
//    }
//
//    @RequestMapping(value = "/urls/{id}" , method = RequestMethod.POST)
//    public String updateUrlInfo(@PathVariable("id") String id , @RequestBody UrlInfo urlInfo){
//        UrlInfo old =  mapper.findById(Long.parseLong(id));
//        if(null==old){
//            return "not exist";
//        }else{
//            urlInfo.setId(Long.parseLong(id));
//            mapper.update(urlInfo);
//        }
//        return "success";
//    }
//
//    @RequestMapping(value = "/urls/batch" , method = RequestMethod.POST)
//    public String updateBatch(@RequestBody UrlRequest request){
//        List<UrlInfo> urlInfos = request.getData();
//        if(!CollectionUtils.isEmpty(urlInfos)){
//            urlInfos.stream().forEach(node->{
//                UrlInfo old =  mapper.findById(node.getId());
//                if(null!=old){
//                    mapper.update(node);
//                }
//            });
//        }
//        return "success";
//    }
//}
