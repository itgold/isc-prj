
package com.mip.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mip.command package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ViewGroupInternal_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", "ViewGroupInternal");
    private final static QName _ViewGroupInternalData_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", "ViewGroupInternalData");
    private final static QName _CustomSettingInternal_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", "CustomSettingInternal");
    private final static QName _CustomSettingInternalData_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", "CustomSettingInternalData");
    private final static QName _RetentionOptionType_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService", "RetentionOptionType");
    private final static QName _ResultStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService", "ResultStatus");
    private final static QName _SortOrderOption_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService", "SortOrderOption");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _ProductInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ProductInfo");
    private final static QName _SystemLicenseInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SystemLicenseInfo");
    private final static QName _SmartClientVersion_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SmartClientVersion");
    private final static QName _UpgradeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "UpgradeInfo");
    private final static QName _LoginInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "LoginInfo");
    private final static QName _TimeDuration_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "TimeDuration");
    private final static QName _AuthorizationActionType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "AuthorizationActionType");
    private final static QName _UserInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "UserInfo");
    private final static QName _ConfigurationInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ConfigurationInfo");
    private final static QName _ArrayOfAlertTypeGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfAlertTypeGroupInfo");
    private final static QName _AlertTypeGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "AlertTypeGroupInfo");
    private final static QName _GroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "GroupInfo");
    private final static QName _ArrayOfAlertTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfAlertTypeInfo");
    private final static QName _AlertTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "AlertTypeInfo");
    private final static QName _ApplicationSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ApplicationSecurityInfo");
    private final static QName _SmartClientSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SmartClientSecurityInfo");
    private final static QName _ArrayOfAudioMessageInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfAudioMessageInfo");
    private final static QName _AudioMessageInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "AudioMessageInfo");
    private final static QName _BookmarkSettingInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "BookmarkSettingInfo");
    private final static QName _ArrayOfCameraGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfCameraGroupInfo");
    private final static QName _CameraGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "CameraGroupInfo");
    private final static QName _ArrayOfDeviceMappingInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfDeviceMappingInfo");
    private final static QName _DeviceMappingInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "DeviceMappingInfo");
    private final static QName _ArrayOfEventTypeGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfEventTypeGroupInfo");
    private final static QName _EventTypeGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "EventTypeGroupInfo");
    private final static QName _ArrayOfEventTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfEventTypeInfo");
    private final static QName _EventTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "EventTypeInfo");
    private final static QName _ArrayOfInputGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfInputGroupInfo");
    private final static QName _InputGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "InputGroupInfo");
    private final static QName _ArrayOfLicenseInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfLicenseInfo");
    private final static QName _LicenseInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "LicenseInfo");
    private final static QName _ArrayOfMatrixMonitorInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMatrixMonitorInfo");
    private final static QName _MatrixMonitorInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MatrixMonitorInfo");
    private final static QName _ArrayOfMetadataDeviceGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMetadataDeviceGroupInfo");
    private final static QName _MetadataDeviceGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MetadataDeviceGroupInfo");
    private final static QName _ArrayOfMicrophoneGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMicrophoneGroupInfo");
    private final static QName _MicrophoneGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MicrophoneGroupInfo");
    private final static QName _ArrayOfOutputGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfOutputGroupInfo");
    private final static QName _OutputGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "OutputGroupInfo");
    private final static QName _ArrayOfRecorderInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfRecorderInfo");
    private final static QName _RecorderInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "RecorderInfo");
    private final static QName _ArrayOfCameraInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfCameraInfo");
    private final static QName _CameraInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "CameraInfo");
    private final static QName _DeviceInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "DeviceInfo");
    private final static QName _CameraSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "CameraSecurityInfo");
    private final static QName _PtzSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "PtzSecurityInfo");
    private final static QName _IpixInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "IpixInfo");
    private final static QName _HemisphereInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "HemisphereInfo");
    private final static QName _PositionInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "PositionInfo");
    private final static QName _PanoramicLensInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "PanoramicLensInfo");
    private final static QName _ImmerVisionInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ImmerVisionInfo");
    private final static QName _PtzInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "PtzInfo");
    private final static QName _ArrayOfCapabilityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfCapabilityInfo");
    private final static QName _CapabilityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "CapabilityInfo");
    private final static QName _ArrayOfPresetInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfPresetInfo");
    private final static QName _PresetInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "PresetInfo");
    private final static QName _ArrayOfStreamInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfStreamInfo");
    private final static QName _StreamInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "StreamInfo");
    private final static QName _ArrayOfTrackInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfTrackInfo");
    private final static QName _TrackInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "TrackInfo");
    private final static QName _ArrayOfHardwareInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfHardwareInfo");
    private final static QName _HardwareInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "HardwareInfo");
    private final static QName _ArrayOfInputInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfInputInfo");
    private final static QName _InputInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "InputInfo");
    private final static QName _InputSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "InputSecurityInfo");
    private final static QName _ArrayOfMetadataDeviceInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMetadataDeviceInfo");
    private final static QName _MetadataDeviceInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MetadataDeviceInfo");
    private final static QName _MetadataDeviceSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MetadataDeviceSecurityInfo");
    private final static QName _ArrayOfMetadataTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMetadataTypeInfo");
    private final static QName _MetadataTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MetadataTypeInfo");
    private final static QName _ArrayOfMicrophoneInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMicrophoneInfo");
    private final static QName _MicrophoneInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MicrophoneInfo");
    private final static QName _MicrophoneSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MicrophoneSecurityInfo");
    private final static QName _ArrayOfOutputInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfOutputInfo");
    private final static QName _OutputInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "OutputInfo");
    private final static QName _OutputSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "OutputSecurityInfo");
    private final static QName _ArrayOfSpeakerInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfSpeakerInfo");
    private final static QName _SpeakerInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SpeakerInfo");
    private final static QName _SpeakerSecurityInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SpeakerSecurityInfo");
    private final static QName _RetentionSettingInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "RetentionSettingInfo");
    private final static QName _ArrayOfRetentionOption_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfRetentionOption");
    private final static QName _RetentionOption_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "RetentionOption");
    private final static QName _ArrayOfServerOption_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfServerOption");
    private final static QName _ServerOption_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ServerOption");
    private final static QName _ArrayOfSpeakerGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfSpeakerGroupInfo");
    private final static QName _SpeakerGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SpeakerGroupInfo");
    private final static QName _ArrayOfSystemEventTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfSystemEventTypeInfo");
    private final static QName _SystemEventTypeInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SystemEventTypeInfo");
    private final static QName _EventSource_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "EventSource");
    private final static QName _ArrayOfDeviceDisabledInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfDeviceDisabledInfo");
    private final static QName _DeviceDisabledInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "DeviceDisabledInfo");
    private final static QName _ArrayOfViewGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfViewGroupInfo");
    private final static QName _ViewGroupInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ViewGroupInfo");
    private final static QName _ViewGroupType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ViewGroupType");
    private final static QName _SetViewGroupDataResultType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SetViewGroupDataResultType");
    private final static QName _CustomSettingInfo_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "CustomSettingInfo");
    private final static QName _SetCustomSettingResultType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "SetCustomSettingResultType");
    private final static QName _ArrayOfKeyValue_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfKeyValue");
    private final static QName _KeyValue_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "KeyValue");
    private final static QName _ArrayOfAlertOccurrence_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfAlertOccurrence");
    private final static QName _AlertOccurrence_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "AlertOccurrence");
    private final static QName _ArrayOfChange_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfChange");
    private final static QName _Change_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "Change");
    private final static QName _ChangeType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ChangeType");
    private final static QName _Bookmark_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "Bookmark");
    private final static QName _ArrayOfMediaDeviceType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMediaDeviceType");
    private final static QName _MediaDeviceType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MediaDeviceType");
    private final static QName _ArrayOfBookmark_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfBookmark");
    private final static QName _IntegrationType_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "IntegrationType");
    private final static QName _ArrayOfPluginIntegration_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfPluginIntegration");
    private final static QName _PluginIntegration_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "PluginIntegration");
    private final static QName _MarkedDataResult_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MarkedDataResult");
    private final static QName _ArrayOfFaultDevice_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfFaultDevice");
    private final static QName _FaultDevice_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "FaultDevice");
    private final static QName _MarkedData_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "MarkedData");
    private final static QName _ArrayOfWarningDevice_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfWarningDevice");
    private final static QName _WarningDevice_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "WarningDevice");
    private final static QName _ArrayOfMarkedDataResult_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMarkedDataResult");
    private final static QName _ArrayOfMarkedData_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfMarkedData");
    private final static QName _ClientProfile_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ClientProfile");
    private final static QName _ArrayOfClientProfileSetting_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ArrayOfClientProfileSetting");
    private final static QName _ClientProfileSetting_QNAME = new QName("http://video.net/2/XProtectCSServerCommand", "ClientProfileSetting");
    private final static QName _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    private final static QName _ArrayOfguid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfguid");
    private final static QName _ArrayOfDictionaryEntry_QNAME = new QName("http://schemas.datacontract.org/2004/07/System.Collections", "ArrayOfDictionaryEntry");
    private final static QName _DictionaryEntry_QNAME = new QName("http://schemas.datacontract.org/2004/07/System.Collections", "DictionaryEntry");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mip.command
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ViewGroupInternal }
     * 
     */
    public ViewGroupInternal createViewGroupInternal() {
        return new ViewGroupInternal();
    }

    /**
     * Create an instance of {@link ViewGroupInternalData }
     * 
     */
    public ViewGroupInternalData createViewGroupInternalData() {
        return new ViewGroupInternalData();
    }

    /**
     * Create an instance of {@link CustomSettingInternal }
     * 
     */
    public CustomSettingInternal createCustomSettingInternal() {
        return new CustomSettingInternal();
    }

    /**
     * Create an instance of {@link CustomSettingInternalData }
     * 
     */
    public CustomSettingInternalData createCustomSettingInternalData() {
        return new CustomSettingInternalData();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link GetServerVersion }
     * 
     */
    public GetServerVersion createGetServerVersion() {
        return new GetServerVersion();
    }

    /**
     * Create an instance of {@link GetServerVersionResponse }
     * 
     */
    public GetServerVersionResponse createGetServerVersionResponse() {
        return new GetServerVersionResponse();
    }

    /**
     * Create an instance of {@link GetProductInfo }
     * 
     */
    public GetProductInfo createGetProductInfo() {
        return new GetProductInfo();
    }

    /**
     * Create an instance of {@link GetProductInfoResponse }
     * 
     */
    public GetProductInfoResponse createGetProductInfoResponse() {
        return new GetProductInfoResponse();
    }

    /**
     * Create an instance of {@link ProductInfo }
     * 
     */
    public ProductInfo createProductInfo() {
        return new ProductInfo();
    }

    /**
     * Create an instance of {@link GetSystemLicenseInfo }
     * 
     */
    public GetSystemLicenseInfo createGetSystemLicenseInfo() {
        return new GetSystemLicenseInfo();
    }

    /**
     * Create an instance of {@link GetSystemLicenseInfoResponse }
     * 
     */
    public GetSystemLicenseInfoResponse createGetSystemLicenseInfoResponse() {
        return new GetSystemLicenseInfoResponse();
    }

    /**
     * Create an instance of {@link SystemLicenseInfo }
     * 
     */
    public SystemLicenseInfo createSystemLicenseInfo() {
        return new SystemLicenseInfo();
    }

    /**
     * Create an instance of {@link GetSmartClientVersion }
     * 
     */
    public GetSmartClientVersion createGetSmartClientVersion() {
        return new GetSmartClientVersion();
    }

    /**
     * Create an instance of {@link GetSmartClientVersionResponse }
     * 
     */
    public GetSmartClientVersionResponse createGetSmartClientVersionResponse() {
        return new GetSmartClientVersionResponse();
    }

    /**
     * Create an instance of {@link SmartClientVersion }
     * 
     */
    public SmartClientVersion createSmartClientVersion() {
        return new SmartClientVersion();
    }

    /**
     * Create an instance of {@link UpgradeInfo }
     * 
     */
    public UpgradeInfo createUpgradeInfo() {
        return new UpgradeInfo();
    }

    /**
     * Create an instance of {@link CheckSmartClientVersion }
     * 
     */
    public CheckSmartClientVersion createCheckSmartClientVersion() {
        return new CheckSmartClientVersion();
    }

    /**
     * Create an instance of {@link CheckSmartClientVersionResponse }
     * 
     */
    public CheckSmartClientVersionResponse createCheckSmartClientVersionResponse() {
        return new CheckSmartClientVersionResponse();
    }

    /**
     * Create an instance of {@link IsOnline }
     * 
     */
    public IsOnline createIsOnline() {
        return new IsOnline();
    }

    /**
     * Create an instance of {@link IsOnlineResponse }
     * 
     */
    public IsOnlineResponse createIsOnlineResponse() {
        return new IsOnlineResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link LoginInfo }
     * 
     */
    public LoginInfo createLoginInfo() {
        return new LoginInfo();
    }

    /**
     * Create an instance of {@link TimeDuration }
     * 
     */
    public TimeDuration createTimeDuration() {
        return new TimeDuration();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link Authorize }
     * 
     */
    public Authorize createAuthorize() {
        return new Authorize();
    }

    /**
     * Create an instance of {@link AuthorizeResponse }
     * 
     */
    public AuthorizeResponse createAuthorizeResponse() {
        return new AuthorizeResponse();
    }

    /**
     * Create an instance of {@link GetUserInfo }
     * 
     */
    public GetUserInfo createGetUserInfo() {
        return new GetUserInfo();
    }

    /**
     * Create an instance of {@link GetUserInfoResponse }
     * 
     */
    public GetUserInfoResponse createGetUserInfoResponse() {
        return new GetUserInfoResponse();
    }

    /**
     * Create an instance of {@link UserInfo }
     * 
     */
    public UserInfo createUserInfo() {
        return new UserInfo();
    }

    /**
     * Create an instance of {@link GetConfiguration }
     * 
     */
    public GetConfiguration createGetConfiguration() {
        return new GetConfiguration();
    }

    /**
     * Create an instance of {@link GetConfigurationResponse }
     * 
     */
    public GetConfigurationResponse createGetConfigurationResponse() {
        return new GetConfigurationResponse();
    }

    /**
     * Create an instance of {@link ConfigurationInfo }
     * 
     */
    public ConfigurationInfo createConfigurationInfo() {
        return new ConfigurationInfo();
    }

    /**
     * Create an instance of {@link ArrayOfAlertTypeGroupInfo }
     * 
     */
    public ArrayOfAlertTypeGroupInfo createArrayOfAlertTypeGroupInfo() {
        return new ArrayOfAlertTypeGroupInfo();
    }

    /**
     * Create an instance of {@link AlertTypeGroupInfo }
     * 
     */
    public AlertTypeGroupInfo createAlertTypeGroupInfo() {
        return new AlertTypeGroupInfo();
    }

    /**
     * Create an instance of {@link GroupInfo }
     * 
     */
    public GroupInfo createGroupInfo() {
        return new GroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfAlertTypeInfo }
     * 
     */
    public ArrayOfAlertTypeInfo createArrayOfAlertTypeInfo() {
        return new ArrayOfAlertTypeInfo();
    }

    /**
     * Create an instance of {@link AlertTypeInfo }
     * 
     */
    public AlertTypeInfo createAlertTypeInfo() {
        return new AlertTypeInfo();
    }

    /**
     * Create an instance of {@link ApplicationSecurityInfo }
     * 
     */
    public ApplicationSecurityInfo createApplicationSecurityInfo() {
        return new ApplicationSecurityInfo();
    }

    /**
     * Create an instance of {@link SmartClientSecurityInfo }
     * 
     */
    public SmartClientSecurityInfo createSmartClientSecurityInfo() {
        return new SmartClientSecurityInfo();
    }

    /**
     * Create an instance of {@link ArrayOfAudioMessageInfo }
     * 
     */
    public ArrayOfAudioMessageInfo createArrayOfAudioMessageInfo() {
        return new ArrayOfAudioMessageInfo();
    }

    /**
     * Create an instance of {@link AudioMessageInfo }
     * 
     */
    public AudioMessageInfo createAudioMessageInfo() {
        return new AudioMessageInfo();
    }

    /**
     * Create an instance of {@link BookmarkSettingInfo }
     * 
     */
    public BookmarkSettingInfo createBookmarkSettingInfo() {
        return new BookmarkSettingInfo();
    }

    /**
     * Create an instance of {@link ArrayOfCameraGroupInfo }
     * 
     */
    public ArrayOfCameraGroupInfo createArrayOfCameraGroupInfo() {
        return new ArrayOfCameraGroupInfo();
    }

    /**
     * Create an instance of {@link CameraGroupInfo }
     * 
     */
    public CameraGroupInfo createCameraGroupInfo() {
        return new CameraGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfDeviceMappingInfo }
     * 
     */
    public ArrayOfDeviceMappingInfo createArrayOfDeviceMappingInfo() {
        return new ArrayOfDeviceMappingInfo();
    }

    /**
     * Create an instance of {@link DeviceMappingInfo }
     * 
     */
    public DeviceMappingInfo createDeviceMappingInfo() {
        return new DeviceMappingInfo();
    }

    /**
     * Create an instance of {@link ArrayOfEventTypeGroupInfo }
     * 
     */
    public ArrayOfEventTypeGroupInfo createArrayOfEventTypeGroupInfo() {
        return new ArrayOfEventTypeGroupInfo();
    }

    /**
     * Create an instance of {@link EventTypeGroupInfo }
     * 
     */
    public EventTypeGroupInfo createEventTypeGroupInfo() {
        return new EventTypeGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfEventTypeInfo }
     * 
     */
    public ArrayOfEventTypeInfo createArrayOfEventTypeInfo() {
        return new ArrayOfEventTypeInfo();
    }

    /**
     * Create an instance of {@link EventTypeInfo }
     * 
     */
    public EventTypeInfo createEventTypeInfo() {
        return new EventTypeInfo();
    }

    /**
     * Create an instance of {@link ArrayOfInputGroupInfo }
     * 
     */
    public ArrayOfInputGroupInfo createArrayOfInputGroupInfo() {
        return new ArrayOfInputGroupInfo();
    }

    /**
     * Create an instance of {@link InputGroupInfo }
     * 
     */
    public InputGroupInfo createInputGroupInfo() {
        return new InputGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfLicenseInfo }
     * 
     */
    public ArrayOfLicenseInfo createArrayOfLicenseInfo() {
        return new ArrayOfLicenseInfo();
    }

    /**
     * Create an instance of {@link LicenseInfo }
     * 
     */
    public LicenseInfo createLicenseInfo() {
        return new LicenseInfo();
    }

    /**
     * Create an instance of {@link ArrayOfMatrixMonitorInfo }
     * 
     */
    public ArrayOfMatrixMonitorInfo createArrayOfMatrixMonitorInfo() {
        return new ArrayOfMatrixMonitorInfo();
    }

    /**
     * Create an instance of {@link MatrixMonitorInfo }
     * 
     */
    public MatrixMonitorInfo createMatrixMonitorInfo() {
        return new MatrixMonitorInfo();
    }

    /**
     * Create an instance of {@link ArrayOfMetadataDeviceGroupInfo }
     * 
     */
    public ArrayOfMetadataDeviceGroupInfo createArrayOfMetadataDeviceGroupInfo() {
        return new ArrayOfMetadataDeviceGroupInfo();
    }

    /**
     * Create an instance of {@link MetadataDeviceGroupInfo }
     * 
     */
    public MetadataDeviceGroupInfo createMetadataDeviceGroupInfo() {
        return new MetadataDeviceGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfMicrophoneGroupInfo }
     * 
     */
    public ArrayOfMicrophoneGroupInfo createArrayOfMicrophoneGroupInfo() {
        return new ArrayOfMicrophoneGroupInfo();
    }

    /**
     * Create an instance of {@link MicrophoneGroupInfo }
     * 
     */
    public MicrophoneGroupInfo createMicrophoneGroupInfo() {
        return new MicrophoneGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfOutputGroupInfo }
     * 
     */
    public ArrayOfOutputGroupInfo createArrayOfOutputGroupInfo() {
        return new ArrayOfOutputGroupInfo();
    }

    /**
     * Create an instance of {@link OutputGroupInfo }
     * 
     */
    public OutputGroupInfo createOutputGroupInfo() {
        return new OutputGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfRecorderInfo }
     * 
     */
    public ArrayOfRecorderInfo createArrayOfRecorderInfo() {
        return new ArrayOfRecorderInfo();
    }

    /**
     * Create an instance of {@link RecorderInfo }
     * 
     */
    public RecorderInfo createRecorderInfo() {
        return new RecorderInfo();
    }

    /**
     * Create an instance of {@link ArrayOfCameraInfo }
     * 
     */
    public ArrayOfCameraInfo createArrayOfCameraInfo() {
        return new ArrayOfCameraInfo();
    }

    /**
     * Create an instance of {@link CameraInfo }
     * 
     */
    public CameraInfo createCameraInfo() {
        return new CameraInfo();
    }

    /**
     * Create an instance of {@link DeviceInfo }
     * 
     */
    public DeviceInfo createDeviceInfo() {
        return new DeviceInfo();
    }

    /**
     * Create an instance of {@link CameraSecurityInfo }
     * 
     */
    public CameraSecurityInfo createCameraSecurityInfo() {
        return new CameraSecurityInfo();
    }

    /**
     * Create an instance of {@link PtzSecurityInfo }
     * 
     */
    public PtzSecurityInfo createPtzSecurityInfo() {
        return new PtzSecurityInfo();
    }

    /**
     * Create an instance of {@link IpixInfo }
     * 
     */
    public IpixInfo createIpixInfo() {
        return new IpixInfo();
    }

    /**
     * Create an instance of {@link HemisphereInfo }
     * 
     */
    public HemisphereInfo createHemisphereInfo() {
        return new HemisphereInfo();
    }

    /**
     * Create an instance of {@link PositionInfo }
     * 
     */
    public PositionInfo createPositionInfo() {
        return new PositionInfo();
    }

    /**
     * Create an instance of {@link PanoramicLensInfo }
     * 
     */
    public PanoramicLensInfo createPanoramicLensInfo() {
        return new PanoramicLensInfo();
    }

    /**
     * Create an instance of {@link ImmerVisionInfo }
     * 
     */
    public ImmerVisionInfo createImmerVisionInfo() {
        return new ImmerVisionInfo();
    }

    /**
     * Create an instance of {@link PtzInfo }
     * 
     */
    public PtzInfo createPtzInfo() {
        return new PtzInfo();
    }

    /**
     * Create an instance of {@link ArrayOfCapabilityInfo }
     * 
     */
    public ArrayOfCapabilityInfo createArrayOfCapabilityInfo() {
        return new ArrayOfCapabilityInfo();
    }

    /**
     * Create an instance of {@link CapabilityInfo }
     * 
     */
    public CapabilityInfo createCapabilityInfo() {
        return new CapabilityInfo();
    }

    /**
     * Create an instance of {@link ArrayOfPresetInfo }
     * 
     */
    public ArrayOfPresetInfo createArrayOfPresetInfo() {
        return new ArrayOfPresetInfo();
    }

    /**
     * Create an instance of {@link PresetInfo }
     * 
     */
    public PresetInfo createPresetInfo() {
        return new PresetInfo();
    }

    /**
     * Create an instance of {@link ArrayOfStreamInfo }
     * 
     */
    public ArrayOfStreamInfo createArrayOfStreamInfo() {
        return new ArrayOfStreamInfo();
    }

    /**
     * Create an instance of {@link StreamInfo }
     * 
     */
    public StreamInfo createStreamInfo() {
        return new StreamInfo();
    }

    /**
     * Create an instance of {@link ArrayOfTrackInfo }
     * 
     */
    public ArrayOfTrackInfo createArrayOfTrackInfo() {
        return new ArrayOfTrackInfo();
    }

    /**
     * Create an instance of {@link TrackInfo }
     * 
     */
    public TrackInfo createTrackInfo() {
        return new TrackInfo();
    }

    /**
     * Create an instance of {@link ArrayOfHardwareInfo }
     * 
     */
    public ArrayOfHardwareInfo createArrayOfHardwareInfo() {
        return new ArrayOfHardwareInfo();
    }

    /**
     * Create an instance of {@link HardwareInfo }
     * 
     */
    public HardwareInfo createHardwareInfo() {
        return new HardwareInfo();
    }

    /**
     * Create an instance of {@link ArrayOfInputInfo }
     * 
     */
    public ArrayOfInputInfo createArrayOfInputInfo() {
        return new ArrayOfInputInfo();
    }

    /**
     * Create an instance of {@link InputInfo }
     * 
     */
    public InputInfo createInputInfo() {
        return new InputInfo();
    }

    /**
     * Create an instance of {@link InputSecurityInfo }
     * 
     */
    public InputSecurityInfo createInputSecurityInfo() {
        return new InputSecurityInfo();
    }

    /**
     * Create an instance of {@link ArrayOfMetadataDeviceInfo }
     * 
     */
    public ArrayOfMetadataDeviceInfo createArrayOfMetadataDeviceInfo() {
        return new ArrayOfMetadataDeviceInfo();
    }

    /**
     * Create an instance of {@link MetadataDeviceInfo }
     * 
     */
    public MetadataDeviceInfo createMetadataDeviceInfo() {
        return new MetadataDeviceInfo();
    }

    /**
     * Create an instance of {@link MetadataDeviceSecurityInfo }
     * 
     */
    public MetadataDeviceSecurityInfo createMetadataDeviceSecurityInfo() {
        return new MetadataDeviceSecurityInfo();
    }

    /**
     * Create an instance of {@link ArrayOfMetadataTypeInfo }
     * 
     */
    public ArrayOfMetadataTypeInfo createArrayOfMetadataTypeInfo() {
        return new ArrayOfMetadataTypeInfo();
    }

    /**
     * Create an instance of {@link MetadataTypeInfo }
     * 
     */
    public MetadataTypeInfo createMetadataTypeInfo() {
        return new MetadataTypeInfo();
    }

    /**
     * Create an instance of {@link ArrayOfMicrophoneInfo }
     * 
     */
    public ArrayOfMicrophoneInfo createArrayOfMicrophoneInfo() {
        return new ArrayOfMicrophoneInfo();
    }

    /**
     * Create an instance of {@link MicrophoneInfo }
     * 
     */
    public MicrophoneInfo createMicrophoneInfo() {
        return new MicrophoneInfo();
    }

    /**
     * Create an instance of {@link MicrophoneSecurityInfo }
     * 
     */
    public MicrophoneSecurityInfo createMicrophoneSecurityInfo() {
        return new MicrophoneSecurityInfo();
    }

    /**
     * Create an instance of {@link ArrayOfOutputInfo }
     * 
     */
    public ArrayOfOutputInfo createArrayOfOutputInfo() {
        return new ArrayOfOutputInfo();
    }

    /**
     * Create an instance of {@link OutputInfo }
     * 
     */
    public OutputInfo createOutputInfo() {
        return new OutputInfo();
    }

    /**
     * Create an instance of {@link OutputSecurityInfo }
     * 
     */
    public OutputSecurityInfo createOutputSecurityInfo() {
        return new OutputSecurityInfo();
    }

    /**
     * Create an instance of {@link ArrayOfSpeakerInfo }
     * 
     */
    public ArrayOfSpeakerInfo createArrayOfSpeakerInfo() {
        return new ArrayOfSpeakerInfo();
    }

    /**
     * Create an instance of {@link SpeakerInfo }
     * 
     */
    public SpeakerInfo createSpeakerInfo() {
        return new SpeakerInfo();
    }

    /**
     * Create an instance of {@link SpeakerSecurityInfo }
     * 
     */
    public SpeakerSecurityInfo createSpeakerSecurityInfo() {
        return new SpeakerSecurityInfo();
    }

    /**
     * Create an instance of {@link RetentionSettingInfo }
     * 
     */
    public RetentionSettingInfo createRetentionSettingInfo() {
        return new RetentionSettingInfo();
    }

    /**
     * Create an instance of {@link ArrayOfRetentionOption }
     * 
     */
    public ArrayOfRetentionOption createArrayOfRetentionOption() {
        return new ArrayOfRetentionOption();
    }

    /**
     * Create an instance of {@link RetentionOption }
     * 
     */
    public RetentionOption createRetentionOption() {
        return new RetentionOption();
    }

    /**
     * Create an instance of {@link ArrayOfServerOption }
     * 
     */
    public ArrayOfServerOption createArrayOfServerOption() {
        return new ArrayOfServerOption();
    }

    /**
     * Create an instance of {@link ServerOption }
     * 
     */
    public ServerOption createServerOption() {
        return new ServerOption();
    }

    /**
     * Create an instance of {@link ArrayOfSpeakerGroupInfo }
     * 
     */
    public ArrayOfSpeakerGroupInfo createArrayOfSpeakerGroupInfo() {
        return new ArrayOfSpeakerGroupInfo();
    }

    /**
     * Create an instance of {@link SpeakerGroupInfo }
     * 
     */
    public SpeakerGroupInfo createSpeakerGroupInfo() {
        return new SpeakerGroupInfo();
    }

    /**
     * Create an instance of {@link ArrayOfSystemEventTypeInfo }
     * 
     */
    public ArrayOfSystemEventTypeInfo createArrayOfSystemEventTypeInfo() {
        return new ArrayOfSystemEventTypeInfo();
    }

    /**
     * Create an instance of {@link SystemEventTypeInfo }
     * 
     */
    public SystemEventTypeInfo createSystemEventTypeInfo() {
        return new SystemEventTypeInfo();
    }

    /**
     * Create an instance of {@link GetConfigurationRecorders }
     * 
     */
    public GetConfigurationRecorders createGetConfigurationRecorders() {
        return new GetConfigurationRecorders();
    }

    /**
     * Create an instance of {@link ArrayOfguid }
     * 
     */
    public ArrayOfguid createArrayOfguid() {
        return new ArrayOfguid();
    }

    /**
     * Create an instance of {@link GetConfigurationRecordersResponse }
     * 
     */
    public GetConfigurationRecordersResponse createGetConfigurationRecordersResponse() {
        return new GetConfigurationRecordersResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationHardware }
     * 
     */
    public GetConfigurationHardware createGetConfigurationHardware() {
        return new GetConfigurationHardware();
    }

    /**
     * Create an instance of {@link GetConfigurationHardwareResponse }
     * 
     */
    public GetConfigurationHardwareResponse createGetConfigurationHardwareResponse() {
        return new GetConfigurationHardwareResponse();
    }

    /**
     * Create an instance of {@link GetDevicesDisabled }
     * 
     */
    public GetDevicesDisabled createGetDevicesDisabled() {
        return new GetDevicesDisabled();
    }

    /**
     * Create an instance of {@link GetDevicesDisabledResponse }
     * 
     */
    public GetDevicesDisabledResponse createGetDevicesDisabledResponse() {
        return new GetDevicesDisabledResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDeviceDisabledInfo }
     * 
     */
    public ArrayOfDeviceDisabledInfo createArrayOfDeviceDisabledInfo() {
        return new ArrayOfDeviceDisabledInfo();
    }

    /**
     * Create an instance of {@link DeviceDisabledInfo }
     * 
     */
    public DeviceDisabledInfo createDeviceDisabledInfo() {
        return new DeviceDisabledInfo();
    }

    /**
     * Create an instance of {@link GetVmoServicesList }
     * 
     */
    public GetVmoServicesList createGetVmoServicesList() {
        return new GetVmoServicesList();
    }

    /**
     * Create an instance of {@link GetVmoServicesListResponse }
     * 
     */
    public GetVmoServicesListResponse createGetVmoServicesListResponse() {
        return new GetVmoServicesListResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDictionaryEntry }
     * 
     */
    public ArrayOfDictionaryEntry createArrayOfDictionaryEntry() {
        return new ArrayOfDictionaryEntry();
    }

    /**
     * Create an instance of {@link GetViewGroups }
     * 
     */
    public GetViewGroups createGetViewGroups() {
        return new GetViewGroups();
    }

    /**
     * Create an instance of {@link GetViewGroupsResponse }
     * 
     */
    public GetViewGroupsResponse createGetViewGroupsResponse() {
        return new GetViewGroupsResponse();
    }

    /**
     * Create an instance of {@link ArrayOfViewGroupInfo }
     * 
     */
    public ArrayOfViewGroupInfo createArrayOfViewGroupInfo() {
        return new ArrayOfViewGroupInfo();
    }

    /**
     * Create an instance of {@link ViewGroupInfo }
     * 
     */
    public ViewGroupInfo createViewGroupInfo() {
        return new ViewGroupInfo();
    }

    /**
     * Create an instance of {@link GetViewGroupData }
     * 
     */
    public GetViewGroupData createGetViewGroupData() {
        return new GetViewGroupData();
    }

    /**
     * Create an instance of {@link GetViewGroupDataResponse }
     * 
     */
    public GetViewGroupDataResponse createGetViewGroupDataResponse() {
        return new GetViewGroupDataResponse();
    }

    /**
     * Create an instance of {@link SetViewGroupData }
     * 
     */
    public SetViewGroupData createSetViewGroupData() {
        return new SetViewGroupData();
    }

    /**
     * Create an instance of {@link SetViewGroupDataResponse }
     * 
     */
    public SetViewGroupDataResponse createSetViewGroupDataResponse() {
        return new SetViewGroupDataResponse();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataGlobal }
     * 
     */
    public GetCustomSettingDataGlobal createGetCustomSettingDataGlobal() {
        return new GetCustomSettingDataGlobal();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataGlobalResponse }
     * 
     */
    public GetCustomSettingDataGlobalResponse createGetCustomSettingDataGlobalResponse() {
        return new GetCustomSettingDataGlobalResponse();
    }

    /**
     * Create an instance of {@link CustomSettingInfo }
     * 
     */
    public CustomSettingInfo createCustomSettingInfo() {
        return new CustomSettingInfo();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataUser }
     * 
     */
    public GetCustomSettingDataUser createGetCustomSettingDataUser() {
        return new GetCustomSettingDataUser();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataUserResponse }
     * 
     */
    public GetCustomSettingDataUserResponse createGetCustomSettingDataUserResponse() {
        return new GetCustomSettingDataUserResponse();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataGlobal }
     * 
     */
    public SetCustomSettingDataGlobal createSetCustomSettingDataGlobal() {
        return new SetCustomSettingDataGlobal();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataGlobalResponse }
     * 
     */
    public SetCustomSettingDataGlobalResponse createSetCustomSettingDataGlobalResponse() {
        return new SetCustomSettingDataGlobalResponse();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataUser }
     * 
     */
    public SetCustomSettingDataUser createSetCustomSettingDataUser() {
        return new SetCustomSettingDataUser();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataUserResponse }
     * 
     */
    public SetCustomSettingDataUserResponse createSetCustomSettingDataUserResponse() {
        return new SetCustomSettingDataUserResponse();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataGlobal2 }
     * 
     */
    public GetCustomSettingDataGlobal2 createGetCustomSettingDataGlobal2() {
        return new GetCustomSettingDataGlobal2();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataGlobal2Response }
     * 
     */
    public GetCustomSettingDataGlobal2Response createGetCustomSettingDataGlobal2Response() {
        return new GetCustomSettingDataGlobal2Response();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataUser2 }
     * 
     */
    public GetCustomSettingDataUser2 createGetCustomSettingDataUser2() {
        return new GetCustomSettingDataUser2();
    }

    /**
     * Create an instance of {@link GetCustomSettingDataUser2Response }
     * 
     */
    public GetCustomSettingDataUser2Response createGetCustomSettingDataUser2Response() {
        return new GetCustomSettingDataUser2Response();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataGlobal2 }
     * 
     */
    public SetCustomSettingDataGlobal2 createSetCustomSettingDataGlobal2() {
        return new SetCustomSettingDataGlobal2();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataGlobal2Response }
     * 
     */
    public SetCustomSettingDataGlobal2Response createSetCustomSettingDataGlobal2Response() {
        return new SetCustomSettingDataGlobal2Response();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataUser2 }
     * 
     */
    public SetCustomSettingDataUser2 createSetCustomSettingDataUser2() {
        return new SetCustomSettingDataUser2();
    }

    /**
     * Create an instance of {@link SetCustomSettingDataUser2Response }
     * 
     */
    public SetCustomSettingDataUser2Response createSetCustomSettingDataUser2Response() {
        return new SetCustomSettingDataUser2Response();
    }

    /**
     * Create an instance of {@link EventTrigger }
     * 
     */
    public EventTrigger createEventTrigger() {
        return new EventTrigger();
    }

    /**
     * Create an instance of {@link EventTriggerResponse }
     * 
     */
    public EventTriggerResponse createEventTriggerResponse() {
        return new EventTriggerResponse();
    }

    /**
     * Create an instance of {@link EventTriggerWithMetadata }
     * 
     */
    public EventTriggerWithMetadata createEventTriggerWithMetadata() {
        return new EventTriggerWithMetadata();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValue }
     * 
     */
    public ArrayOfKeyValue createArrayOfKeyValue() {
        return new ArrayOfKeyValue();
    }

    /**
     * Create an instance of {@link KeyValue }
     * 
     */
    public KeyValue createKeyValue() {
        return new KeyValue();
    }

    /**
     * Create an instance of {@link EventTriggerWithMetadataResponse }
     * 
     */
    public EventTriggerWithMetadataResponse createEventTriggerWithMetadataResponse() {
        return new EventTriggerWithMetadataResponse();
    }

    /**
     * Create an instance of {@link AlertsGetAroundWithSpan }
     * 
     */
    public AlertsGetAroundWithSpan createAlertsGetAroundWithSpan() {
        return new AlertsGetAroundWithSpan();
    }

    /**
     * Create an instance of {@link AlertsGetAroundWithSpanResponse }
     * 
     */
    public AlertsGetAroundWithSpanResponse createAlertsGetAroundWithSpanResponse() {
        return new AlertsGetAroundWithSpanResponse();
    }

    /**
     * Create an instance of {@link ArrayOfAlertOccurrence }
     * 
     */
    public ArrayOfAlertOccurrence createArrayOfAlertOccurrence() {
        return new ArrayOfAlertOccurrence();
    }

    /**
     * Create an instance of {@link AlertOccurrence }
     * 
     */
    public AlertOccurrence createAlertOccurrence() {
        return new AlertOccurrence();
    }

    /**
     * Create an instance of {@link MatrixMonitorActivateCamera }
     * 
     */
    public MatrixMonitorActivateCamera createMatrixMonitorActivateCamera() {
        return new MatrixMonitorActivateCamera();
    }

    /**
     * Create an instance of {@link MatrixMonitorActivateCameraResponse }
     * 
     */
    public MatrixMonitorActivateCameraResponse createMatrixMonitorActivateCameraResponse() {
        return new MatrixMonitorActivateCameraResponse();
    }

    /**
     * Create an instance of {@link QueryChanges }
     * 
     */
    public QueryChanges createQueryChanges() {
        return new QueryChanges();
    }

    /**
     * Create an instance of {@link QueryChangesResponse }
     * 
     */
    public QueryChangesResponse createQueryChangesResponse() {
        return new QueryChangesResponse();
    }

    /**
     * Create an instance of {@link ArrayOfChange }
     * 
     */
    public ArrayOfChange createArrayOfChange() {
        return new ArrayOfChange();
    }

    /**
     * Create an instance of {@link Change }
     * 
     */
    public Change createChange() {
        return new Change();
    }

    /**
     * Create an instance of {@link ChangeType }
     * 
     */
    public ChangeType createChangeType() {
        return new ChangeType();
    }

    /**
     * Create an instance of {@link QueryRecorderInfo }
     * 
     */
    public QueryRecorderInfo createQueryRecorderInfo() {
        return new QueryRecorderInfo();
    }

    /**
     * Create an instance of {@link QueryRecorderInfoResponse }
     * 
     */
    public QueryRecorderInfoResponse createQueryRecorderInfoResponse() {
        return new QueryRecorderInfoResponse();
    }

    /**
     * Create an instance of {@link BookmarkGetNewReference }
     * 
     */
    public BookmarkGetNewReference createBookmarkGetNewReference() {
        return new BookmarkGetNewReference();
    }

    /**
     * Create an instance of {@link BookmarkGetNewReferenceResponse }
     * 
     */
    public BookmarkGetNewReferenceResponse createBookmarkGetNewReferenceResponse() {
        return new BookmarkGetNewReferenceResponse();
    }

    /**
     * Create an instance of {@link BookmarkReference }
     * 
     */
    public BookmarkReference createBookmarkReference() {
        return new BookmarkReference();
    }

    /**
     * Create an instance of {@link BookmarkCreate }
     * 
     */
    public BookmarkCreate createBookmarkCreate() {
        return new BookmarkCreate();
    }

    /**
     * Create an instance of {@link BookmarkCreateResponse }
     * 
     */
    public BookmarkCreateResponse createBookmarkCreateResponse() {
        return new BookmarkCreateResponse();
    }

    /**
     * Create an instance of {@link Bookmark }
     * 
     */
    public Bookmark createBookmark() {
        return new Bookmark();
    }

    /**
     * Create an instance of {@link BookmarkUpdate }
     * 
     */
    public BookmarkUpdate createBookmarkUpdate() {
        return new BookmarkUpdate();
    }

    /**
     * Create an instance of {@link BookmarkUpdateResponse }
     * 
     */
    public BookmarkUpdateResponse createBookmarkUpdateResponse() {
        return new BookmarkUpdateResponse();
    }

    /**
     * Create an instance of {@link BookmarkDelete }
     * 
     */
    public BookmarkDelete createBookmarkDelete() {
        return new BookmarkDelete();
    }

    /**
     * Create an instance of {@link BookmarkDeleteResponse }
     * 
     */
    public BookmarkDeleteResponse createBookmarkDeleteResponse() {
        return new BookmarkDeleteResponse();
    }

    /**
     * Create an instance of {@link BookmarkGet }
     * 
     */
    public BookmarkGet createBookmarkGet() {
        return new BookmarkGet();
    }

    /**
     * Create an instance of {@link BookmarkGetResponse }
     * 
     */
    public BookmarkGetResponse createBookmarkGetResponse() {
        return new BookmarkGetResponse();
    }

    /**
     * Create an instance of {@link BookmarkSearchTime }
     * 
     */
    public BookmarkSearchTime createBookmarkSearchTime() {
        return new BookmarkSearchTime();
    }

    /**
     * Create an instance of {@link ArrayOfMediaDeviceType }
     * 
     */
    public ArrayOfMediaDeviceType createArrayOfMediaDeviceType() {
        return new ArrayOfMediaDeviceType();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }

    /**
     * Create an instance of {@link BookmarkSearchTimeResponse }
     * 
     */
    public BookmarkSearchTimeResponse createBookmarkSearchTimeResponse() {
        return new BookmarkSearchTimeResponse();
    }

    /**
     * Create an instance of {@link ArrayOfBookmark }
     * 
     */
    public ArrayOfBookmark createArrayOfBookmark() {
        return new ArrayOfBookmark();
    }

    /**
     * Create an instance of {@link BookmarkSearchFromBookmark }
     * 
     */
    public BookmarkSearchFromBookmark createBookmarkSearchFromBookmark() {
        return new BookmarkSearchFromBookmark();
    }

    /**
     * Create an instance of {@link BookmarkSearchFromBookmarkResponse }
     * 
     */
    public BookmarkSearchFromBookmarkResponse createBookmarkSearchFromBookmarkResponse() {
        return new BookmarkSearchFromBookmarkResponse();
    }

    /**
     * Create an instance of {@link RegisterIntegration }
     * 
     */
    public RegisterIntegration createRegisterIntegration() {
        return new RegisterIntegration();
    }

    /**
     * Create an instance of {@link RegisterIntegrationResponse }
     * 
     */
    public RegisterIntegrationResponse createRegisterIntegrationResponse() {
        return new RegisterIntegrationResponse();
    }

    /**
     * Create an instance of {@link RegisterPluginIntegrations }
     * 
     */
    public RegisterPluginIntegrations createRegisterPluginIntegrations() {
        return new RegisterPluginIntegrations();
    }

    /**
     * Create an instance of {@link ArrayOfPluginIntegration }
     * 
     */
    public ArrayOfPluginIntegration createArrayOfPluginIntegration() {
        return new ArrayOfPluginIntegration();
    }

    /**
     * Create an instance of {@link PluginIntegration }
     * 
     */
    public PluginIntegration createPluginIntegration() {
        return new PluginIntegration();
    }

    /**
     * Create an instance of {@link RegisterPluginIntegrationsResponse }
     * 
     */
    public RegisterPluginIntegrationsResponse createRegisterPluginIntegrationsResponse() {
        return new RegisterPluginIntegrationsResponse();
    }

    /**
     * Create an instance of {@link MarkedDataGetNewReference }
     * 
     */
    public MarkedDataGetNewReference createMarkedDataGetNewReference() {
        return new MarkedDataGetNewReference();
    }

    /**
     * Create an instance of {@link MarkedDataGetNewReferenceResponse }
     * 
     */
    public MarkedDataGetNewReferenceResponse createMarkedDataGetNewReferenceResponse() {
        return new MarkedDataGetNewReferenceResponse();
    }

    /**
     * Create an instance of {@link MarkedDataReference }
     * 
     */
    public MarkedDataReference createMarkedDataReference() {
        return new MarkedDataReference();
    }

    /**
     * Create an instance of {@link MarkedDataCreate }
     * 
     */
    public MarkedDataCreate createMarkedDataCreate() {
        return new MarkedDataCreate();
    }

    /**
     * Create an instance of {@link MarkedDataCreateResponse }
     * 
     */
    public MarkedDataCreateResponse createMarkedDataCreateResponse() {
        return new MarkedDataCreateResponse();
    }

    /**
     * Create an instance of {@link MarkedDataResult }
     * 
     */
    public MarkedDataResult createMarkedDataResult() {
        return new MarkedDataResult();
    }

    /**
     * Create an instance of {@link ArrayOfFaultDevice }
     * 
     */
    public ArrayOfFaultDevice createArrayOfFaultDevice() {
        return new ArrayOfFaultDevice();
    }

    /**
     * Create an instance of {@link FaultDevice }
     * 
     */
    public FaultDevice createFaultDevice() {
        return new FaultDevice();
    }

    /**
     * Create an instance of {@link MarkedData }
     * 
     */
    public MarkedData createMarkedData() {
        return new MarkedData();
    }

    /**
     * Create an instance of {@link ArrayOfWarningDevice }
     * 
     */
    public ArrayOfWarningDevice createArrayOfWarningDevice() {
        return new ArrayOfWarningDevice();
    }

    /**
     * Create an instance of {@link WarningDevice }
     * 
     */
    public WarningDevice createWarningDevice() {
        return new WarningDevice();
    }

    /**
     * Create an instance of {@link MarkedDataUpdate }
     * 
     */
    public MarkedDataUpdate createMarkedDataUpdate() {
        return new MarkedDataUpdate();
    }

    /**
     * Create an instance of {@link MarkedDataUpdateResponse }
     * 
     */
    public MarkedDataUpdateResponse createMarkedDataUpdateResponse() {
        return new MarkedDataUpdateResponse();
    }

    /**
     * Create an instance of {@link MarkedDataDelete }
     * 
     */
    public MarkedDataDelete createMarkedDataDelete() {
        return new MarkedDataDelete();
    }

    /**
     * Create an instance of {@link MarkedDataDeleteResponse }
     * 
     */
    public MarkedDataDeleteResponse createMarkedDataDeleteResponse() {
        return new MarkedDataDeleteResponse();
    }

    /**
     * Create an instance of {@link ArrayOfMarkedDataResult }
     * 
     */
    public ArrayOfMarkedDataResult createArrayOfMarkedDataResult() {
        return new ArrayOfMarkedDataResult();
    }

    /**
     * Create an instance of {@link MarkedDataGet }
     * 
     */
    public MarkedDataGet createMarkedDataGet() {
        return new MarkedDataGet();
    }

    /**
     * Create an instance of {@link MarkedDataGetResponse }
     * 
     */
    public MarkedDataGetResponse createMarkedDataGetResponse() {
        return new MarkedDataGetResponse();
    }

    /**
     * Create an instance of {@link MarkedDataSearch }
     * 
     */
    public MarkedDataSearch createMarkedDataSearch() {
        return new MarkedDataSearch();
    }

    /**
     * Create an instance of {@link MarkedDataSearchResponse }
     * 
     */
    public MarkedDataSearchResponse createMarkedDataSearchResponse() {
        return new MarkedDataSearchResponse();
    }

    /**
     * Create an instance of {@link ArrayOfMarkedData }
     * 
     */
    public ArrayOfMarkedData createArrayOfMarkedData() {
        return new ArrayOfMarkedData();
    }

    /**
     * Create an instance of {@link GetClientProfile }
     * 
     */
    public GetClientProfile createGetClientProfile() {
        return new GetClientProfile();
    }

    /**
     * Create an instance of {@link GetClientProfileResponse }
     * 
     */
    public GetClientProfileResponse createGetClientProfileResponse() {
        return new GetClientProfileResponse();
    }

    /**
     * Create an instance of {@link ClientProfile }
     * 
     */
    public ClientProfile createClientProfile() {
        return new ClientProfile();
    }

    /**
     * Create an instance of {@link ArrayOfClientProfileSetting }
     * 
     */
    public ArrayOfClientProfileSetting createArrayOfClientProfileSetting() {
        return new ArrayOfClientProfileSetting();
    }

    /**
     * Create an instance of {@link ClientProfileSetting }
     * 
     */
    public ClientProfileSetting createClientProfileSetting() {
        return new ClientProfileSetting();
    }

    /**
     * Create an instance of {@link GetClientProfile2 }
     * 
     */
    public GetClientProfile2 createGetClientProfile2() {
        return new GetClientProfile2();
    }

    /**
     * Create an instance of {@link GetClientProfile2Response }
     * 
     */
    public GetClientProfile2Response createGetClientProfile2Response() {
        return new GetClientProfile2Response();
    }

    /**
     * Create an instance of {@link GetClientProfile3 }
     * 
     */
    public GetClientProfile3 createGetClientProfile3() {
        return new GetClientProfile3();
    }

    /**
     * Create an instance of {@link GetClientProfile3Response }
     * 
     */
    public GetClientProfile3Response createGetClientProfile3Response() {
        return new GetClientProfile3Response();
    }

    /**
     * Create an instance of {@link GetPresets }
     * 
     */
    public GetPresets createGetPresets() {
        return new GetPresets();
    }

    /**
     * Create an instance of {@link GetPresetsResponse }
     * 
     */
    public GetPresetsResponse createGetPresetsResponse() {
        return new GetPresetsResponse();
    }

    /**
     * Create an instance of {@link DeletePreset }
     * 
     */
    public DeletePreset createDeletePreset() {
        return new DeletePreset();
    }

    /**
     * Create an instance of {@link DeletePresetResponse }
     * 
     */
    public DeletePresetResponse createDeletePresetResponse() {
        return new DeletePresetResponse();
    }

    /**
     * Create an instance of {@link UpdatePresetName }
     * 
     */
    public UpdatePresetName createUpdatePresetName() {
        return new UpdatePresetName();
    }

    /**
     * Create an instance of {@link UpdatePresetNameResponse }
     * 
     */
    public UpdatePresetNameResponse createUpdatePresetNameResponse() {
        return new UpdatePresetNameResponse();
    }

    /**
     * Create an instance of {@link UpdatePresetPosition }
     * 
     */
    public UpdatePresetPosition createUpdatePresetPosition() {
        return new UpdatePresetPosition();
    }

    /**
     * Create an instance of {@link UpdatePresetPositionResponse }
     * 
     */
    public UpdatePresetPositionResponse createUpdatePresetPositionResponse() {
        return new UpdatePresetPositionResponse();
    }

    /**
     * Create an instance of {@link CreatePreset }
     * 
     */
    public CreatePreset createCreatePreset() {
        return new CreatePreset();
    }

    /**
     * Create an instance of {@link CreatePresetResponse }
     * 
     */
    public CreatePresetResponse createCreatePresetResponse() {
        return new CreatePresetResponse();
    }

    /**
     * Create an instance of {@link OrderPresets }
     * 
     */
    public OrderPresets createOrderPresets() {
        return new OrderPresets();
    }

    /**
     * Create an instance of {@link OrderPresetsResponse }
     * 
     */
    public OrderPresetsResponse createOrderPresetsResponse() {
        return new OrderPresetsResponse();
    }

    /**
     * Create an instance of {@link DictionaryEntry }
     * 
     */
    public DictionaryEntry createDictionaryEntry() {
        return new DictionaryEntry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewGroupInternal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ViewGroupInternal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", name = "ViewGroupInternal")
    public JAXBElement<ViewGroupInternal> createViewGroupInternal(ViewGroupInternal value) {
        return new JAXBElement<ViewGroupInternal>(_ViewGroupInternal_QNAME, ViewGroupInternal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewGroupInternalData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ViewGroupInternalData }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", name = "ViewGroupInternalData")
    public JAXBElement<ViewGroupInternalData> createViewGroupInternalData(ViewGroupInternalData value) {
        return new JAXBElement<ViewGroupInternalData>(_ViewGroupInternalData_QNAME, ViewGroupInternalData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomSettingInternal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomSettingInternal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", name = "CustomSettingInternal")
    public JAXBElement<CustomSettingInternal> createCustomSettingInternal(CustomSettingInternal value) {
        return new JAXBElement<CustomSettingInternal>(_CustomSettingInternal_QNAME, CustomSettingInternal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomSettingInternalData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomSettingInternalData }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", name = "CustomSettingInternalData")
    public JAXBElement<CustomSettingInternalData> createCustomSettingInternalData(CustomSettingInternalData value) {
        return new JAXBElement<CustomSettingInternalData>(_CustomSettingInternalData_QNAME, CustomSettingInternalData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetentionOptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetentionOptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService", name = "RetentionOptionType")
    public JAXBElement<RetentionOptionType> createRetentionOptionType(RetentionOptionType value) {
        return new JAXBElement<RetentionOptionType>(_RetentionOptionType_QNAME, RetentionOptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ResultStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService", name = "ResultStatus")
    public JAXBElement<ResultStatus> createResultStatus(ResultStatus value) {
        return new JAXBElement<ResultStatus>(_ResultStatus_QNAME, ResultStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SortOrderOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SortOrderOption }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService", name = "SortOrderOption")
    public JAXBElement<SortOrderOption> createSortOrderOption(SortOrderOption value) {
        return new JAXBElement<SortOrderOption>(_SortOrderOption_QNAME, SortOrderOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProductInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ProductInfo")
    public JAXBElement<ProductInfo> createProductInfo(ProductInfo value) {
        return new JAXBElement<ProductInfo>(_ProductInfo_QNAME, ProductInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemLicenseInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SystemLicenseInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SystemLicenseInfo")
    public JAXBElement<SystemLicenseInfo> createSystemLicenseInfo(SystemLicenseInfo value) {
        return new JAXBElement<SystemLicenseInfo>(_SystemLicenseInfo_QNAME, SystemLicenseInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SmartClientVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SmartClientVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SmartClientVersion")
    public JAXBElement<SmartClientVersion> createSmartClientVersion(SmartClientVersion value) {
        return new JAXBElement<SmartClientVersion>(_SmartClientVersion_QNAME, SmartClientVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpgradeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpgradeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "UpgradeInfo")
    public JAXBElement<UpgradeInfo> createUpgradeInfo(UpgradeInfo value) {
        return new JAXBElement<UpgradeInfo>(_UpgradeInfo_QNAME, UpgradeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "LoginInfo")
    public JAXBElement<LoginInfo> createLoginInfo(LoginInfo value) {
        return new JAXBElement<LoginInfo>(_LoginInfo_QNAME, LoginInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeDuration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TimeDuration }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "TimeDuration")
    public JAXBElement<TimeDuration> createTimeDuration(TimeDuration value) {
        return new JAXBElement<TimeDuration>(_TimeDuration_QNAME, TimeDuration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorizationActionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthorizationActionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "AuthorizationActionType")
    public JAXBElement<AuthorizationActionType> createAuthorizationActionType(AuthorizationActionType value) {
        return new JAXBElement<AuthorizationActionType>(_AuthorizationActionType_QNAME, AuthorizationActionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UserInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "UserInfo")
    public JAXBElement<UserInfo> createUserInfo(UserInfo value) {
        return new JAXBElement<UserInfo>(_UserInfo_QNAME, UserInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConfigurationInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ConfigurationInfo")
    public JAXBElement<ConfigurationInfo> createConfigurationInfo(ConfigurationInfo value) {
        return new JAXBElement<ConfigurationInfo>(_ConfigurationInfo_QNAME, ConfigurationInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlertTypeGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlertTypeGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfAlertTypeGroupInfo")
    public JAXBElement<ArrayOfAlertTypeGroupInfo> createArrayOfAlertTypeGroupInfo(ArrayOfAlertTypeGroupInfo value) {
        return new JAXBElement<ArrayOfAlertTypeGroupInfo>(_ArrayOfAlertTypeGroupInfo_QNAME, ArrayOfAlertTypeGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlertTypeGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlertTypeGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "AlertTypeGroupInfo")
    public JAXBElement<AlertTypeGroupInfo> createAlertTypeGroupInfo(AlertTypeGroupInfo value) {
        return new JAXBElement<AlertTypeGroupInfo>(_AlertTypeGroupInfo_QNAME, AlertTypeGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "GroupInfo")
    public JAXBElement<GroupInfo> createGroupInfo(GroupInfo value) {
        return new JAXBElement<GroupInfo>(_GroupInfo_QNAME, GroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlertTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlertTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfAlertTypeInfo")
    public JAXBElement<ArrayOfAlertTypeInfo> createArrayOfAlertTypeInfo(ArrayOfAlertTypeInfo value) {
        return new JAXBElement<ArrayOfAlertTypeInfo>(_ArrayOfAlertTypeInfo_QNAME, ArrayOfAlertTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlertTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlertTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "AlertTypeInfo")
    public JAXBElement<AlertTypeInfo> createAlertTypeInfo(AlertTypeInfo value) {
        return new JAXBElement<AlertTypeInfo>(_AlertTypeInfo_QNAME, AlertTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ApplicationSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ApplicationSecurityInfo")
    public JAXBElement<ApplicationSecurityInfo> createApplicationSecurityInfo(ApplicationSecurityInfo value) {
        return new JAXBElement<ApplicationSecurityInfo>(_ApplicationSecurityInfo_QNAME, ApplicationSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SmartClientSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SmartClientSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SmartClientSecurityInfo")
    public JAXBElement<SmartClientSecurityInfo> createSmartClientSecurityInfo(SmartClientSecurityInfo value) {
        return new JAXBElement<SmartClientSecurityInfo>(_SmartClientSecurityInfo_QNAME, SmartClientSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAudioMessageInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAudioMessageInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfAudioMessageInfo")
    public JAXBElement<ArrayOfAudioMessageInfo> createArrayOfAudioMessageInfo(ArrayOfAudioMessageInfo value) {
        return new JAXBElement<ArrayOfAudioMessageInfo>(_ArrayOfAudioMessageInfo_QNAME, ArrayOfAudioMessageInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AudioMessageInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AudioMessageInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "AudioMessageInfo")
    public JAXBElement<AudioMessageInfo> createAudioMessageInfo(AudioMessageInfo value) {
        return new JAXBElement<AudioMessageInfo>(_AudioMessageInfo_QNAME, AudioMessageInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookmarkSettingInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BookmarkSettingInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "BookmarkSettingInfo")
    public JAXBElement<BookmarkSettingInfo> createBookmarkSettingInfo(BookmarkSettingInfo value) {
        return new JAXBElement<BookmarkSettingInfo>(_BookmarkSettingInfo_QNAME, BookmarkSettingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfCameraGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfCameraGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfCameraGroupInfo")
    public JAXBElement<ArrayOfCameraGroupInfo> createArrayOfCameraGroupInfo(ArrayOfCameraGroupInfo value) {
        return new JAXBElement<ArrayOfCameraGroupInfo>(_ArrayOfCameraGroupInfo_QNAME, ArrayOfCameraGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CameraGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CameraGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "CameraGroupInfo")
    public JAXBElement<CameraGroupInfo> createCameraGroupInfo(CameraGroupInfo value) {
        return new JAXBElement<CameraGroupInfo>(_CameraGroupInfo_QNAME, CameraGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDeviceMappingInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDeviceMappingInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfDeviceMappingInfo")
    public JAXBElement<ArrayOfDeviceMappingInfo> createArrayOfDeviceMappingInfo(ArrayOfDeviceMappingInfo value) {
        return new JAXBElement<ArrayOfDeviceMappingInfo>(_ArrayOfDeviceMappingInfo_QNAME, ArrayOfDeviceMappingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceMappingInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeviceMappingInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "DeviceMappingInfo")
    public JAXBElement<DeviceMappingInfo> createDeviceMappingInfo(DeviceMappingInfo value) {
        return new JAXBElement<DeviceMappingInfo>(_DeviceMappingInfo_QNAME, DeviceMappingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEventTypeGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEventTypeGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfEventTypeGroupInfo")
    public JAXBElement<ArrayOfEventTypeGroupInfo> createArrayOfEventTypeGroupInfo(ArrayOfEventTypeGroupInfo value) {
        return new JAXBElement<ArrayOfEventTypeGroupInfo>(_ArrayOfEventTypeGroupInfo_QNAME, ArrayOfEventTypeGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventTypeGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventTypeGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "EventTypeGroupInfo")
    public JAXBElement<EventTypeGroupInfo> createEventTypeGroupInfo(EventTypeGroupInfo value) {
        return new JAXBElement<EventTypeGroupInfo>(_EventTypeGroupInfo_QNAME, EventTypeGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEventTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEventTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfEventTypeInfo")
    public JAXBElement<ArrayOfEventTypeInfo> createArrayOfEventTypeInfo(ArrayOfEventTypeInfo value) {
        return new JAXBElement<ArrayOfEventTypeInfo>(_ArrayOfEventTypeInfo_QNAME, ArrayOfEventTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "EventTypeInfo")
    public JAXBElement<EventTypeInfo> createEventTypeInfo(EventTypeInfo value) {
        return new JAXBElement<EventTypeInfo>(_EventTypeInfo_QNAME, EventTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfInputGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfInputGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfInputGroupInfo")
    public JAXBElement<ArrayOfInputGroupInfo> createArrayOfInputGroupInfo(ArrayOfInputGroupInfo value) {
        return new JAXBElement<ArrayOfInputGroupInfo>(_ArrayOfInputGroupInfo_QNAME, ArrayOfInputGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InputGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "InputGroupInfo")
    public JAXBElement<InputGroupInfo> createInputGroupInfo(InputGroupInfo value) {
        return new JAXBElement<InputGroupInfo>(_InputGroupInfo_QNAME, InputGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfLicenseInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfLicenseInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfLicenseInfo")
    public JAXBElement<ArrayOfLicenseInfo> createArrayOfLicenseInfo(ArrayOfLicenseInfo value) {
        return new JAXBElement<ArrayOfLicenseInfo>(_ArrayOfLicenseInfo_QNAME, ArrayOfLicenseInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LicenseInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LicenseInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "LicenseInfo")
    public JAXBElement<LicenseInfo> createLicenseInfo(LicenseInfo value) {
        return new JAXBElement<LicenseInfo>(_LicenseInfo_QNAME, LicenseInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMatrixMonitorInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMatrixMonitorInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMatrixMonitorInfo")
    public JAXBElement<ArrayOfMatrixMonitorInfo> createArrayOfMatrixMonitorInfo(ArrayOfMatrixMonitorInfo value) {
        return new JAXBElement<ArrayOfMatrixMonitorInfo>(_ArrayOfMatrixMonitorInfo_QNAME, ArrayOfMatrixMonitorInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatrixMonitorInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MatrixMonitorInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MatrixMonitorInfo")
    public JAXBElement<MatrixMonitorInfo> createMatrixMonitorInfo(MatrixMonitorInfo value) {
        return new JAXBElement<MatrixMonitorInfo>(_MatrixMonitorInfo_QNAME, MatrixMonitorInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadataDeviceGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadataDeviceGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMetadataDeviceGroupInfo")
    public JAXBElement<ArrayOfMetadataDeviceGroupInfo> createArrayOfMetadataDeviceGroupInfo(ArrayOfMetadataDeviceGroupInfo value) {
        return new JAXBElement<ArrayOfMetadataDeviceGroupInfo>(_ArrayOfMetadataDeviceGroupInfo_QNAME, ArrayOfMetadataDeviceGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataDeviceGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetadataDeviceGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MetadataDeviceGroupInfo")
    public JAXBElement<MetadataDeviceGroupInfo> createMetadataDeviceGroupInfo(MetadataDeviceGroupInfo value) {
        return new JAXBElement<MetadataDeviceGroupInfo>(_MetadataDeviceGroupInfo_QNAME, MetadataDeviceGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMicrophoneGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMicrophoneGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMicrophoneGroupInfo")
    public JAXBElement<ArrayOfMicrophoneGroupInfo> createArrayOfMicrophoneGroupInfo(ArrayOfMicrophoneGroupInfo value) {
        return new JAXBElement<ArrayOfMicrophoneGroupInfo>(_ArrayOfMicrophoneGroupInfo_QNAME, ArrayOfMicrophoneGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MicrophoneGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MicrophoneGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MicrophoneGroupInfo")
    public JAXBElement<MicrophoneGroupInfo> createMicrophoneGroupInfo(MicrophoneGroupInfo value) {
        return new JAXBElement<MicrophoneGroupInfo>(_MicrophoneGroupInfo_QNAME, MicrophoneGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOutputGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfOutputGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfOutputGroupInfo")
    public JAXBElement<ArrayOfOutputGroupInfo> createArrayOfOutputGroupInfo(ArrayOfOutputGroupInfo value) {
        return new JAXBElement<ArrayOfOutputGroupInfo>(_ArrayOfOutputGroupInfo_QNAME, ArrayOfOutputGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OutputGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "OutputGroupInfo")
    public JAXBElement<OutputGroupInfo> createOutputGroupInfo(OutputGroupInfo value) {
        return new JAXBElement<OutputGroupInfo>(_OutputGroupInfo_QNAME, OutputGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRecorderInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfRecorderInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfRecorderInfo")
    public JAXBElement<ArrayOfRecorderInfo> createArrayOfRecorderInfo(ArrayOfRecorderInfo value) {
        return new JAXBElement<ArrayOfRecorderInfo>(_ArrayOfRecorderInfo_QNAME, ArrayOfRecorderInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecorderInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RecorderInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "RecorderInfo")
    public JAXBElement<RecorderInfo> createRecorderInfo(RecorderInfo value) {
        return new JAXBElement<RecorderInfo>(_RecorderInfo_QNAME, RecorderInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfCameraInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfCameraInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfCameraInfo")
    public JAXBElement<ArrayOfCameraInfo> createArrayOfCameraInfo(ArrayOfCameraInfo value) {
        return new JAXBElement<ArrayOfCameraInfo>(_ArrayOfCameraInfo_QNAME, ArrayOfCameraInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CameraInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CameraInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "CameraInfo")
    public JAXBElement<CameraInfo> createCameraInfo(CameraInfo value) {
        return new JAXBElement<CameraInfo>(_CameraInfo_QNAME, CameraInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeviceInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "DeviceInfo")
    public JAXBElement<DeviceInfo> createDeviceInfo(DeviceInfo value) {
        return new JAXBElement<DeviceInfo>(_DeviceInfo_QNAME, DeviceInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CameraSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CameraSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "CameraSecurityInfo")
    public JAXBElement<CameraSecurityInfo> createCameraSecurityInfo(CameraSecurityInfo value) {
        return new JAXBElement<CameraSecurityInfo>(_CameraSecurityInfo_QNAME, CameraSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PtzSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PtzSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "PtzSecurityInfo")
    public JAXBElement<PtzSecurityInfo> createPtzSecurityInfo(PtzSecurityInfo value) {
        return new JAXBElement<PtzSecurityInfo>(_PtzSecurityInfo_QNAME, PtzSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IpixInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IpixInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "IpixInfo")
    public JAXBElement<IpixInfo> createIpixInfo(IpixInfo value) {
        return new JAXBElement<IpixInfo>(_IpixInfo_QNAME, IpixInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HemisphereInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HemisphereInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "HemisphereInfo")
    public JAXBElement<HemisphereInfo> createHemisphereInfo(HemisphereInfo value) {
        return new JAXBElement<HemisphereInfo>(_HemisphereInfo_QNAME, HemisphereInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PositionInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PositionInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "PositionInfo")
    public JAXBElement<PositionInfo> createPositionInfo(PositionInfo value) {
        return new JAXBElement<PositionInfo>(_PositionInfo_QNAME, PositionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PanoramicLensInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PanoramicLensInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "PanoramicLensInfo")
    public JAXBElement<PanoramicLensInfo> createPanoramicLensInfo(PanoramicLensInfo value) {
        return new JAXBElement<PanoramicLensInfo>(_PanoramicLensInfo_QNAME, PanoramicLensInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImmerVisionInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ImmerVisionInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ImmerVisionInfo")
    public JAXBElement<ImmerVisionInfo> createImmerVisionInfo(ImmerVisionInfo value) {
        return new JAXBElement<ImmerVisionInfo>(_ImmerVisionInfo_QNAME, ImmerVisionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PtzInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PtzInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "PtzInfo")
    public JAXBElement<PtzInfo> createPtzInfo(PtzInfo value) {
        return new JAXBElement<PtzInfo>(_PtzInfo_QNAME, PtzInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfCapabilityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfCapabilityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfCapabilityInfo")
    public JAXBElement<ArrayOfCapabilityInfo> createArrayOfCapabilityInfo(ArrayOfCapabilityInfo value) {
        return new JAXBElement<ArrayOfCapabilityInfo>(_ArrayOfCapabilityInfo_QNAME, ArrayOfCapabilityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CapabilityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CapabilityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "CapabilityInfo")
    public JAXBElement<CapabilityInfo> createCapabilityInfo(CapabilityInfo value) {
        return new JAXBElement<CapabilityInfo>(_CapabilityInfo_QNAME, CapabilityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPresetInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfPresetInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfPresetInfo")
    public JAXBElement<ArrayOfPresetInfo> createArrayOfPresetInfo(ArrayOfPresetInfo value) {
        return new JAXBElement<ArrayOfPresetInfo>(_ArrayOfPresetInfo_QNAME, ArrayOfPresetInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PresetInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PresetInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "PresetInfo")
    public JAXBElement<PresetInfo> createPresetInfo(PresetInfo value) {
        return new JAXBElement<PresetInfo>(_PresetInfo_QNAME, PresetInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfStreamInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfStreamInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfStreamInfo")
    public JAXBElement<ArrayOfStreamInfo> createArrayOfStreamInfo(ArrayOfStreamInfo value) {
        return new JAXBElement<ArrayOfStreamInfo>(_ArrayOfStreamInfo_QNAME, ArrayOfStreamInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StreamInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StreamInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "StreamInfo")
    public JAXBElement<StreamInfo> createStreamInfo(StreamInfo value) {
        return new JAXBElement<StreamInfo>(_StreamInfo_QNAME, StreamInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTrackInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfTrackInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfTrackInfo")
    public JAXBElement<ArrayOfTrackInfo> createArrayOfTrackInfo(ArrayOfTrackInfo value) {
        return new JAXBElement<ArrayOfTrackInfo>(_ArrayOfTrackInfo_QNAME, ArrayOfTrackInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TrackInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TrackInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "TrackInfo")
    public JAXBElement<TrackInfo> createTrackInfo(TrackInfo value) {
        return new JAXBElement<TrackInfo>(_TrackInfo_QNAME, TrackInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfHardwareInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfHardwareInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfHardwareInfo")
    public JAXBElement<ArrayOfHardwareInfo> createArrayOfHardwareInfo(ArrayOfHardwareInfo value) {
        return new JAXBElement<ArrayOfHardwareInfo>(_ArrayOfHardwareInfo_QNAME, ArrayOfHardwareInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HardwareInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HardwareInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "HardwareInfo")
    public JAXBElement<HardwareInfo> createHardwareInfo(HardwareInfo value) {
        return new JAXBElement<HardwareInfo>(_HardwareInfo_QNAME, HardwareInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfInputInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfInputInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfInputInfo")
    public JAXBElement<ArrayOfInputInfo> createArrayOfInputInfo(ArrayOfInputInfo value) {
        return new JAXBElement<ArrayOfInputInfo>(_ArrayOfInputInfo_QNAME, ArrayOfInputInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InputInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "InputInfo")
    public JAXBElement<InputInfo> createInputInfo(InputInfo value) {
        return new JAXBElement<InputInfo>(_InputInfo_QNAME, InputInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InputSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "InputSecurityInfo")
    public JAXBElement<InputSecurityInfo> createInputSecurityInfo(InputSecurityInfo value) {
        return new JAXBElement<InputSecurityInfo>(_InputSecurityInfo_QNAME, InputSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadataDeviceInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadataDeviceInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMetadataDeviceInfo")
    public JAXBElement<ArrayOfMetadataDeviceInfo> createArrayOfMetadataDeviceInfo(ArrayOfMetadataDeviceInfo value) {
        return new JAXBElement<ArrayOfMetadataDeviceInfo>(_ArrayOfMetadataDeviceInfo_QNAME, ArrayOfMetadataDeviceInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataDeviceInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetadataDeviceInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MetadataDeviceInfo")
    public JAXBElement<MetadataDeviceInfo> createMetadataDeviceInfo(MetadataDeviceInfo value) {
        return new JAXBElement<MetadataDeviceInfo>(_MetadataDeviceInfo_QNAME, MetadataDeviceInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataDeviceSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetadataDeviceSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MetadataDeviceSecurityInfo")
    public JAXBElement<MetadataDeviceSecurityInfo> createMetadataDeviceSecurityInfo(MetadataDeviceSecurityInfo value) {
        return new JAXBElement<MetadataDeviceSecurityInfo>(_MetadataDeviceSecurityInfo_QNAME, MetadataDeviceSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadataTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadataTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMetadataTypeInfo")
    public JAXBElement<ArrayOfMetadataTypeInfo> createArrayOfMetadataTypeInfo(ArrayOfMetadataTypeInfo value) {
        return new JAXBElement<ArrayOfMetadataTypeInfo>(_ArrayOfMetadataTypeInfo_QNAME, ArrayOfMetadataTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetadataTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MetadataTypeInfo")
    public JAXBElement<MetadataTypeInfo> createMetadataTypeInfo(MetadataTypeInfo value) {
        return new JAXBElement<MetadataTypeInfo>(_MetadataTypeInfo_QNAME, MetadataTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMicrophoneInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMicrophoneInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMicrophoneInfo")
    public JAXBElement<ArrayOfMicrophoneInfo> createArrayOfMicrophoneInfo(ArrayOfMicrophoneInfo value) {
        return new JAXBElement<ArrayOfMicrophoneInfo>(_ArrayOfMicrophoneInfo_QNAME, ArrayOfMicrophoneInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MicrophoneInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MicrophoneInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MicrophoneInfo")
    public JAXBElement<MicrophoneInfo> createMicrophoneInfo(MicrophoneInfo value) {
        return new JAXBElement<MicrophoneInfo>(_MicrophoneInfo_QNAME, MicrophoneInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MicrophoneSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MicrophoneSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MicrophoneSecurityInfo")
    public JAXBElement<MicrophoneSecurityInfo> createMicrophoneSecurityInfo(MicrophoneSecurityInfo value) {
        return new JAXBElement<MicrophoneSecurityInfo>(_MicrophoneSecurityInfo_QNAME, MicrophoneSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOutputInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfOutputInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfOutputInfo")
    public JAXBElement<ArrayOfOutputInfo> createArrayOfOutputInfo(ArrayOfOutputInfo value) {
        return new JAXBElement<ArrayOfOutputInfo>(_ArrayOfOutputInfo_QNAME, ArrayOfOutputInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OutputInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "OutputInfo")
    public JAXBElement<OutputInfo> createOutputInfo(OutputInfo value) {
        return new JAXBElement<OutputInfo>(_OutputInfo_QNAME, OutputInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OutputSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "OutputSecurityInfo")
    public JAXBElement<OutputSecurityInfo> createOutputSecurityInfo(OutputSecurityInfo value) {
        return new JAXBElement<OutputSecurityInfo>(_OutputSecurityInfo_QNAME, OutputSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSpeakerInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSpeakerInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfSpeakerInfo")
    public JAXBElement<ArrayOfSpeakerInfo> createArrayOfSpeakerInfo(ArrayOfSpeakerInfo value) {
        return new JAXBElement<ArrayOfSpeakerInfo>(_ArrayOfSpeakerInfo_QNAME, ArrayOfSpeakerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpeakerInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SpeakerInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SpeakerInfo")
    public JAXBElement<SpeakerInfo> createSpeakerInfo(SpeakerInfo value) {
        return new JAXBElement<SpeakerInfo>(_SpeakerInfo_QNAME, SpeakerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpeakerSecurityInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SpeakerSecurityInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SpeakerSecurityInfo")
    public JAXBElement<SpeakerSecurityInfo> createSpeakerSecurityInfo(SpeakerSecurityInfo value) {
        return new JAXBElement<SpeakerSecurityInfo>(_SpeakerSecurityInfo_QNAME, SpeakerSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetentionSettingInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetentionSettingInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "RetentionSettingInfo")
    public JAXBElement<RetentionSettingInfo> createRetentionSettingInfo(RetentionSettingInfo value) {
        return new JAXBElement<RetentionSettingInfo>(_RetentionSettingInfo_QNAME, RetentionSettingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetentionOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfRetentionOption }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfRetentionOption")
    public JAXBElement<ArrayOfRetentionOption> createArrayOfRetentionOption(ArrayOfRetentionOption value) {
        return new JAXBElement<ArrayOfRetentionOption>(_ArrayOfRetentionOption_QNAME, ArrayOfRetentionOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetentionOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetentionOption }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "RetentionOption")
    public JAXBElement<RetentionOption> createRetentionOption(RetentionOption value) {
        return new JAXBElement<RetentionOption>(_RetentionOption_QNAME, RetentionOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfServerOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfServerOption }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfServerOption")
    public JAXBElement<ArrayOfServerOption> createArrayOfServerOption(ArrayOfServerOption value) {
        return new JAXBElement<ArrayOfServerOption>(_ArrayOfServerOption_QNAME, ArrayOfServerOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServerOption }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ServerOption")
    public JAXBElement<ServerOption> createServerOption(ServerOption value) {
        return new JAXBElement<ServerOption>(_ServerOption_QNAME, ServerOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSpeakerGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSpeakerGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfSpeakerGroupInfo")
    public JAXBElement<ArrayOfSpeakerGroupInfo> createArrayOfSpeakerGroupInfo(ArrayOfSpeakerGroupInfo value) {
        return new JAXBElement<ArrayOfSpeakerGroupInfo>(_ArrayOfSpeakerGroupInfo_QNAME, ArrayOfSpeakerGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpeakerGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SpeakerGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SpeakerGroupInfo")
    public JAXBElement<SpeakerGroupInfo> createSpeakerGroupInfo(SpeakerGroupInfo value) {
        return new JAXBElement<SpeakerGroupInfo>(_SpeakerGroupInfo_QNAME, SpeakerGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSystemEventTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSystemEventTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfSystemEventTypeInfo")
    public JAXBElement<ArrayOfSystemEventTypeInfo> createArrayOfSystemEventTypeInfo(ArrayOfSystemEventTypeInfo value) {
        return new JAXBElement<ArrayOfSystemEventTypeInfo>(_ArrayOfSystemEventTypeInfo_QNAME, ArrayOfSystemEventTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemEventTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SystemEventTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SystemEventTypeInfo")
    public JAXBElement<SystemEventTypeInfo> createSystemEventTypeInfo(SystemEventTypeInfo value) {
        return new JAXBElement<SystemEventTypeInfo>(_SystemEventTypeInfo_QNAME, SystemEventTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventSource }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventSource }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "EventSource")
    public JAXBElement<EventSource> createEventSource(EventSource value) {
        return new JAXBElement<EventSource>(_EventSource_QNAME, EventSource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDeviceDisabledInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDeviceDisabledInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfDeviceDisabledInfo")
    public JAXBElement<ArrayOfDeviceDisabledInfo> createArrayOfDeviceDisabledInfo(ArrayOfDeviceDisabledInfo value) {
        return new JAXBElement<ArrayOfDeviceDisabledInfo>(_ArrayOfDeviceDisabledInfo_QNAME, ArrayOfDeviceDisabledInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceDisabledInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeviceDisabledInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "DeviceDisabledInfo")
    public JAXBElement<DeviceDisabledInfo> createDeviceDisabledInfo(DeviceDisabledInfo value) {
        return new JAXBElement<DeviceDisabledInfo>(_DeviceDisabledInfo_QNAME, DeviceDisabledInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfViewGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfViewGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfViewGroupInfo")
    public JAXBElement<ArrayOfViewGroupInfo> createArrayOfViewGroupInfo(ArrayOfViewGroupInfo value) {
        return new JAXBElement<ArrayOfViewGroupInfo>(_ArrayOfViewGroupInfo_QNAME, ArrayOfViewGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewGroupInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ViewGroupInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ViewGroupInfo")
    public JAXBElement<ViewGroupInfo> createViewGroupInfo(ViewGroupInfo value) {
        return new JAXBElement<ViewGroupInfo>(_ViewGroupInfo_QNAME, ViewGroupInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewGroupType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ViewGroupType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ViewGroupType")
    public JAXBElement<ViewGroupType> createViewGroupType(ViewGroupType value) {
        return new JAXBElement<ViewGroupType>(_ViewGroupType_QNAME, ViewGroupType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetViewGroupDataResultType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SetViewGroupDataResultType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SetViewGroupDataResultType")
    public JAXBElement<SetViewGroupDataResultType> createSetViewGroupDataResultType(SetViewGroupDataResultType value) {
        return new JAXBElement<SetViewGroupDataResultType>(_SetViewGroupDataResultType_QNAME, SetViewGroupDataResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomSettingInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomSettingInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "CustomSettingInfo")
    public JAXBElement<CustomSettingInfo> createCustomSettingInfo(CustomSettingInfo value) {
        return new JAXBElement<CustomSettingInfo>(_CustomSettingInfo_QNAME, CustomSettingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCustomSettingResultType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SetCustomSettingResultType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "SetCustomSettingResultType")
    public JAXBElement<SetCustomSettingResultType> createSetCustomSettingResultType(SetCustomSettingResultType value) {
        return new JAXBElement<SetCustomSettingResultType>(_SetCustomSettingResultType_QNAME, SetCustomSettingResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValue }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValue }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfKeyValue")
    public JAXBElement<ArrayOfKeyValue> createArrayOfKeyValue(ArrayOfKeyValue value) {
        return new JAXBElement<ArrayOfKeyValue>(_ArrayOfKeyValue_QNAME, ArrayOfKeyValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyValue }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link KeyValue }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "KeyValue")
    public JAXBElement<KeyValue> createKeyValue(KeyValue value) {
        return new JAXBElement<KeyValue>(_KeyValue_QNAME, KeyValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlertOccurrence }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlertOccurrence }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfAlertOccurrence")
    public JAXBElement<ArrayOfAlertOccurrence> createArrayOfAlertOccurrence(ArrayOfAlertOccurrence value) {
        return new JAXBElement<ArrayOfAlertOccurrence>(_ArrayOfAlertOccurrence_QNAME, ArrayOfAlertOccurrence.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlertOccurrence }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlertOccurrence }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "AlertOccurrence")
    public JAXBElement<AlertOccurrence> createAlertOccurrence(AlertOccurrence value) {
        return new JAXBElement<AlertOccurrence>(_AlertOccurrence_QNAME, AlertOccurrence.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfChange }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfChange }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfChange")
    public JAXBElement<ArrayOfChange> createArrayOfChange(ArrayOfChange value) {
        return new JAXBElement<ArrayOfChange>(_ArrayOfChange_QNAME, ArrayOfChange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Change }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Change }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "Change")
    public JAXBElement<Change> createChange(Change value) {
        return new JAXBElement<Change>(_Change_QNAME, Change.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ChangeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ChangeType")
    public JAXBElement<ChangeType> createChangeType(ChangeType value) {
        return new JAXBElement<ChangeType>(_ChangeType_QNAME, ChangeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Bookmark }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Bookmark }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "Bookmark")
    public JAXBElement<Bookmark> createBookmark(Bookmark value) {
        return new JAXBElement<Bookmark>(_Bookmark_QNAME, Bookmark.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMediaDeviceType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMediaDeviceType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMediaDeviceType")
    public JAXBElement<ArrayOfMediaDeviceType> createArrayOfMediaDeviceType(ArrayOfMediaDeviceType value) {
        return new JAXBElement<ArrayOfMediaDeviceType>(_ArrayOfMediaDeviceType_QNAME, ArrayOfMediaDeviceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MediaDeviceType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MediaDeviceType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MediaDeviceType")
    public JAXBElement<MediaDeviceType> createMediaDeviceType(MediaDeviceType value) {
        return new JAXBElement<MediaDeviceType>(_MediaDeviceType_QNAME, MediaDeviceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBookmark }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfBookmark }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfBookmark")
    public JAXBElement<ArrayOfBookmark> createArrayOfBookmark(ArrayOfBookmark value) {
        return new JAXBElement<ArrayOfBookmark>(_ArrayOfBookmark_QNAME, ArrayOfBookmark.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntegrationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IntegrationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "IntegrationType")
    public JAXBElement<IntegrationType> createIntegrationType(IntegrationType value) {
        return new JAXBElement<IntegrationType>(_IntegrationType_QNAME, IntegrationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPluginIntegration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfPluginIntegration }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfPluginIntegration")
    public JAXBElement<ArrayOfPluginIntegration> createArrayOfPluginIntegration(ArrayOfPluginIntegration value) {
        return new JAXBElement<ArrayOfPluginIntegration>(_ArrayOfPluginIntegration_QNAME, ArrayOfPluginIntegration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PluginIntegration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PluginIntegration }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "PluginIntegration")
    public JAXBElement<PluginIntegration> createPluginIntegration(PluginIntegration value) {
        return new JAXBElement<PluginIntegration>(_PluginIntegration_QNAME, PluginIntegration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarkedDataResult }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MarkedDataResult }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MarkedDataResult")
    public JAXBElement<MarkedDataResult> createMarkedDataResult(MarkedDataResult value) {
        return new JAXBElement<MarkedDataResult>(_MarkedDataResult_QNAME, MarkedDataResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfFaultDevice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfFaultDevice }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfFaultDevice")
    public JAXBElement<ArrayOfFaultDevice> createArrayOfFaultDevice(ArrayOfFaultDevice value) {
        return new JAXBElement<ArrayOfFaultDevice>(_ArrayOfFaultDevice_QNAME, ArrayOfFaultDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultDevice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FaultDevice }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "FaultDevice")
    public JAXBElement<FaultDevice> createFaultDevice(FaultDevice value) {
        return new JAXBElement<FaultDevice>(_FaultDevice_QNAME, FaultDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarkedData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MarkedData }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "MarkedData")
    public JAXBElement<MarkedData> createMarkedData(MarkedData value) {
        return new JAXBElement<MarkedData>(_MarkedData_QNAME, MarkedData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfWarningDevice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfWarningDevice }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfWarningDevice")
    public JAXBElement<ArrayOfWarningDevice> createArrayOfWarningDevice(ArrayOfWarningDevice value) {
        return new JAXBElement<ArrayOfWarningDevice>(_ArrayOfWarningDevice_QNAME, ArrayOfWarningDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WarningDevice }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link WarningDevice }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "WarningDevice")
    public JAXBElement<WarningDevice> createWarningDevice(WarningDevice value) {
        return new JAXBElement<WarningDevice>(_WarningDevice_QNAME, WarningDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMarkedDataResult }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMarkedDataResult }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMarkedDataResult")
    public JAXBElement<ArrayOfMarkedDataResult> createArrayOfMarkedDataResult(ArrayOfMarkedDataResult value) {
        return new JAXBElement<ArrayOfMarkedDataResult>(_ArrayOfMarkedDataResult_QNAME, ArrayOfMarkedDataResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMarkedData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMarkedData }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfMarkedData")
    public JAXBElement<ArrayOfMarkedData> createArrayOfMarkedData(ArrayOfMarkedData value) {
        return new JAXBElement<ArrayOfMarkedData>(_ArrayOfMarkedData_QNAME, ArrayOfMarkedData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientProfile }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientProfile }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ClientProfile")
    public JAXBElement<ClientProfile> createClientProfile(ClientProfile value) {
        return new JAXBElement<ClientProfile>(_ClientProfile_QNAME, ClientProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfClientProfileSetting }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfClientProfileSetting }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ArrayOfClientProfileSetting")
    public JAXBElement<ArrayOfClientProfileSetting> createArrayOfClientProfileSetting(ArrayOfClientProfileSetting value) {
        return new JAXBElement<ArrayOfClientProfileSetting>(_ArrayOfClientProfileSetting_QNAME, ArrayOfClientProfileSetting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientProfileSetting }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientProfileSetting }{@code >}
     */
    @XmlElementDecl(namespace = "http://video.net/2/XProtectCSServerCommand", name = "ClientProfileSetting")
    public JAXBElement<ClientProfileSetting> createClientProfileSetting(ClientProfileSetting value) {
        return new JAXBElement<ClientProfileSetting>(_ClientProfileSetting_QNAME, ClientProfileSetting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfstring")
    public JAXBElement<ArrayOfstring> createArrayOfstring(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_ArrayOfstring_QNAME, ArrayOfstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfguid }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfguid }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfguid")
    public JAXBElement<ArrayOfguid> createArrayOfguid(ArrayOfguid value) {
        return new JAXBElement<ArrayOfguid>(_ArrayOfguid_QNAME, ArrayOfguid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDictionaryEntry }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDictionaryEntry }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System.Collections", name = "ArrayOfDictionaryEntry")
    public JAXBElement<ArrayOfDictionaryEntry> createArrayOfDictionaryEntry(ArrayOfDictionaryEntry value) {
        return new JAXBElement<ArrayOfDictionaryEntry>(_ArrayOfDictionaryEntry_QNAME, ArrayOfDictionaryEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DictionaryEntry }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DictionaryEntry }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System.Collections", name = "DictionaryEntry")
    public JAXBElement<DictionaryEntry> createDictionaryEntry(DictionaryEntry value) {
        return new JAXBElement<DictionaryEntry>(_DictionaryEntry_QNAME, DictionaryEntry.class, null, value);
    }

}
