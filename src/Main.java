import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new DatabaseClass();
    }
}


class DatabaseClass {
    DatabaseClass(){
        String dbUri="jdbc:mysql://localhost:3306/lesson13?serverTimezone=UTC";
        String sUser="root";
        String passWord = "Aeglir001";
        try{
            Connection connection=DriverManager.getConnection(dbUri,sUser,passWord);
            Statement statement=connection.createStatement();
            insertDate(statement);
            control(statement);
            System.out.println("已连接");
            connection.close();
            System.out.println("连接已安全断开");
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    void insertDate(Statement statement){
        String sql="insert into cityinfo values('";
        Scanner scanner=new Scanner(System.in);
        System.out.println("City");
        sql+= scanner.nextLine() +"'"+",";
        System.out.println("Population");
        sql+= scanner.nextLine() +",";
        System.out.println("GDP");
        sql+= scanner.nextLine() +",";
        System.out.println("Date");
        sql+="'"+ scanner.nextLine() +" ";
        System.out.println("Time");
        sql+= scanner.nextLine() +"')";
        try{
            statement.executeUpdate(sql);
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    void control(Statement statement){
        boolean flag=true;
        while(flag){
            Scanner scanner=new Scanner(System.in);
            if(scanner.hasNextInt())
            {
                switch (scanner.nextInt()){
                    case 0:
                        flag=false;
                        break;
                    case 1:
                        show(statement);
                        break;
                    case 2:
                        deleteDate(statement);
                        break;
                    case 3:
                        update(statement);
                        break;
                    default:
                        System.out.println("输入格式错误");
                }
            }
        }
    }

    void show(Statement statement){
        String sql="select City,Population,GDP,LastModified from cityinfo";
        try{
            ResultSet resultSet= statement.executeQuery(sql);
            System.out.println("City\tPopulation\tGDP\tLastModified");
            while (resultSet.next()){
                System.out.print(resultSet.getString("City")+"\t");
                System.out.print(resultSet.getInt("Population")+"\t");
                System.out.print(resultSet.getFloat("GDP")+"\t");
                System.out.println(resultSet.getDate("LastModified")
                        +" "+resultSet.getTime("LastModified"));
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    void deleteDate(Statement statement){
        show(statement);
        Scanner scanner=new Scanner(System.in);
        System.out.println("输入要删除的城市");
        String city = scanner.nextLine();
        String sql="delete from cityinfo where City="+"'"+city+"'";
        try{
            int num=statement.executeUpdate(sql);
            System.out.println("共"+num+"条记录被修改");
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    void update(Statement statement){
        LocalDateTime localDateTime=LocalDateTime.now();
        String sql="update cityinfo set LastModified="+"'"+localDateTime.toLocalDate().toString()+" "+localDateTime.toLocalTime().toString()+"'";
        try{
            int num=statement.executeUpdate(sql);
            System.out.println("共"+num+"条记录被修改");
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }
}