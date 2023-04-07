package com.iscweb.integration.cameras.mip.events;

/**
 * Build in event types exposed by Milestone camera server
 */
public enum BuiltInEventTypes {
    // PTZ Events
    PTZManualSessionStarted("2EEB582A-1506-4D73-96C9-AD356ACF85FB"),
    PTZManualSessionStopped("EED47283-694F-4683-9E4D-18E7D93CF796"),

    // ImageServer Events
    LiveClientFeedRequested("EEDA47FF-4F3D-459E-8143-69896C7C74AD"),
    LiveClientFeedTerminated("66BDD008-B856-44B6-B385-B1F6C3F76F1B"),

    // Motion Plugin Events
    MotionStarted("6EB95DD6-7CCC-4BCE-99F8-AF0D0B582D77"),
    MotionStopped("6F55A7A7-D21C-4629-AC18-AF1975E395A2"),

    // Bookmark Events
    BookmarkReferenceRequested("7CFC6709-2C16-405f-9182-0BDB6F9F7E43"),

    // Rule Engine / Timer Events
    RelativeTimerEvent("6A00FD9A-A7F1-446C-ABC2-29639CECD595"),
    PeriodicTimerEvent("F07AC0E3-9662-4781-9176-EC9E488AA494"),

    // Media Database Events
    RecordingStarted("4577F552-765A-438c-BC7D-E5FF1F754BC3"),
    RecordingStopped("79A94F89-92DE-4fca-8A43-5561D407423D"),
    DatabaseRepair("B6C4B695-7404-414D-A229-33BF83FFE255"),
    DatabaseDiskFull("A4935AA3-C4B6-42A3-B4C5-6D4C20B043CF"),
    DatabaseDiskUnavailable("29986AA1-924B-4c8d-AAC7-A876883DC82C"),
    DatabaseDiskAvailable("FAB03C4D-0589-48c8-9328-E28B76C95AF0"),
    ArchiveDiskUnavailable("72C29AAE-DDB2-46df-B6FD-7F728A85F292"),
    ArchiveDiskAvailable("B7F0003B-1392-450e-B6CC-9BBE6E7DF065"),

    // Driver Events
    HardwareSettingsChanged("A600C492-11C8-4D65-92DB-6D46209CE9EA"),
    HardwareSettingsChangedError("829A93AB-1AC0-4AEB-BDA0-ED8F6D8F514B"),
    InputActivated("836CA458-A833-4742-8EE0-64B2380984BD"),
    InputDeactivated("8666E3DC-57A7-4F38-9611-51D774EE7358"),
    InputChanged("FDA121B3-2070-4F31-A086-9F74E16580C0"),
    InputSensor("F84C8443-BFE3-4ECA-B69B-07DAEBC63A02"),
    OutputActivated("7A78F5BB-D8C3-4997-89B7-CAE72713B7DB"),
    OutputDeactivated("35742498-BCC5-4F0A-9800-827C9388D1CD"),
    OutputChanged("672F9F80-55FE-496F-BEE4-6C870BAB5064"),
    FeedOverflowStarted("536AD730-05AE-42CD-B14C-07B07C293E79"),
    FeedOverflowStopped("247FEF11-15DB-4C99-99DB-DE5475EAE443"),
    CommunicationError("A334AF1C-4B4B-4957-9E5F-AB8CA07FEAB6"),
    CommunicationStarted("DD3E6464-7DC0-405A-A92F-6150587563E8"),
    CommunicationStopped("0EE90664-2924-42A0-A816-4129D0ECABDC"),
    DeviceSettingsChanged("4C8BE5ED-80FA-43E7-BF5B-4E7570AD0025"),
    DeviceSettingsChangedError("87BE2A0D-C33B-4B5F-AE1F-AFA6770A2497"),

    // Failover Events
    FailoverStarted("F951B1F0-2FED-48F7-88D3-49EB5999C923"),
    FailoverStopped("C7B9227A-8E11-451B-BC9C-8EA659731012");

    private final String guid;

    BuiltInEventTypes(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return this.guid;
    }
}
