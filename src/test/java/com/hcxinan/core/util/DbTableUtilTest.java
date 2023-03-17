package com.hcxinan.core.util;

import com.morph.db.AbsDbTabel;
import com.morph.db.IColumn;
import com.morph.db.impl.MoTabel;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author liudk
 * @Description:
 * @date 21-6-28 下午7:04
 */
public class DbTableUtilTest {
    @Test
    void getMotable(){
        DbTableUtil dbTableUtil=new DbTableUtil();
        String jsonstr="{" +
                "  'tableName': 'form_xls'," +
                "  'remark': '（表单引擎）xls导出表'," +
                "  'columns': {" +
                "    'FX_ID': {" +
                "      'columnSize': 22," +
                "      'columnType': 'VARCHAR'," +
                "      'decimalDigits': 0," +
                "      'nullAbled': false," +
                "      'pk': true," +
                "      'remark': '主键'" +
                "    }," +
                "    'FNAME': {" +
                "      'columnSize': 50," +
                "      'columnType': 'VARCHAR'," +
                "      'remark': '导出文件名'" +
                "    }," +
                "    'EXP_ROWS': {" +
                "      'columnSize': 10," +
                "      'columnType': 'INT'," +
                "      'remark': '模板ID'" +
                "    }," +
                "    'TIPS': {" +
                "      'columnSize': 500," +
                "      'columnType': 'VARCHAR'," +
                "      'remark': '填写提示信息'" +
                "    }," +
                "    'TIPS_ROW_HIGHT': {" +
                "      'columnSize': 10," +
                "      'columnType': 'INT'," +
                "      'remark': '填写提示行的高度'" +
                "    }," +
                "    'HEAD_ROW_HEIGHT': {" +
                "      'columnSize': 10," +
                "      'columnType': 'INT'," +
                "      'remark': '列表头高度'" +
                "    }," +
                "    'COL_WIDTH': {" +
                "      'columnSize': 10," +
                "      'columnType': 'INT'," +
                "      'decimalDigits': 0," +
                "      'nullAbled': true," +
                "      'pk': false," +
                "      'remark': '列宽'" +
                "    }," +
                "    'LOCK': {" +
                "      'columnSize': 1," +
                "      'columnType': 'BIT'," +
                "      'nullAbled': false," +
                "      'remark': '是否锁定'" +
                "    }," +
                "    'CREATOR': {" +
                "      'columnSize': 32," +
                "      'columnType': 'VARCHAR'," +
                "      'remark': '创建人'" +
                "    }," +
                "    'CDATE': {" +
                "      'columnSize': 26," +
                "      'columnType': 'DATETIME'," +
                "      'remark': '创建时间'" +
                "    }," +
                "    'UPDATOR': {" +
                "      'columnSize': 32," +
                "      'columnType': 'VARCHAR'," +
                "      'remark': '修改人'" +
                "    }," +
                "    'UDATE': {" +
                "      'columnSize': 26," +
                "      'columnType': 'DATETIME'," +
                "      'remark': '修改时间'" +
                "    }" +
                "  }" +
                "}";
        AbsDbTabel tabel=dbTableUtil.getMotable(jsonstr,false);
        Map<String, IColumn> cols = tabel.getColumns();
        IColumn column=cols.get("FNAME");
        assertThat(column.getColumnName()).isEqualTo("FNAME");
        assertThat(column.getDecimalDigits()).isEqualTo(0);
        assertThat(column.isPk()).isFalse();
        assertThat(column.getColumnType()).isEqualTo("VARCHAR");
        assertThat(column.getRemark()).isEqualTo("导出文件名");
        assertThat(column.isNullAbled()).isTrue();


        column=cols.get("LOCK");
        assertThat(column.isNullAbled()).isFalse();
    }
}
