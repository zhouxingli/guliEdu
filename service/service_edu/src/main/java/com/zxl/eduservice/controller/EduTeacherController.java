package com.zxl.eduservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxl.serviceBase.exceptionhandler.GuliException;
import org.springframework.util.StringUtils;
import com.zxl.commonUtils.R;
import com.zxl.eduservice.entity.EduTeacher;
import com.zxl.eduservice.entity.vo.TeacherQuery;
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
@CrossOrigin //解决跨域问题
public class EduTeacherController {


    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping(value = "/findAllTeacher")
    public R findAllTeacher() {

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "分頁查詢讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable Long current,
                         @PathVariable Long limit) {

        Page<EduTeacher> objectPage = new Page<>(current, limit);
        eduTeacherService.page(objectPage, null);
        long total = objectPage.getTotal();//总记录数
        List<EduTeacher> records = objectPage.getRecords();//数据list集合
       /* Map map=new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);*/
        return R.ok().data("total", total).data("records", records);
    }

    @ApiOperation(value = "多条件查询加分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacher(@PathVariable Long current,
                         @PathVariable Long limit,
                         @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> objectPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //调用方法实现条件查询分页
        eduTeacherService.page(objectPage, wrapper);
        long total = objectPage.getTotal();//总记录数
        List<EduTeacher> records = objectPage.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean saveTeacher = eduTeacherService.save(eduTeacher);
        if (saveTeacher) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id进行查询
    @ApiOperation(value = "根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //讲师修改功能,必须传id
    @ApiOperation(value = "讲师修改功能")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //手动添加异常
    @ApiOperation("手动添加异常")
    @GetMapping("errorTest")
    public R errorTest() {
        int i = 10 / 0;
        return R.ok();
    }

    //自定义异常
    @ApiOperation("自定义异常")
    @GetMapping("errorhandlerTest")
    public R errorhandlerTest() {
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            //执行自定义异常
            throw new GuliException(20001, "执行了自定义异常处理....");
        }
        return R.ok();
    }

}