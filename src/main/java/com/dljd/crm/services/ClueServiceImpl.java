package com.dljd.crm.services;

import com.dljd.crm.beans.*;
import com.dljd.crm.mapper.ClueMapper;
import com.dljd.crm.mapper.ContactsMapper;
import com.dljd.crm.mapper.CustomerMapper;
import com.dljd.crm.mapper.TransactionMapper;
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
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public Clue get(String id) {
        return clueMapper.get(id);
    }

    @Override
    public List<Clue> getAll() {
        return clueMapper.getAll();
    }

    @Override
    public Page getSome(Page page) {
        int currentPage = page.getCurrentPage();
        int rowsPerPage = page.getRowsPerPage();
        int beginRow = (currentPage - 1) * rowsPerPage;
        page.setData(clueMapper.getSome(beginRow, rowsPerPage, page.getSearchMap()));
        int totalRows = clueMapper.getCount(page.getSearchMap());
        page.setTotalRows(totalRows);
        int totalPages = totalRows % rowsPerPage == 0 ? (totalRows / rowsPerPage) : (totalRows / rowsPerPage) + 1;
        page.setTotalPages(totalPages);
        return page;
    }

    @Override
    public void importDo(MultipartFile file) throws IOException {
        List<Clue> clues = new ArrayList<>();
        String createTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        User user = (User) session.getAttribute("user");
        String createBy = user.getName();
        //通过输入流获取Excel文件对象
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        //通过Excel文件对象获取表单
        HSSFSheet sheet = workbook.getSheetAt(0);
        //从表单第二行开始遍历表单
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            HSSFRow row = sheet.getRow(i);
            Clue clue = new Clue();
            String name = row.getCell(0).getStringCellValue();
            //截取名称中最后两个字符为称呼，其他字符为姓名
            String fullName = name.substring(0, name.length() - 2);
            String appellation = name.substring(name.length() - 2);
            clue.setFullName(fullName);
            clue.setAppellation(appellation);
            clue.setCompany(row.getCell(1).getStringCellValue());
            clue.setPhone(row.getCell(2).getStringCellValue());
            clue.setMphone(row.getCell(3).getStringCellValue());
            clue.setSource(row.getCell(4).getStringCellValue());
            clue.setOwner(row.getCell(5).getStringCellValue());
            clue.setState(row.getCell(6).getStringCellValue());
            clue.setId(UUIDUtil.getUUID());
            clue.setCreateBy(createBy);
            clue.setCreateTime(createTime);
            clues.add(clue);
        }
        clueMapper.addAll(clues);
    }

    @Override
    public Workbook exportDo(String[] fields) {
        //创建一个Excel文件对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel表单对象
        HSSFSheet sheet = workbook.createSheet();
        //创建表头
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            row.createCell(i).setCellValue(fields[i]);
        }
        //从数据库查询数据并将数据添加到表单对象中
        List<Clue> clues = clueMapper.getAll();
        for (int i = 0; i < clues.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            //拼接姓名和称呼
            String name = clues.get(i).getFullName() + clues.get(i).getAppellation();
            row1.createCell(0).setCellValue(name);
            row1.createCell(1).setCellValue(clues.get(i).getCompany());
            row1.createCell(2).setCellValue(clues.get(i).getPhone());
            row1.createCell(3).setCellValue(clues.get(i).getMphone());
            row1.createCell(4).setCellValue(clues.get(i).getSource());
            row1.createCell(5).setCellValue(clues.get(i).getOwner());
            row1.createCell(6).setCellValue(clues.get(i).getState());
        }
        return workbook;
    }

    @Override
    public int add(Clue param) {
        param.setId(UUIDUtil.getUUID());
        String createTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        param.setCreateTime(createTime);
        return clueMapper.add(param);
    }

    @Override
    public int delete(String[] ids) {
        return clueMapper.delete(ids);
    }

    @Override
    public int update(Clue param) {
        String editTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        param.setEditTime(editTime);
        return clueMapper.update(param);
    }

    @Override
    public List<ClueRemark> getRemarks(String id) {
        return clueMapper.getRemarks(id);
    }

    @Override
    public int addRemark(ClueRemark clueRemark) {
        String noteTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        clueRemark.setId(UUIDUtil.getUUID());
        clueRemark.setNoteTime(noteTime);
        clueRemark.setEditFlag(0);
        return clueMapper.addRemark(clueRemark);
    }

    @Override
    public int updateRemark(ClueRemark clueRemark) {
        String editTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        clueRemark.setEditTime(editTime);
        clueRemark.setEditFlag(1);
        return clueMapper.updateRemark(clueRemark);
    }

    @Override
    public int deleteRemark(String id) {
        return clueMapper.deleteRemark(id);
    }

    @Override
    public List<Activity> getActivities(String id) {
        return clueMapper.getActivities(id);
    }

    //解除线索与市场活动的关联
    @Override
    public int unbind(String clueId, String actId) {
        return clueMapper.deleteClueActRelation(clueId, actId);
    }

    @Override
    public int bind(String clueId, String[] actIds) {
        //先删除关联
        clueMapper.deleteByClueId(clueId);

        //再添加新的关联
        int count = 0;
        for (String actId : actIds) {
            ClueActivity clueActivity = new ClueActivity();
            clueActivity.setId(UUIDUtil.getUUID());
            clueActivity.setClueId(clueId);
            clueActivity.setActivityId(actId);
            clueMapper.addClueActivity(clueActivity);
            count++;
        }
        return count;
    }

    //转换功能
    @Override
    public int convert(String clueId, Transaction transaction, String createBy) {
        int count = 0;
        //查询线索
        Clue clue = clueMapper.get(clueId);
        String nowTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        //添加客户
        Customer customer = new Customer();
        String customerId = UUIDUtil.getUUID();
        customer.setId(customerId);
        customer.setOwner(clue.getOwner());
        customer.setName(clue.getCompany());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        customer.setDescription(clue.getDescription());
        customer.setCreateBy(createBy);
        customer.setCreateTime(nowTime);
        count += customerMapper.add(customer);

        //添加联系人
        Contacts contacts = new Contacts();
        String contactsId = UUIDUtil.getUUID();
        contacts.setId(contactsId);
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setAppellation(clue.getAppellation());
        contacts.setFullName(clue.getFullName());
        contacts.setEmail(clue.getEmail());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customerId);
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(nowTime);
        count += contactsMapper.add(contacts);

        //判断用户是否创建交易
        //添加交易
        if (transaction.getName() != null) {
            transaction.setId(UUIDUtil.getUUID());
            transaction.setOwner(clue.getOwner());
            transaction.setCustomerId(customerId);
            transaction.setSource(clue.getSource());
            transaction.setType("新业务");
            transaction.setContactsId(contactsId);
            transaction.setDescription(clue.getDescription());
            transaction.setCreateBy(createBy);
            transaction.setCreateTime(nowTime);
            count += transactionMapper.add(transaction);
        }

        //删除线索、线索备注和线索关联活动表的数据
        count += clueMapper.deleteByClueId(clueId);
        count += clueMapper.deleteRemarkByClueId(clueId);
        count += clueMapper.delete(new String[]{clueId});
        return count;
    }

}
