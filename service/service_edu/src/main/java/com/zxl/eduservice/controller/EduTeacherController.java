package com.zxl.eduservice.controller;



import com.zxl.commonUtils.R;
import com.zxl.eduservice.entity.EduTeacher;
import com.zxl.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {


    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping(value = "/findAllTeacher")
    public R findAllTeacher(){

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag=true){
           return R.ok();
       }else{
           return R.error();
       }
    }

    @ApiOperation(value = "分頁查詢讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable Long current,
                         @PathVariable Long limit){

        Page<EduTeacher> objectPage = new Page<>(current, limit);
        eduTeacherService.page(objectPage, null);
        long total = objectPage.getTotal();//总记录数
        List<EduTeacher> records = objectPage.getRecords();//数据list集合
       /* Map map=new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);*/
       return  R.ok().data("total",total).data("records",records);
    }

}

