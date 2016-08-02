package com.automation.support;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
	private static String connectionUrl = null;
	private static String dbDriver = null;
	private static String userName = null;
	private static String password = null;
	
	public int updateTestcaseResult(int ExecutionID,String TestcaseName,String Result)
	{
		Connection dbConnection = getDBConnection();
		int TestcaseID = 0;
		if(dbConnection!=null)
		{
			try
			{
			Statement statement = dbConnection.createStatement();
			String Insertquery="insert into TestcaseExecutionDetail(ExecutionID,TestcaseName,Result)values("+ExecutionID+","+TestcaseName +","+Result+")";
			int result = statement.executeUpdate(Insertquery);
			String Getquery = "select max(TestCaseID) from TestcaseExecutionDetail where TestcaseName="+TestcaseName;
			ResultSet res = statement.executeQuery(Getquery);
			while(res.next())
			{
				TestcaseID = res.getInt(1);
			}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return TestcaseID;		
	}
	public void updateStepResult(int TestCaseID,String StepName,String ExpectedResult,String ActualResult,String Result)
	{
		Connection dbConnection = getDBConnection();
		int TestcaseID = 0;
		if(dbConnection!=null)
		{
			try
			{
			Statement statement = dbConnection.createStatement();
			String Insertquery="insert into StepExecutionDetail(TestCaseID,StepName,App_ExpectedResult,App_ActualResult,Step_Result)values("+TestCaseID+","+StepName +","+ExpectedResult+","+ActualResult+","+Result+")";
			int result = statement.executeUpdate(Insertquery);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getExecutionId(int ProjectID,String ExecutionName)
	{
		Connection dbConnection = getDBConnection();
		int ExecutionId = 0;
		if(dbConnection!=null)
		{
			try
			{
			Statement statement = dbConnection.createStatement();
			String Insertquery="insert into ExecutionDetail(ProjectID,ExecutionName)values("+ProjectID+","+ExecutionName +")";
			int result = statement.executeUpdate(Insertquery);
			String Getquery = "select max(ExecutionID) from ExecutionDetail where ExecutionName="+ExecutionName;
			ResultSet res = statement.executeQuery(Getquery);
			while(res.next())
			{
				ExecutionId = res.getInt(1);
			}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ExecutionId;
	}
	
	public Connection getDBConnection()
	{
		Connection dbConnection = null;
		try {
			Class.forName(dbDriver).newInstance();
			dbConnection = DriverManager.getConnection(connectionUrl,userName, password);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbConnection;
	}
}

