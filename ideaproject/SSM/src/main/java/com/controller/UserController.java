package com.controller;

import com.assist.StringUtil;
import com.assist.WebUtils;
import com.bean.*;
import com.github.pagehelper.PageHelper;
import com.service.DepartmentService;
import com.service.MenuService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping(value = "/userController")
public class UserController {
    @Autowired
    private User user;
    @Autowired
    private UserForm userForm;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MenuService menuService;
    @Autowired
    private DepartmentService departmentService;

    private void setmsg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("rid",req.getParameter("id"));
        req.setAttribute("rpwd",req.getParameter("pwd"));
        req.setAttribute("email",req.getParameter("email"));
        req.setAttribute("code",req.getParameter("code"));
    }

    @RequestMapping("/editUser")
    @ResponseBody
    public Map<String,Object> editUser(User user){
        int i=userService.updateUser(user);
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        if (i!=1) {
            flag = false;
        }
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("/returnEditUser")
    public  ModelAndView returnEditUser(){
        String id = request.getParameter("id");
        user.setId(id);
        List<User> userList=userService.selectById(user);
        List<Department> departmentList=departmentService.departmentList();
        User user1=userList.get(0);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user1);
        mv.addObject("departmentList",departmentList);
        mv.setViewName("editUser");
        return mv;
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String,Object> addUser(User user){
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        List<User> userList=userService.selectById(user);
        if (!userList.isEmpty()){
            flag = false;
            map.put("flag",flag);
            return map;
        }
        user.setPwd("888888");
        int i=userService.register(user);
        if (i!=1)
            flag = false;
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("/returnAddUser")
    public ModelAndView returnAddUser(){
        ModelAndView mv=new ModelAndView();
        List<Department> departmentList=departmentService.departmentList();
        mv.addObject("user",user);
        mv.addObject("departmentList",departmentList);
        mv.setViewName("addUser");
        return mv;
    }

    @RequestMapping("/userList")
    public ModelAndView userList(){
        ModelAndView mv = new ModelAndView();
        List<Department> departmentList=departmentService.departmentList();
        mv.addObject("search_user",userForm);
        mv.addObject("departmentList",departmentList);
        mv.setViewName("userlist");
        return mv;
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public Map<String,Object> deleteUser(String id){
        int i=userService.delete(id);
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        if (i!=1)
            flag = false;
        map.put("flag",flag);
        return map;
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ResponseBody
    public String getUserList() throws Exception{
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        if (StringUtil.isEmpty(sort))
            sort="id";
        if(StringUtil.isEmpty(order))
            order="asc";
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtil.isEmpty(page))
            sort="1";
        if(StringUtil.isEmpty(rows))
            order="10";
        Map<String,Object> map = new HashMap<>();
        map.put("sort",sort);
        map.put("order",order);
        map.put("id",id);
        map.put("name",name);
        map.put("department",department);
        int int_page = Integer.parseInt(page);
        int int_rows = Integer.parseInt(rows);
        int totalCount = userService.getTotalCount(map);
        PageHelper.startPage(int_page,int_rows);
        List<User> userList = userService.getUserList(map);
        for (User user:userList) {
            System.out.println(user);
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", totalCount);
        dataMap.put("rows", userList);
        return WebUtils.getDataByCodeAndMsgAndRs("0","ok",dataMap);
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public List<Map<String,Object>> menuList(){
        String menuid = request.getParameter("id");
        User user=(User) session.getAttribute("user");
        List<Menu> menuList=menuService.menuListByUserid(user.getId());
        List<Menu> finalMenuList=new ArrayList<>();
        for (Menu m:menuList) {
            String r_id = m.getId();
            if(r_id.startsWith(menuid)) {
                finalMenuList.add(m);
            }
        }
        Map<String,Object> treeNode =null;
        Map<String,Map<String,Object>> nodeMap = new LinkedHashMap<>();
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (Menu menu:finalMenuList) {
            treeNode = new LinkedHashMap<String,Object>();
            String id = menu.getId();
            String name = menu.getName();
            String parenid = menu.getParentid();
            String url = menu.getUrl();
            int isleaf = menu.getIsleaf();
            treeNode.put("id",id);
            treeNode.put("text",name);
            if(isleaf==1)
                treeNode.put("url",request.getContextPath()+url);
            treeNode.put("isleaf",isleaf);
            nodeMap.put(id,treeNode);
            if(menu.getParentid().equals("0")){
                mapList.add(treeNode);
            }else {
                Map<String,Object> parentNode=nodeMap.get(parenid);
                List<Map<String,Object>> children = null;
                if (parentNode.get("children")==null){
                    children = new ArrayList<>();
                }else {
                    children = (List<Map<String,Object>>)parentNode.get("children");
                }
                children.add(treeNode);
                parentNode.put("children",children);
            }
        }
        return mapList;
    }

    @RequestMapping(value = "/returnlogin")
    private  ModelAndView returnlogin(){
        ModelAndView mv = new ModelAndView();
        Enumeration e=session.getAttributeNames();
        while(e.hasMoreElements()){
            String sessionName=(String)e.nextElement();
            session.removeAttribute(sessionName);
        }
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "/returnfinduppwd",method = RequestMethod.GET)
    private ModelAndView returnfinduppwd(HttpServletRequest req) throws ServletException, IOException{
        ModelAndView mv = new ModelAndView();
        String referer = req.getHeader("referer");
        if(!"https://mail.qq.com/".equals(referer))
            mv.setViewName("login");
        else {
            mv.setViewName("findpassword");
            mv.addObject("id", req.getParameter("id"));
        }
        return mv;
    }

    @RequestMapping(value = "/uppwdcheck",method = RequestMethod.POST)
    private ModelAndView uppwdcheck(HttpServletRequest req) throws ServletException, IOException{
        ModelAndView mv = new ModelAndView();
        user.setId(req.getParameter("id"));
        user.setEmail(req.getParameter("email"));
        boolean flag = userService.uppwdcheck(user);
        if(flag) {
            String url ="http://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/userController/returnfinduppwd?id="+user.getId();
            userService.senduppwd(user.getEmail(),url);
            mv.addObject("selected", 3);
        }
        else {
            mv.addObject("uppwdcheckerrorMessage", "账号或者邮箱错误");
            mv.addObject("selected", 3);
        }
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "/getcode",method = RequestMethod.POST)
    private ModelAndView getcode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ModelAndView mv = new ModelAndView();
        setmsg(req,resp);
        String email=req.getParameter("email");
        String code=userService.getcode(email);
        System.out.println("获取了code");
        HttpSession session = req.getSession();
        session.setAttribute("truecode",code);
        mv.addObject("rid",req.getParameter("rid"));
        mv.addObject("rpwd",req.getParameter("rpwd"));
        mv.addObject("selected",2);
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    private ModelAndView register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ModelAndView mv = new ModelAndView();
        if(!req.getSession().getAttribute("truecode").equals(req.getParameter("code"))) {
            setmsg(req,resp);
            mv.addObject("selected",2);
            mv.addObject("registererrorMessage","验证码错误,请重新获取验证码");
            mv.addObject("rid",req.getParameter("rid"));
            mv.addObject("rpwd",req.getParameter("rpwd"));
            req.getSession().setAttribute("truecode",null);
            mv.setViewName("login");
        }
        else {
            req.getSession().setAttribute("truecode",null);
            user.setId(req.getParameter("rid"));
            user.setPwd(req.getParameter("rpwd"));
            user.setEmail(req.getParameter("email"));
            boolean flag = userService.checkid(user);
            if (flag) {
                setmsg(req,resp);
                mv.addObject("registererrorMessage", "账号已存在");
                mv.addObject("selected", 2);
                mv.addObject("rid",req.getParameter("rid"));
                mv.addObject("rpwd",req.getParameter("rpwd"));
                req.setAttribute("registererrorMessage", "账号已存在");
                mv.setViewName("login");
            } else {
                userService.register(user);
                mv.addObject("selected", 1);
                mv.setViewName("login");
            }
        }
        return mv;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(User user,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        User user2=userService.login(user);
        if (user2==null) {
            mv.addObject("id",user.getId());
            mv.addObject("loginerrorMessage","账号或密码错误");
            mv.setViewName("login");
        } else {
            session.setAttribute("user",user2);
            session.setAttribute("logintype","user");
            mv.setViewName("loginsuccess");
        }
        return mv;
    }

    @RequestMapping(value = "/updatepwd",method = RequestMethod.POST)
    public ModelAndView updatepwd(HttpServletRequest req){
        user.setId(req.getParameter("hid"));
        user.setPwd(req.getParameter("pwd"));
        System.out.println(user);
        userService.updatepwd(user);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    /*@RequestMapping(value = "/returnOk",method = RequestMethod.GET)
    public ModelAndView returnOk(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("test","ok");
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "/returnNo",method = RequestMethod.GET)
    public String returnNo(HttpServletRequest request){
        request.setAttribute("test","No");
        return "index";
    }

    @RequestMapping(value = "/returnYes",method = RequestMethod.GET)
    public ModelAndView returnYes(){
        ModelAndView mv = new ModelAndView();
        RedirectView view = new RedirectView("/index.jsp",true);
        mv.addObject("test","Yes");
        mv.setView(view);
        return  mv;
    }

    @RequestMapping(value = "/returnOh",method = RequestMethod.GET)
    public String returnOh(){
        System.out.println("ok");
        System.out.println(user);
        return "redirect:/index.jsp?test=Oh";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(String id,String pwd){
        ModelAndView mv = new ModelAndView();
        System.out.println("id:"+id);
        System.out.println("pwd:"+pwd);
        mv.setViewName("index2");
        return mv;
    }*/

}
