package com.raja.rest.core;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raja.rest.common.Invoker;
import com.raja.rest.common.impl.ClassLoader;
import com.raja.rest.common.impl.RestInvoker;
import com.raja.rest.config.Config;
import com.raja.rest.config.RestWebConfiguration;
import com.raja.rest.mapper.EndPointMapper;
import com.raja.rest.mapper.Mapper;

public abstract class BaseController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public  abstract void setInvoker(Invoker<Config,Mapper<?, ?>> invoker);
	
	public abstract void handleRequest(HttpServletRequest arg0, HttpServletResponse arg1);
	
	public abstract void setConfig(Config config);
	
	public abstract Config getConfig();
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		
		super.init(servletConfig);
		
		setConfig(new RestWebConfiguration().basePack(servletConfig.getInitParameter("package")).loadConfiguration());

		//setInvoker(new RestInvoker().setMapper(EndPointMapper.build()).getEndPointMapper().map(new ClassLoader().load(this.getConfig().getPack()))));
		
		setInvoker(new RestInvoker().setMapper(EndPointMapper.build()).setConfig(getConfig()).setLoader(new ClassLoader()).build());

		
		/*EndPointMapper.build();
		
		loader = new ClassLoader();
		
		this.getEndPointMapper().map(this.getLoader().load(this.getPack()));*/
		
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		handleRequest(arg0, arg1);
	}

	
	
}
