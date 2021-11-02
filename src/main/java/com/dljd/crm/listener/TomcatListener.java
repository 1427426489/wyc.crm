package com.dljd.crm.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class TomcatListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    //在servletContext对象销毁时(spring容器销毁时),注销jdbc驱动,关闭AbandonedConnectionCleanupThread线程
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //手动注销jdbc驱动
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()){
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //关闭AbandonedConnectionCleanupThread线程
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
