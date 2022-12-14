package com.tothefor.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tothefor.mapper.MenuMapper;
import com.tothefor.mapper.RoleMenuMapper;
import com.tothefor.mapper.UserMapper;
import com.tothefor.mapper.UserRoleMapper;
import com.tothefor.pojo.dto.CheckURL;
import com.tothefor.pojo.dto.LoginDTO;
import com.tothefor.pojo.dto.RequestReturnInfo;
import com.tothefor.pojo.dto.UserPassword;
import com.tothefor.pojo.entity.*;
import com.tothefor.resultR.R;
import com.tothefor.service.impl.MenuServiceImpl;
import com.tothefor.service.impl.RoleMenuServiceImpl;
import com.tothefor.service.impl.UserRoleServiceImpl;
import com.tothefor.service.impl.UserServiceImpl;
import com.tothefor.utils.JWTUtils;
import com.tothefor.utils.MD5Utils;
import com.tothefor.utils.RequestInfoUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Steven
 * @Date 2022/08/31 15:31
 
 */

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuServiceImpl menuService;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleMenuServiceImpl roleMenuService;



    @PostMapping("/updateOther")
    public R updateOther(@RequestBody UserPassword userPassword){
        Long userid = userPassword.getUserid();
        User user = userMapper.selectById(userid);
        if(user==null){
            return R.FAIL();
        }else {
            String newP = userPassword.getNewP();
            newP = MD5Utils.getMD5(newP);
            user.setPassword(newP);
            userMapper.updateById(user);
            return R.SUCCESS();
        }
    }

    /**
     * @Author Steven
     * @Date 2022/3/13
     
     * @???????????? updatePassword
     * @???????????? ??????????????????
     * @????????????
     * @?????????
     */
    @PostMapping("/updateP")
    public R updatePassword(@RequestBody UserPassword userPassword){
        Long userid = userPassword.getUserid();
        String oldP = userPassword.getOldP();
        oldP = MD5Utils.getMD5(oldP);
        User user = userMapper.selectById(userid);
        if(user.getPassword().equals(oldP)){ //???????????????,?????????????????????
            String newP = userPassword.getNewP();
            newP = MD5Utils.getMD5(newP);
            user.setPassword(newP);
            userMapper.updateById(user);
            return R.SUCCESS();
        }else {
            return R.FAIL();
        }
    }

    /**
     * @Author Steven
     * @Date 2022/3/13
     
     * @???????????? getUserIdByToken
     * @???????????? ??????token????????????id
     * @????????????
     * @?????????
     */
    @PostMapping("/getUserId")
    public R getUserIdByToken(@RequestBody String token){
        String idByToken = JWTUtils.getIdByToken(token);
        Long integer = Long.valueOf(idByToken);
        return R.SUCCESS(integer);
    }

    /**
     * @Author Steven
     * @Date 2022/3/13
     
     * @???????????? getT
     * @???????????? ?????????????????????????????????
     * @????????????
     * @?????????
     */
    @GetMapping("/getUserInfo")
    public R getT(HttpServletRequest request){
        String ip = RequestInfoUtils.getIp(request);
        String browserName = RequestInfoUtils.getBrowserName(request);
        String browserVersion = RequestInfoUtils.getBrowserVersion(request);
        String osName = RequestInfoUtils.getOsName(request);
        RequestReturnInfo requestReturnInfo = new RequestReturnInfo();
        requestReturnInfo.setIp(ip);
        requestReturnInfo.setBrowserName(browserName);
        requestReturnInfo.setBrowserVersion(browserVersion);
        requestReturnInfo.setOsName(osName);
        return R.SUCCESS(requestReturnInfo);
    }

    /**
     * ??????token??????????????????????????????
     * @param token
     * @return
     */
    @PostMapping("/getUserNameByToken")
    public R getUserName(@RequestBody String token){
        return R.SUCCESS(JWTUtils.getNameByToken(token));
    }

    /**
     * ????????????????????????????????????
     */
    @PostMapping("/checkURL")
    public R checkUserURL(@RequestBody CheckURL checkURL){
        String userToken = checkURL.getToken();
        String nameByToken = JWTUtils.getNameByToken(userToken);
        String urlpath = checkURL.getUrlpath();
        List<Menu> menus = userService.menuByName(nameByToken);
        for(Menu it : menus){
            List<Menu> children = it.getChildren();
            for(Menu subit : children){
                if(subit.getMenuPath().equals(urlpath)){
                    return R.SUCCESS();
                }
            }
        }
        return R.FAIL();
    }

    /**
     * ??????token????????????????????????
     */
    @PostMapping("/getMenu")
    public R getMenus(@RequestBody String token){
        String nameByToken = JWTUtils.getNameByToken(token);
        List<Menu> menus = userService.menuByName(nameByToken);
        return R.SUCCESS(menus);
    }

    /**
     * ??????token
     */
    @PostMapping("/checkToken")
    public R checkToken(@RequestBody String token){
        if(JWTUtils.checkToken(token)){
            return R.SUCCESS();
        }else{
            return R.FAIL();
        }
    }


    /**
     * @Author Steven
     * @Date 2022/3/12 20:01
     
     * @?????? itemAll
     * @?????? ????????????
     * @????????????
     * @return
     */
    @PostMapping("/login")
    public R userLogin(@RequestBody LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        password = MD5Utils.getMD5(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if(user.getPassword().equals(password)){ //????????????
            //??????token
            Map<String,String> hm = new HashMap<>();
            hm.put("username",username);
            hm.put("userid",String.valueOf(user.getId()));
            String token = JWTUtils.getToken(hm);
            loginDTO.setUserToken(token);

            loginDTO.setMenus(userService.menuByName(username));
            return R.SUCCESS(loginDTO);
        }else {
            return R.FAIL();
        }

    }


    /**
     * @Author Steven
     * @Date 2022/09/01 17:01
     
     * @?????? itemAll
     * @?????? ??????????????????????????????
     * @????????????
     * @return
     */
    @GetMapping("/userAll")
    public R<List<User>> userAll(){

        return R.SUCCESS(userMapper.selectList(null));
    }

    /**
     * @Author Steven
     * @Date 2022/09/01 17:02
     
     * @?????? itemSave
     * @?????? ?????????????????????
     * @????????????
     * @return
     */
    @PostMapping("/save")
    public R userSave(@RequestBody User item){

        if(userService.saveOrUpdate(item)){
            return R.SUCCESS();
        }else {
            return R.FAIL();
        }
    }

    /**
     * @Author Steven
     * @Date 2022/09/01 17:06
     
     * @?????? itemDelete
     * @?????? ??????id????????????
     * @????????????
     * @return
     */
    @DeleteMapping("/del/{id}")
    public R userDelete(@PathVariable Integer id){
        if(userMapper.deleteById(id)==1){
            return R.SUCCESS();
        }else{
            return R.FAIL();
        }
    }

    /**
     * @Author Steven
     * @Date 2022/09/01 17:11
     
     * @?????? itemBatchDelete
     * @?????? ????????????
     * @????????????
     * @return
     */
    @PostMapping("/batch/del")
    public R userBatchDelete(@RequestBody List<Integer> ids){
        int len = ids.size();
        if(userMapper.deleteBatchIds(ids)==len){
            return R.SUCCESS();
        }else {
            return R.FAIL();
        }
    }

    /**
     * @Author Steven
     * @Date 2022/3/7 12:37
     
     * @?????? PageitemAll
     * @?????? ?????????????????????
     * @????????????
     * @return
     */
    @GetMapping("/pageAll")
    public R<IPage<User>> PageUserAll(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("PageSize") Integer pageSize,
                                      @RequestParam(defaultValue = "") String itemname,
                                      @RequestParam(defaultValue = "0") Integer searchtype){


        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.hasText(itemname)){
            queryWrapper.like("username",itemname);
        }
        if(searchtype!=0){
            queryWrapper.eq("role_ID",searchtype);
        }
        queryWrapper.ne("id",1);
        queryWrapper.orderByDesc("id");


        IPage<User> page1 = userService.page(page, queryWrapper);
        List<User> records = page1.getRecords();
        for(User it: records){
            Long typeid = it.getRoleId();
            QueryWrapper<UserRole> qw = new QueryWrapper();
            qw.eq("id",typeid);
            UserRole userRole = userRoleMapper.selectOne(qw);
            it.setRoleName(userRole.getRolename());
        }
        return R.SUCCESS(page1);
    }

    /**
     * @Author Steven
     * @Date 2022/3/9 17:13
     
     * @?????? export
     * @?????? ????????????
     * @????????????
     * @return
     */
    @GetMapping("/export")
    public void exportU(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        String token = request.getHeader("token");

        List<Map<String, Object>> list = CollUtil.newArrayList();
        List<User> all = userMapper.selectList(null);

        for(User it : all){
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("???????????????",it.getId());
            row1.put("????????????",it.getUserid());
            row1.put("????????????",it.getUsername());
            row1.put("????????????",it.getRoleId());
            row1.put("????????????",it.getDescription());

            list.add(row1);
        }

        // 2. ???excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("????????????", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);


    }

    /**
     * @Author Steven
     * @Date 2022/3/9 17:13
     
     * @?????? imp
     * @?????? ????????????
     * @????????????
     * @return
     */
    @PostMapping("/import")
    public R imp(MultipartFile file) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<User> items = CollUtil.newArrayList();
        try {

            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            List<List<Object>> list = reader.read(1);

            System.out.println(list.size());
            for (List<Object> row : list) {
                User item = new User();
                item.setUserid(row.get(0).toString());
                item.setUsername(row.get(1).toString());
                item.setRoleId(Long.valueOf(row.get(2).toString()));
                item.setDescription(row.get(3).toString());

                items.add(item);
            }
        }catch (Exception e){
            return R.FAIL();
        }

        if(userService.saveBatch(items)){
            return R.SUCCESS();
        }else{
            return R.FAIL();
        }
    }


    /**
     * @Author Steven
     * @Date 2022/3/10 10:30
     
     * @?????? exportMB
     * @?????? ??????????????????
     * @????????????
     * @return
     */
    @GetMapping("/exportMB")
    public void exportMB(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        String token = request.getHeader("token");

        List<Map<String, Object>> list = CollUtil.newArrayList();
        User it = new User();
        it.setUserid("????????????????????????");
        it.setUsername("????????????????????????,????????????????????????????????????????????????????????????");
        it.setRoleId(2L);
        it.setDescription("?????????????????????????????????");


        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("????????????",it.getUserid());
        row1.put("????????????",it.getUsername());
        row1.put("????????????",it.getRoleId());
        row1.put("????????????",it.getDescription());

        list.add(row1);


        // 2. ???excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("????????????", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);


    }

}
