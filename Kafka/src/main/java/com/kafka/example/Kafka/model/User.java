package com.kafka.example.Kafka.model;

public class User {
	
	private String name;
	private String dept;
	private String salary;
	
	public User(String name, String dept, String salary) {
		super();
		this.name = name;
		this.dept = dept;
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", dept=" + dept + ", salary=" + salary + "]";
	}

	public User() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
}
