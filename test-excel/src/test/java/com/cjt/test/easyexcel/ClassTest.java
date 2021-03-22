package com.cjt.test.easyexcel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
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
    public void test1() throws FileNotFoundException {


        ExcelWriterBuilder excelWriterBuilder = EasyExcelFactory.write();

        List<String> row1Head = new ArrayList<>();
        row1Head.add("col1");

        List<String> row2Head = new ArrayList<>();
        row2Head.add("col2");

        List<String> row3Head = new ArrayList<>();
        row3Head.add("col3");
        List<List<String>> title = new ArrayList<>();
        title.add(row1Head);
        title.add(row2Head);
        title.add(row3Head);

        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<String> rowData = new ArrayList<>();
            rowData.add("col1Data");
            rowData.add("col2Data");
            rowData.add("col3Data");
            data.add(rowData);
        }

        Map<Integer, Integer> colWidthMap = new HashMap<>();
        colWidthMap.put(0, 10 * 1000);


        File file = new File("./a.xlsx");

        OutputStream os = new FileOutputStream(file);

        excelWriterBuilder.head(title);
        excelWriterBuilder.build();
        excelWriterBuilder.file(os);
        excelWriterBuilder.excelType(ExcelTypeEnum.XLSX);

        WriteSheet writeSheet = new WriteSheet();
        writeSheet.setNeedHead(true);
        writeSheet.setColumnWidthMap(colWidthMap);
        writeSheet.setSheetNo(10);
        writeSheet.setSheetName("测试sheetName");

        ExcelWriter writer = excelWriterBuilder.build();
        writer.write(data, writeSheet);
        writer.finish();

    }


    @Test
    public void test() throws FileNotFoundException {
        List<String> row1Head = new ArrayList<>();
        row1Head.add("col1");

        List<String> row2Head = new ArrayList<>();
        row2Head.add("col2");

        List<String> row3Head = new ArrayList<>();
        row3Head.add("col3");
        List<List<String>> title = new ArrayList<>();
        title.add(row1Head);
        title.add(row2Head);
        title.add(row3Head);

        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<String> rowData = new ArrayList<>();
            rowData.add("col1Data");
            rowData.add("col2Data");
            rowData.add("col3Data");
            data.add(rowData);
        }

        File file = new File("./b.xlsx");

        OutputStream os = new FileOutputStream(file);

        EasyExcel.write(os).head(title).sheet().doWrite(data);
    }

    @Test
    public void test2() {
        EasyExcel.read();
    }

    @Test
    public void test3() throws FileNotFoundException {
        List<List<String>> colName = new ArrayList<>();
        Map map = new HashMap();
        for (int i = 0; i < 10; i++) {
            List<String> head = new ArrayList<>();
            head.add("col" + i);
            colName.add(head);
            map.put(i, 25 * 256);
        }


        OutputStream os = new FileOutputStream(new File("./定制化模板.xlsx"));
        ExcelWriterBuilder writerBuilder = EasyExcel.write(os);
        writerBuilder.head(colName);

        ExcelWriterSheetBuilder writerSheetBuilder = writerBuilder.sheet();

        WriteSheet writeSheet = writerSheetBuilder.build();
        writeSheet.setColumnWidthMap(map);
        ExcelWriter writer = writerBuilder.build();
        writer.write(null, writeSheet);
        writer.finish();
    }

    @Test
    public void test4() throws FileNotFoundException {
        List<List<String>> colName = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            List<String> head = new ArrayList<>();
            head.add("col" + i);
            colName.add(head);
        }

        OutputStream os = new FileOutputStream(new File("./定制化模板.xlsx"));
        EasyExcel.write(os).head(colName).sheet().doWrite(null);
    }




    @Test
    public void test9() {
        OK:
        for (int i = 0; i < 5; i++) {

            for (int k = 0; k < 5; k++) {
                System.out.println("k" + k + "," + i);
                if (i == 3) continue OK;


            }
            System.out.println(i);
        }
    }

    @Test
    public void test10() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        System.out.println(map.remove("key1"));
        System.out.println(map.remove("key2"));
    }

    @Test
    public void test11() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        String[] strArr1 = new String[3];

        String[] strArr2 = list.toArray(strArr1);

        System.out.println(Arrays.toString(strArr1));
        System.out.println(Arrays.toString(strArr2));
    }

    @Test
    public void test12() throws FileNotFoundException {


        //模拟传输过来的潜客名单
        List<GuestBatch> list = new ArrayList<>();

        String testBigdataGuestGroupId = null;
        String testBIgdataGuestName = null;
        for (int i = 0; i < 10; i++) {
            int n = i % 2;
            boolean bool = n == 0;
            int level = (i % 3) + 1;
            int factor = bool ? 1 : 2;
            String groupName = bool ? "丰田" : "威马";
            String city = bool ? "北京" : "上海";
            String batchId = String.valueOf(i);


            GuestBatch guestBatch = new GuestBatch();
            guestBatch.setGuestBatchName(groupName + "-" + city + "-" + level + "-0");
            guestBatch.setGuestBatchState(0);
            guestBatch.setGuestBatchId(batchId);
            guestBatch.setGuestListCount(level * 1000 / factor);
            guestBatch.setGuestAssignCount(level * 10 / factor);

            list.add(guestBatch);
        }


        List<List<String>> head = new ArrayList<>();         //声明excel表头
        Map<Integer, Integer> colWidth = new HashMap<>();    //声明excel列宽
        List<String> head0 = new ArrayList<>();
        head0.add("车企品牌");
        head.add(head0);
        colWidth.put(0, 12 * 256);
        List<String> head1 = new ArrayList<>();
        head1.add("城市（潜客级别）");
        head.add(head1);
        colWidth.put(1, 25 * 256);
        List<String> head2 = new ArrayList<>();
        head2.add("总号源");
        head.add(head2);
        colWidth.put(2, 10 * 256);
        List<String> head3 = new ArrayList<>();
        head3.add("已分配号源");
        head.add(head3);
        colWidth.put(3, 15 * 256);
        List<String> head4 = new ArrayList<>();
        head4.add("剩余号源");
        head.add(head4);
        colWidth.put(4, 12 * 256);


        OutputStream os = new FileOutputStream(new File("./guestBatch.xlsx"));
        ExcelWriterBuilder writerBuilder = EasyExcel.write(os).head(head);
        ExcelWriter excelWriter = writerBuilder.excelType(ExcelTypeEnum.XLSX).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("批次详情").needHead(true).build();
        writeSheet.setColumnWidthMap(colWidth);


        Map<String, List<GuestBatch>> batchMap = new HashMap<>();  //key:groupName,value：batchList
        if (list != null) {

            Map<String, GuestBatch> batchNameMap = new HashMap<>();
            for (GuestBatch guestBatch : list) {
                String batchName = guestBatch.getGuestBatchName();
                String batchNameNoNum = batchName.substring(0, batchName.lastIndexOf("-"));

                Integer listCount = guestBatch.getGuestListCount();
                Integer assignCount = guestBatch.getGuestAssignCount();

                if (batchNameMap.containsKey(batchNameNoNum)) {
                    GuestBatch sumBatch = batchNameMap.get(batchNameNoNum);
                    sumBatch.setGuestListCount(sumBatch.getGuestListCount() + listCount);
                    sumBatch.setGuestAssignCount(sumBatch.getGuestAssignCount() + assignCount);
                } else {
                    batchNameMap.put(batchNameNoNum, guestBatch);
                }

            }

            for (GuestBatch guestBatch : batchNameMap.values()) {
                String batchName = guestBatch.getGuestBatchName();
                String[] batchInfoArr = batchName.split("-");
                String groupName = batchInfoArr[0];

                if (batchMap.containsKey(groupName)) {
                    List<GuestBatch> batchList = batchMap.get(groupName);
                    batchList.add(guestBatch);
                } else {
                    List<GuestBatch> batchList = new ArrayList<>();
                    batchList.add(guestBatch);
                    batchMap.put(groupName, batchList);
                }
            }

            final Integer[] i = {0};

            batchMap.forEach((groupName, batchList) -> {

                List<List<Object>> data = new ArrayList<>();
                int listCountSum = 0;
                int assignCountSum = 0;
                int unAssignCountSum = 0;
                for (GuestBatch guestBatch : batchList) {
                    String batchName = guestBatch.getGuestBatchName();
                    Integer listCount = guestBatch.getGuestListCount();
                    Integer assignCount = guestBatch.getGuestAssignCount();
                    int unAssignCount = listCount - assignCount;
                    String[] batchInfoArr = batchName.split("-");
                    String city = batchInfoArr[1];
                    String level = null;
                    if (batchInfoArr[2] != null) {
                        int levelNum = Integer.parseInt(batchInfoArr[2]);
                        switch (levelNum) {
                            case 1:
                                level = "高";
                                break;
                            case 2:
                                level = "中";
                                break;
                            case 3:
                                level = "低";
                                break;
                            default:
                                level = null;
                        }
                    }

                    List<Object> rowData = new ArrayList<>();
                    rowData.add(groupName);
                    rowData.add(city + "（" + level + "）");
//                    rowData.add(String.valueOf(listCount));
//                    rowData.add(String.valueOf(assignCount));
//                    rowData.add(String.valueOf(unAssignCount));
                    rowData.add(listCount);
                    rowData.add(assignCount);
                    rowData.add(unAssignCount);
                    data.add(rowData);

                    listCountSum += listCount;
                    assignCountSum += assignCount;
                    unAssignCountSum += unAssignCount;
                }

                List<Object> rowData = new ArrayList<>();
                rowData.add("");
                rowData.add("合计");
//                rowData.add(String.valueOf(listCountSum));
//                rowData.add(String.valueOf(assignCountSum));
//                rowData.add(String.valueOf(unAssignCountSum));
                rowData.add(listCountSum);
                rowData.add(assignCountSum);
                rowData.add(unAssignCountSum);

                data.add(rowData);

                WriteTable writeTable = EasyExcel.writerTable(i[0]++).needHead(false).registerWriteHandler(new LoopMergeStrategy(data.size(), 0)).build();
                excelWriter.write(data, writeSheet, writeTable);
            });


            excelWriter.finish();

        }
    }


    @Test
    public void testSheet() throws FileNotFoundException {
        File file = new File("./testSheetEasyExcel.xlsx");

        OutputStream os = new FileOutputStream(file);
        ExcelWriter excelWriter = EasyExcel.write(os).build();

        WriteSheet writeSheet1 = new WriteSheet();
        writeSheet1.setSheetName("sheet1");
        writeSheet1.setSheetNo(10);


        excelWriter.write(new ArrayList(),writeSheet1);


        WriteSheet writeSheet2 = new WriteSheet();
        writeSheet2.setSheetName("sheet2");
        writeSheet2.setSheetNo(20);

        excelWriter.write(new ArrayList(),writeSheet2);

        excelWriter.finish();
    }

    @Test
    public void exportForAnnotation() throws FileNotFoundException {
        File file = new File("./testAnnotationExcel.xlsx");

        OutputStream os = new FileOutputStream(file);
        ExcelWriter excelWriter = EasyExcel.write(os).build();

        List<Test3> data = new ArrayList<>();
        for (int i = 0;i<5;i++){
            Test3 test3 = new Test3();
            test3.setName("name"+i);
            test3.setAge(i);
            test3.setPass("pass"+i);

            data.add(test3);
        }

        WriteSheet sheet = new WriteSheet();
        sheet.setSheetName("sheet1");
        sheet.setSheetNo(1);
        sheet.setClazz(Test3.class);

        excelWriter.write(data,sheet);
        excelWriter.finish();
    }

    @Test
    public void saxExport(){
        EasyExcel.read(new File("./test2.xlsx"), new AnalysisEventListener() {
            @Override
            public void invokeHeadMap(Map headMap, AnalysisContext context) {
                System.out.println(headMap.toString());
            }
            @Override
            public void invoke(Object data, AnalysisContext context) {
                System.out.println(data.toString());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                System.out.println(context);
            }
        }).build().read();
    }

}
