public class BuildingNode {
    private int buildingNum;
    private int executedTime;
    private int totalTime;

    public BuildingNode(int buildingNum, int executedTime, int totalTime) {
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
