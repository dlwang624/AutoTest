package org.czy.entity;

import java.util.List;

public class GateWay {

	private int ppCount;
	
	private int totalPPCount;
	
	private int toolCount;
	
	private int usetoolCount;
	
	private int downtoolCount;
	
	private int regressCount;
	
	private int regressTime;
	
	private int releaseCount;
	
	private int releaseTime;
	
	private List<WorkDynamic> worklist;
	
	public int getPpCount() {
		return ppCount;
	}

	public void setPpCount(int ppCount) {
		this.ppCount = ppCount;
	}

	public int getTotalPPCount() {
		return totalPPCount;
	}

	public void setTotalPPCount(int totalPPCount) {
		this.totalPPCount = totalPPCount;
	}

	public int getToolCount() {
		return toolCount;
	}

	public void setToolCount(int toolCount) {
		this.toolCount = toolCount;
	}

	public int getUsetoolCount() {
		return usetoolCount;
	}

	public void setUsetoolCount(int usetoolCount) {
		this.usetoolCount = usetoolCount;
	}

	public int getRegressCount() {
		return regressCount;
	}

	public void setRegressCount(int regressCount) {
		this.regressCount = regressCount;
	}

	public int getRegressTime() {
		return regressTime;
	}

	public void setRegressTime(int regressTime) {
		this.regressTime = regressTime;
	}

	public int getReleaseCount() {
		return releaseCount;
	}

	public void setReleaseCount(int releaseCount) {
		this.releaseCount = releaseCount;
	}

	public int getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(int releaseTime) {
		this.releaseTime = releaseTime;
	}

	public List<WorkDynamic> getWorklist() {
		return worklist;
	}

	public void setWorklist(List<WorkDynamic> worklist) {
		this.worklist = worklist;
	}

	public List<Train> getTrainlist() {
		return trainlist;
	}

	public void setTrainlist(List<Train> trainlist) {
		this.trainlist = trainlist;
	}

	public List<Test> getTestlist() {
		return testlist;
	}

	public void setTestlist(List<Test> testlist) {
		this.testlist = testlist;
	}

	public List<Tools> getToollist() {
		return toollist;
	}

	public void setToollist(List<Tools> toollist) {
		this.toollist = toollist;
	}
	
	public int getDowntoolCount() {
		return downtoolCount;
	}

	public void setDowntoolCount(int downtoolCount) {
		this.downtoolCount = downtoolCount;
	}

	private List<Train> trainlist;
	
	private List<Test> testlist;
	
	private List<Tools> toollist;
	
}
