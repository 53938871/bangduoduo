package com.bangduoduo.web.form.component;

import com.bangduoduo.web.form.DataSource;
import com.bangduoduo.web.form.Field;

public class TextArea extends Component {
	
	private int rows = 10;
	
	private int cols = 50;
	
	public TextArea(DataSource dataSource,Field field){
		super(dataSource,field);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	
	
}
