package com.hcxinan.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
*@Description 代表了一个excel
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-4-15 下午3:43
*/
public class SheetData {
    private int breakEmptyLineNum=2;//当遇到多少条空行后跳出

    private int sheetIndex;

    private String sheetName;

    private List<SheetTable> tables;

    public SheetData(Sheet sheet) {
        this(0,sheet,null);
    }

    public SheetData(Sheet sheet, Predicate<String[]> emptyRowPredicate) {
        this(0,sheet,emptyRowPredicate);
    }

    public SheetData(int sheetIndex, Sheet sheet,Predicate<String[]> emptyRowPredicate) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheet.getSheetName();
        System.out.println(this.sheetName);

        tables=new ArrayList<>();
        int curEmptyLineNum=0;

        int firstRowNum=sheet.getFirstRowNum();
        int lastRowNum=sheet.getLastRowNum();
        int firstCellNum;
        int lastCellNum;
        int tableIndex=1;
        boolean existTableData=false;//判断是否已经开始
        SheetTable sheetTable=new SheetTable(tableIndex++);
        List<String[]> datas=new ArrayList<>();
        sheetTable.setDatas(datas);
        for(int i=firstRowNum;i<=lastRowNum;i++){
            Row row = sheet.getRow(i);
//            System.out.println("i="+i);
            if(row==null){//遇见空行
                ++curEmptyLineNum;

                //如果已经开始有表数据，再遇见空行，那么意味着表结束，把表数据加入到sheet，再次创建表
                if(existTableData){
                    tables.add(sheetTable);//把之前的表数据加入

//                        System.out.println("=======创建表");
                    //重新创建表
                    existTableData=false;
                    sheetTable=new SheetTable(tableIndex++);
                    datas=new ArrayList<>();
                    sheetTable.setDatas(datas);
                }

                if(curEmptyLineNum>=breakEmptyLineNum){//已经达到了跳出空行的上限,跳出
                    break;
                }else{
                    continue;
                }
            }
            boolean isAllEmptyCell=true;//判断是否全是空的cell(包括有格式但没数据的情况)
            firstCellNum=row.getFirstCellNum();
            lastCellNum=row.getLastCellNum();
            String[] arr=new String[lastCellNum-firstCellNum];
            for(int j=firstCellNum;j<lastCellNum;j++){
                Cell cell=row.getCell(j);
                if(cell==null){
                    arr[j-firstCellNum]="";
                }else{
//                    cell.setCellType(CellType.STRING);//会清空单元格的内容
//                    String value=cell.getStringCellValue();

                    DataFormatter formatter = new DataFormatter();
                    String value=formatter.formatCellValue(cell);//直接获取到单元格的值

                    if(isAllEmptyCell && StringUtils.isNotBlank(value)){
                        isAllEmptyCell=false;
                    }
                    arr[j-firstCellNum]=value;
                }
//                System.out.print("j="+j+"->"+arr[j-firstCellNum]);
            }
            if(!isAllEmptyCell && emptyRowPredicate!=null){
                isAllEmptyCell=emptyRowPredicate.test(arr);
            }
//            System.out.println(Arrays.toString(arr));
//            System.out.println(isAllEmptyCell);
            if(isAllEmptyCell){//全是空
                ++curEmptyLineNum;

                //如果已经开始有表数据，再遇见空行，那么意味着表结束
                if(existTableData){
                    tables.add(sheetTable);//把之前的表数据加入

//                        System.out.println("\n=======创建表");
                    //重新创建表
                    existTableData=false;
                    sheetTable=new SheetTable(tableIndex++);
                    datas=new ArrayList<>();
                    sheetTable.setDatas(datas);
                }
                //已经达到了跳出空行的上限,跳出
                if(curEmptyLineNum>=breakEmptyLineNum)break;
            }else{
                datas.add(arr);
                if(curEmptyLineNum>0){//当前是非空,并且之前有累计空行,一旦遇到非空行,重设为0
                    curEmptyLineNum=0;
                }
                if(!existTableData)existTableData=true;//标识已经遇到表，开始读取数据了
            }

        }
        if(sheetTable!=null && sheetTable.getDatas()!=null){
            tables.add(sheetTable);
        }
    }

    public int getBreakEmptyLineNum() {
        return breakEmptyLineNum;
    }

    public void setBreakEmptyLineNum(int breakEmptyLineNum) {
        this.breakEmptyLineNum = breakEmptyLineNum;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<SheetTable> getTables() {
        return tables;
    }
}
