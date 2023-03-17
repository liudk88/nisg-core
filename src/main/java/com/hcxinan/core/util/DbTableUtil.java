package com.hcxinan.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.morph.db.AbsDbTabel;
import com.morph.db.IColumn;
import com.morph.db.IDGenerator;
import com.morph.db.ITable;
import com.morph.db.h2.H2Column;
import com.morph.db.h2.H2Table;
import com.morph.db.impl.Column;
import com.morph.db.impl.ColumnType;
import com.morph.db.impl.MoTabel;
import com.morph.db.mysql.MysqlColumn;
import com.morph.db.mysql.MysqlTable;

import com.morph.util.TableDiff;
import com.morph.util.TableUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DbTableUtil {
    private static final Logger logger = Logger.getLogger(DbTableUtil.class);

    private String process(InputStream input) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(input));
        String line=null;
        StringBuilder sb=new StringBuilder();

        while((line = br.readLine())!=null && line.length() !=0) {
//            System.out.println(line);
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public void syncTables(Connection connection, Resource[] resources,boolean isMysql) throws IOException, SQLException {
        int len=resources.length;
        if(len>0){
            InputStream[] inputStreams=new InputStream[resources.length];
            for(int i=0;i<len;i++){
                inputStreams[i]=resources[i].getInputStream();
            }
            syncTables(connection,inputStreams,isMysql);
        }
    }

    public void syncTables(Connection connection, InputStream[] inputStreams,boolean isMysql) throws IOException, SQLException {
        logger.info("正在同步数据库表结构...");

        List<String> jsonstrs=new ArrayList<>();

        for(InputStream in:inputStreams){
            jsonstrs.add(process(in));

        }
        List<String> sqls = new ArrayList<>();
        if (jsonstrs.size() > 0) {
            for (String jsonstr : jsonstrs) {
                if(StringUtils.isBlank(jsonstr)){
                    continue;
                }
                ITable distTable = getMotable(jsonstr,isMysql);
                String tableName = distTable.getTableName();
                ITable sourcetable = () -> tableName;
//                System.out.println(tableName+"=========");
                List<String> updateSqls=null;
                if(sourcetable==null){
                    String createTableSql = getTableSchemaSql(distTable, isMysql, true);
                    updateSqls.add(createTableSql);
                }else{
                    try {
                        TableDiff tableDiff= TableUtil.getDiff(sourcetable,distTable);
                        updateSqls=tableDiff.getUpdateSql();
                    }catch (Exception e){
                        throw new RuntimeException("获取表"+tableName+"的更新sql失败!",e);
                    }
                }
                if(updateSqls!=null && updateSqls.size()>0){
                    if(sourcetable.exists()){
                        String backupSql = "create table auto" + IDGenerator.getDateTimeId() + "_" + tableName + " select * from " + tableName ;
                        sqls.add(0, backupSql);
                    }
                    sqls.addAll(updateSqls);
                }
            }
            logger.info("sql更新记录：");
            if(1==1){
                logger.info("暂时取消！");
                sqls.forEach(sql->System.out.println(sql+";"));
                return;
            }
            if(sqls.size()==0){
                logger.info("无更新！");
            }else{
                /*try {
                    Statement smt = connection.createStatement();
                    for (String sql : sqls) {
                        logger.info(sql);
                        smt.addBatch(sql);
                    }
                    smt.executeBatch();
                } catch (SQLException e) {
                    logger.error("自动更新数据失败！（可能是没有权限）");
                }*/
            }
        }
        logger.info("数据库表结构同步结束！");
    }
    @Deprecated
    public static AbsDbTabel getMotable(String jsonstr,boolean isMysql) {
        AbsDbTabel dbTabel;
        if(isMysql){
            dbTabel=new MysqlTable();
        }else{
            dbTabel=new H2Table();
        }
        Map<String, IColumn> columns = new LinkedHashMap<>();
        dbTabel.setColumns(columns);
        JSONObject jsonObject = JSON.parseObject(jsonstr, Feature.OrderedField);
        dbTabel.setTableName(jsonObject.getString("tableName"));
        dbTabel.setRemark(jsonObject.getString("remark"));

        JSONArray jarr=JSON.parseArray(jsonObject.getString("columns"));
        jarr.stream().forEach(ja->{
            JSONObject json= (JSONObject) ja;
            json.put("columnType", ColumnType.getColumnType(json.get("columnType")+""));
            IColumn column;
            if(isMysql){
                column=JSONObject.toJavaObject(json,MysqlColumn.class);
            }else{
                column=JSONObject.toJavaObject(json, H2Column.class);
            }
            columns.put(column.getColumnName(), column);
        });
        return dbTabel;
    }
    @Deprecated
    public static String getTableSchemaSql(ITable table, boolean isMysql, boolean hasBreak){
        StringBuilder sb = new StringBuilder();
        String columnType;
        String colLen;
        String linebreak = hasBreak ? "\n" : "";

        sb.append("CREATE TABLE `" + table.getTableName() + "` (" + linebreak);
        int index = 0;
        String defaultNullStr=isMysql?"DEFAULT NULL":"";//h2数据不能用“DEFAULT NULL”，否则会把“NULL”字符串当成是默认值的（后面插入日期字段就发生错误了）
        for (IColumn column : table.getColumns().values()) {
            if (index > 0) {
                sb.append("," + linebreak);
            }
            columnType = column.getColumnType();
            if(!isMysql && columnType.toUpperCase().equals("TINYINT")){
                /*因为h2数据库会把TINYINT映射为java的Byte,而mysql却是Integer,所以把h2数据库的都转成int，这样javabean都用int接收*/
                columnType="INT";
            }
            colLen = getColLen(column);
            if(!isMysql){//是h2数据库
                if(columnType.equals("DOUBLE")){//h2数据库的double不设置长度和精度的
                    colLen="";
                }else if(columnType.toUpperCase().indexOf("ENUM")>-1){//h2数据库中不存在枚举，用VARCHAR(32)表示
                    columnType="VARCHAR";
                    colLen="(32)";
                }
            }
            String nullable = column.isNullAbled() ? defaultNullStr : "NOT NULL";
            Object defalutVal=column.getDefaultVal();
            if(defalutVal!=null){
                //如果有默认值，暂时去掉不可为空的设置
                if(columnType.equals("VARCHAR") || columnType.equals("CHAR")){
                    nullable="default '"+defalutVal+"'";
                }else if(columnType.equals("BIT")){
                    defalutVal=defalutVal=="b'0'"?0:1;
                    nullable="default "+(defalutVal=="b'0'"?0:1);
                }else{
                    nullable="default "+defalutVal;
                }
            }

            sb.append("  `" + column.getColumnName() + "` " + columnType + colLen + " " + nullable + " " +
                    "COMMENT '" + column.getRemark() + "'");
            index++;
        }
        if (table.getPk() != null) {
            sb.append("," + linebreak + " PRIMARY KEY (`" + table.getPkName()[0] + "`)");//没有考虑复合主键的情况
        }
        if (isMysql) {
            sb.append(" USING BTREE");
        }
        String ENGINE = "";
        if (isMysql) {
            ENGINE = "ENGINE=InnoDB";
        }
        sb.append(")");
        if (isMysql) {
            sb.append(ENGINE + " COMMENT='" + table.getRemark() + "'");
        }
        sb.append(";" + linebreak);
        return sb.toString();
    }
    @Deprecated
    private static String getColLen(IColumn column) {
        String columnType;
        String colLen="";

        columnType = column.getColumnType();
        int datasize = (int) column.getColumnRange();
        colLen = "(" + datasize + ")";
        if(column.getDecimalDigits()!=0){
            colLen="("+datasize+","+column.getDecimalDigits()+")";
        }

        if (columnType.equals("INT") && datasize == 10 || columnType.equals("DATETIME") && datasize == Column.COLUMN_SIZE_DATETIME ||
                columnType.equals("TEXT") || columnType.equals("TINYINT") && datasize == 3
                || columnType.equals("MEDIUMTEXT") || columnType.equals("BIT") && datasize == 1) {
            colLen = "";
        }
        if (columnType.indexOf("enum") >= 0) {
            colLen = "";
        }
        return colLen;
    }
}
