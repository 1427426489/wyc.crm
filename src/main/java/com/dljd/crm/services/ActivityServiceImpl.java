package com.dljd.crm.services;

import com.dljd.crm.beans.ActRemark;
import com.dljd.crm.beans.Activity;
import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.User;
import com.dljd.crm.mapper.ActivityMapper;
import com.dljd.crm.util.LocalDateTimeUtil;
import com.dljd.crm.util.UUIDUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private HttpSession session;

    @Override
    public Page getSome(Page page) {
        int rowsPerPage = page.getRowsPerPage();
        int beginRow = (page.getCurrentPage() - 1) * rowsPerPage;
        List<Activity> activities = activityMapper.getSome(beginRow, rowsPerPage,page.getSearchMap());
        page.setData(activities);
        //查询的总行数应该带有查询条件
        int totalRows = activityMapper.getCount(page.getSearchMap());
        page.setTotalRows(totalRows);
        int totalPages = totalRows / rowsPerPage;
        if (totalRows % rowsPerPage != 0) {
            totalPages += 1;
        }
        page.setTotalPages(totalPages);
        return page;
    }

    @Override
    public Activity get(String id) {
        return activityMapper.get(id);
    }

    @Override
    public List<Activity> getAll() {
        return activityMapper.getAll();
    }

    @Override
    public int add(Activity param) {
        //获取创建人信息
        User user = (User) session.getAttribute("user");
        //获取创建时间
        String nowTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        param.setId(UUIDUtil.getUUID());
        param.setCreateBy(user.getName());
        param.setCreateTime(nowTime);
        return activityMapper.add(param);
    }

    @Override
    public int delete(String[] ids) {
        return activityMapper.delete(ids);
    }

    @Override
    public int update(Activity param) {
        User user = (User) session.getAttribute("user");
        String nowTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        param.setEditBy(user.getName());
        param.setEditTime(nowTime);
        return activityMapper.update(param);
    }

    @Override
    public Workbook actExport() {
        //设置表头数据
        String[] title = {"名称","所有者","开始时间","结束时间","预算","描述"};
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //3.创建表头(第一行)
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        //4.在excel表头中插入表头数据
        for (int i = 0; i < title.length; i++) {
            //在第一行创建第i个单元格
            cell = row.createCell(i);
            //设置第i个单元格的值
            cell.setCellValue(title[i]);
        }

        //5.从数据库查询数据
        List<Activity> activities = activityMapper.getAll();

        //6.将数据追加到excel表中
        //遍历查询结果数据，在excel表的每行的对应单元中填充数据
        for (int i = 0; i < activities.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            Activity activity = activities.get(i);
            row1.createCell(0).setCellValue(activity.getName());
            row1.createCell(1).setCellValue(activity.getOwner());
            row1.createCell(2).setCellValue(activity.getStartDate());
            row1.createCell(3).setCellValue(activity.getEndDate());
            row1.createCell(4).setCellValue(activity.getCost());
            row1.createCell(5).setCellValue(activity.getDescription());
        }
        return workbook;
    }

    @Override
    public void actImport(MultipartFile file) throws IOException {
        User user = (User) session.getAttribute("user");
        String createBy = user.getName();
        String createTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        List<Activity> activities = new ArrayList<>();
        //1.通过指定文件的输入流创建workbook对象(excel文件对象)
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        //2.获取workbook对象中的第一个表单
        HSSFSheet sheet = workbook.getSheetAt(0);
        //3.遍历表单中的所有行,除去第一行标题，遍历其他所有行
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            //获取第i行对象
            HSSFRow row = sheet.getRow(i);
            //将每一列的数据存储到activity对象中
            Activity activity = new Activity();
            activity.setId(UUIDUtil.getUUID());
            activity.setName(row.getCell(0).getStringCellValue());
            activity.setOwner(row.getCell(1).getStringCellValue());
            activity.setStartDate(row.getCell(2).getStringCellValue());
            activity.setEndDate(row.getCell(3).getStringCellValue());
            activity.setCost(row.getCell(4).getStringCellValue());
            activity.setDescription(row.getCell(5).getStringCellValue());
            activity.setCreateBy(createBy);
            activity.setCreateTime(createTime);
            activities.add(activity);
        }
        //将数据添加到数据库中
        activityMapper.addAll(activities);
    }

    @Override
    public Activity getRemark(String id) {
        return activityMapper.getRemark(id);
    }

    @Override
    public int addActRemark(ActRemark actRemark) {
        actRemark.setId(UUIDUtil.getUUID());
        User user = (User) session.getAttribute("user");
        actRemark.setNotePerson(user.getName());
        String noteTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        actRemark.setNoteTime(noteTime);
        actRemark.setEditFlag(0);
        System.out.println(actRemark);
        int i = activityMapper.addActRemark(actRemark);
        System.out.println(i);
        return i;
    }

    @Override
    public int updateActRemark(ActRemark actRemark) {
        User user = (User) session.getAttribute("user");
        actRemark.setEditPerson(user.getName());
        String editTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        actRemark.setEditTime(editTime);
        actRemark.setEditFlag(1);
        return activityMapper.updateActRemark(actRemark);
    }

    @Override
    public int deleteActRemark(String id) {
        return activityMapper.deleteActRemark(id);
    }

    @Override
    public List<Activity> getLikeByName(String actName) {
        return activityMapper.getLikeByName(actName);
    }
}
