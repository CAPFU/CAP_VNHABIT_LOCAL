package habit.tracker.habittracker.repository.group;

public class GroupEntity {
    private String groupId;
    private String groupName;
    private String groupDescription;
    private String isDelete = "0";
    private String isLocal = "0";

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return groupDescription;
    }

    public String isDelete() {
        return isDelete;
    }

    public String isLocal() {
        return isLocal;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public void setDelete(String delete) {
        isDelete = delete;
    }

    public void setLocal(String isLocal) {
        this.isLocal = isLocal;
    }
}
