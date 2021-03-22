package com.cjt.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelImportEntity;
import cn.afterturn.easypoi.handler.inter.IReadHandler;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.ToString;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2019-10-25 17:02
 */
@RunWith(JUnit4.class)
public class ClassTest {


    @Test
    public void testImport() throws Exception {
        InputStream is = new FileInputStream(new File("./tempaltefortest1.xlsx"));
        ImportParams params = new ImportParams();
        String[] importFields = {"名称1","性别"};
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedVerify(true);
        params.setImportFields(importFields);


        List<Test1> list = ExcelImportUtil.importExcel(is,Test1.class,params);
        System.out.println(list);
    }


    @Test
    //失败
    public void testmergeRely() throws IOException {
        OutputStream os = new FileOutputStream(new File("./test2.xlsx"));
        List<Test2> list = new ArrayList<>();

        Test2 test2 = new Test2();
        test2.setStr1("test1");
        test2.setStr2a("test2a");
//        test2.setStr2b("test2b");
//        test2.setStr2c("test2c");
        list.add(test2);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),Test2.class,list);
        workbook.write(os);
    }

    @Test
    public void testnull() throws IOException {
        OutputStream os = new FileOutputStream(new File("./test3.xlsx"));
        List<Test2> list = null;

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),Test2.class,list);
        workbook.write(os);
    }


    @Test
    public void test(){
        ExcelExportEntity excelExportEntity = new ExcelExportEntity();
    }

    @Test
    public void test5() throws IOException {
        OutputStream os = new FileOutputStream(new File("./定制化模板1.xls"));
        List<ExcelExportEntity> entityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelExportEntity excelExportEntity = new ExcelExportEntity();
            excelExportEntity.setWidth(25);
            excelExportEntity.setName("col" + i);
            //excelExportEntity.setOrderNum(new Random().nextInt(20));  //orderNum用于列的排序
            excelExportEntity.setOrderNum(i + 100);  //orderNum用于列的排序
            excelExportEntity.setKey("");  //如果导入的list是map的话需要指定这个key，这个key对应map的key
            entityList.add(excelExportEntity);
        }

        ExportParams exportParams = new ExportParams();
        exportParams.setTitle("定制化导入模板");
//        CellStyle cellStyle = new XSSFCellStyle(new StylesTable());
//        cellStyle.setba
//        exportParams.setStyle();
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entityList, new ArrayList<>());
        workbook.write(os);
    }

    @Test
    public void test6() throws UnsupportedEncodingException {
        String str = "哈哈哈哈";
        byte[] strByteArr = str.getBytes();
        String isoStr = new String(str.getBytes(), "ISO8859-1");
        byte[] isoStrByteArrForUTF = isoStr.getBytes();
        byte[] isoStrByteArrForISO = isoStr.getBytes("ISO8859-1");

        String enCodeStr = URLEncoder.encode("哈哈哈哈", "UTF-8");
        byte[] enCodeStrUTF = enCodeStr.getBytes("UTF-8");
        byte[] enCodeStrISO = enCodeStr.getBytes("ISO8859-1");
        String deCodeStr = URLDecoder.decode(enCodeStr, "UTF-8");
        System.out.println(Arrays.toString(strByteArr));
        System.out.println(isoStr);
        System.out.println(Arrays.toString(isoStrByteArrForUTF));
        System.out.println(Arrays.toString(isoStrByteArrForISO));
        System.out.println(enCodeStr);
        System.out.println(Arrays.toString(enCodeStrUTF));
        System.out.println(Arrays.toString(enCodeStrISO));
        System.out.println(deCodeStr);

        System.out.println(Hex.encodeHexString(strByteArr));
    }

    @Test
    public void test7() throws Exception {
        InputStream is = new FileInputStream(new File("./定制化模板1.xls"));
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        /*
        --->开始解析excel返回Map类型的list集合；
        当返回类型为Map时，key为改列对应的head值，value为单元格内容，且有一个注意点为：在集合中的除最后一个map外，
        其余map都存在一个key为excelRowNum的值，这个是easyPoi生成的表示当前map（行）在所导入的excel中位于（真实）第几行
         */
        List<Map> list = ExcelImportUtil.importExcel(is, Map.class, params);
        System.out.println(list);
    }

    @Test
    public void test7a() throws Exception {
        InputStream is = new FileInputStream(new File("./7a.xls"));
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        /*
        --->开始解析excel返回Map类型的list集合；
        当返回类型为Map时，key为改列对应的head值，value为单元格内容，且有一个注意点为：在集合中的除最后一个map外，
        其余map都存在一个key为excelRowNum的值，这个是easyPoi生成的表示当前map（行）在所导入的excel中位于（真实）第几行
         */
        List<ExcelImportEntity> excelImportEntities = new ArrayList<>();
        List<Map> list = ExcelImportUtil.importExcel(is,excelImportEntities.getClass(),params);
        System.out.println(list);
    }

    @Test
    public void test8() throws IOException {
        OutputStream os = new FileOutputStream(new File("./定制化模板1.xls"));
        List<ExcelExportEntity> entityList = new ArrayList<>();
        List<Map<String, String>> dataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelExportEntity excelExportEntity = new ExcelExportEntity();
            String name = "col" + i;
            excelExportEntity.setWidth(25);
            excelExportEntity.setName(name);
            //excelExportEntity.setOrderNum(new Random().nextInt(20));  //orderNum用于列的排序
            excelExportEntity.setOrderNum(i + 100);  //orderNum用于列的排序
            excelExportEntity.setKey(name);  //如果导入的list是map的话需要指定这个key，这个key对应map的key
            entityList.add(excelExportEntity);

            Map<String, String> map = new HashMap<>();
            for (int k = 0; k < 10; k++) {
                map.put("col" + k, "value" + k);

            }
            dataSet.add(map);
        }

        ExportParams exportParams = new ExportParams();
        exportParams.setTitle("定制化导入模板");

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entityList, dataSet);
        workbook.write(os);
    }


    @Test
    public void testSheet() throws IOException {
        OutputStream os = new FileOutputStream(new File("./testSheet.xls"));

        List<Map<String,Object>> sheetList = new ArrayList<>();
        ExportParams exportParams1 = new ExportParams();
        exportParams1.setSheetName("sheet1");
        Map<String,Object> sheet1 = new HashMap<>();
        sheet1.put("title",exportParams1);
        sheet1.put("entity",Test1.class);
        sheet1.put("data",new ArrayList<>());
        sheetList.add(sheet1);

        ExportParams exportParams2 = new ExportParams();
        exportParams2.setSheetName("sheet2");
        Map<String,Object> sheet2 = new HashMap<>();
        sheet2.put("title",exportParams2);
        sheet2.put("entity",Test2.class);
        sheet2.put("data",new ArrayList<>());
        sheetList.add(sheet2);
        Workbook workbook2 = ExcelExportUtil.exportExcel(sheetList, ExcelType.HSSF);
        workbook2.write(os);
    }

    @Test
    public void test9(){
        String sjiachun = "12345E-10";

        BigDecimal db = new BigDecimal(sjiachun);
        String ii = db.toPlainString();
        System.out.println(ii);
    }

    @Test
    public void test10() {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            long start = new Date().getTime();
            ExcelImportUtil.importExcelBySax(
                    new FileInputStream(
//                            new File("./a.xlsx")),
                            new File("./test2.xlsx")),
                    Map.class, params, new IReadHandler<Map>() {
                        @Override
                        public void handler(Map o) {
                            System.out.println(ReflectionToStringBuilder.toString(o));
                        }

                        @Override
                        public void doAfterAll() {

                        }
                    });
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
