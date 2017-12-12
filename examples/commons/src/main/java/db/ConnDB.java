package db;

/**
 * Created by wanshao
 * Date: 2017/12/11
 * Time: 下午2:54
 **/

import java.sql.DriverManager;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;

public class ConnDB {


    //数据库连接地址
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/jingwei?zeroDateTimeBehavior=convertToNull";
    //用户名
    public final static String USERNAME = "root";
    //密码
    public final static String PASSWORD = "wkm19900527";
    //加载的驱动程序类（这个类就在我们导入的jar包中）
    public final static String DRIVER = "com.mysql.jdbc.Driver";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        query();

    }


    //方法：查询操作
    public static void query(){
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "select id,name,ts from user";
            Statement state = conn.createStatement();
            //执行查询并返回结果集
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()){  //通过next来索引：判断是否有下一个记录
                //rs.getInt("id"); //方法：int java.sql.ResultSet.getInt(String columnLabel) throws SQLException
                int id = rs.getInt(1);  //方法：int java.sql.ResultSet.getInt(int columnIndex) throws SQLException

                String name = rs.getString(2);
                String timestamp = rs.getString(3);
                System.out.println("id="+id+",name="+name+",timestamp="+timestamp);
            }
            rs.close();
            state.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
