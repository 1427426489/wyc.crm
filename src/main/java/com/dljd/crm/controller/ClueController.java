package com.dljd.crm.controller;

import com.dljd.crm.beans.*;
import com.dljd.crm.services.ClueService;
import com.dljd.crm.services.TypeService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clue")
public class ClueController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/indexView")
    public String indexView(Model model){
        //加载线索页面中相关的字典类型的字典值
        Type source = typeService.get("source");
        model.addAttribute("sourceList",source.getValues());
        Type clueState = typeService.get("clueState");
        model.addAttribute("clueStateList",clueState.getValues());
        Type application = typeService.get("application");
        model.addAttribute("appellationList",application.getValues());
        return "workbench/clue/index";
    }

    @RequestMapping("/getAll.json")
    @ResponseBody
    public Object getAll(Page page){
        return clueService.getSome(page);
    }

    @RequestMapping("/get.json")
    @ResponseBody
    public Object getJson(String id){
        return clueService.get(id);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object saveDo(Clue clue){
        User user = (User) session.getAttribute("user");
        clue.setCreateBy(user.getName());
        clueService.add(clue);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Object deleteDo(String[] ids){
        clueService.delete(ids);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Object updateDo(Clue clue){
        User user = (User) session.getAttribute("user");
        clue.setEditBy(user.getName());
        clueService.update(clue);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/import.do")
    @ResponseBody
    public Object importDo(MultipartFile file) throws IOException {
        clueService.importDo(file);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/export.do")
    public String exportDo() throws IOException {
        String[] fields = {"名称","公司","公司座机","手机","线索来源","所有者","线索状态"};
        Workbook workbook = clueService.exportDo(fields);
        //通过响应对象的输出流，将Excel文件对象输出到硬盘上
        ServletOutputStream outputStream = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition","attachment;filename=clue.xls");
        response.setContentType("application/msexcel");
        workbook.write(outputStream);
        outputStream.close();
        return "redirect:/clue/indexView";
    }


    /**
     * 备注相关请求
     */

    @RequestMapping("/detailView")
    public String detailView(String id){
        request.setAttribute("id",id);
        return "forward:/WEB-INF/jsp/workbench/clue/detail.jsp";
    }

    @RequestMapping("/getRemarks.json")
    @ResponseBody
    public Object getRemarks(String id){
        return clueService.getRemarks(id);
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Object saveRemark(ClueRemark clueRemark){
        User user = (User) session.getAttribute("user");
        //判断传递的数据中id是否为null
        if (clueRemark.getId()==null){
            //id为null表示添加操作
            clueRemark.setNotePerson(user.getName());
            clueService.addRemark(clueRemark);
        }else {
            //id不为null表示修改操作
            clueRemark.setEditPerson(user.getName());
            clueService.updateRemark(clueRemark);
        }
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/delRemark.do")
    @ResponseBody
    public Object delRemark(String id){
        clueService.deleteRemark(id);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    /**
     * 线索关联活动相关请求
     */
    @RequestMapping("/getActivities.json")
    @ResponseBody
    public Object getActivities(String id){
        return clueService.getActivities(id);
    }

    //解除线索与市场活动的关联
    /**delete请求方式表示请求服务器删除Request-URI标识的资源
     *      使用URI传递参数，在控制器方法中需要使用@PathVariable注解标识参数
     */
    @RequestMapping(value = "/unbind/{clueId}/{actId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object unbind(@PathVariable("clueId") String clueId, @PathVariable("actId") String actId){
        clueService.unbind(clueId,actId);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/bind.do")
    @ResponseBody
    public Object bindDo(String clueId,String[] actIds){
        clueService.bind(clueId,actIds);
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    @RequestMapping("/convertView")
    public String convertView(Model model){
        model.addAttribute("stageList",typeService.get("stage").getValues());
        return "workbench/clue/convert";
    }

    @RequestMapping("/convert.do")
    @ResponseBody
    public Map<String,Object> convertDo(String clueId,Transaction transaction){
        User user = (User) session.getAttribute("user");
        String createBy = user.getName();
        clueService.convert(clueId,transaction,createBy);
        return new HashMap<String, Object>(){{
            put("success",true);
        }};
    }
}
