
package com.mip.alarmService;

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
 * generated in the com.mip.alarmService package. 
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

    private final static QName _ArrayOfbase64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfbase64Binary");
    private final static QName _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    private final static QName _ArrayOfguid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfguid");
    private final static QName _ArrayOfanyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfanyType");
    private final static QName _Alarm_QNAME = new QName("urn:milestone-systems", "Alarm");
    private final static QName _EventHeader_QNAME = new QName("urn:milestone-systems", "EventHeader");
    private final static QName _EventSource_QNAME = new QName("urn:milestone-systems", "EventSource");
    private final static QName _FQID_QNAME = new QName("urn:milestone-systems", "FQID");
    private final static QName _ServerId_QNAME = new QName("urn:milestone-systems", "ServerId");
    private final static QName _FolderType_QNAME = new QName("urn:milestone-systems", "FolderType");
    private final static QName _RuleList_QNAME = new QName("urn:milestone-systems", "RuleList");
    private final static QName _Rule_QNAME = new QName("urn:milestone-systems", "Rule");
    private final static QName _PolygonList_QNAME = new QName("urn:milestone-systems", "PolygonList");
    private final static QName _TPolygon_QNAME = new QName("urn:milestone-systems", "TPolygon");
    private final static QName _TColor_QNAME = new QName("urn:milestone-systems", "TColor");
    private final static QName _PointList_QNAME = new QName("urn:milestone-systems", "PointList");
    private final static QName _TPoint_QNAME = new QName("urn:milestone-systems", "TPoint");
    private final static QName _AnalyticsObjectList_QNAME = new QName("urn:milestone-systems", "AnalyticsObjectList");
    private final static QName _AnalyticsObject_QNAME = new QName("urn:milestone-systems", "AnalyticsObject");
    private final static QName _BoundingBox_QNAME = new QName("urn:milestone-systems", "BoundingBox");
    private final static QName _ObjectMotion_QNAME = new QName("urn:milestone-systems", "ObjectMotion");
    private final static QName _ReferenceList_QNAME = new QName("urn:milestone-systems", "ReferenceList");
    private final static QName _Reference_QNAME = new QName("urn:milestone-systems", "Reference");
    private final static QName _SnapshotList_QNAME = new QName("urn:milestone-systems", "SnapshotList");
    private final static QName _Snapshot_QNAME = new QName("urn:milestone-systems", "Snapshot");
    private final static QName _Vendor_QNAME = new QName("urn:milestone-systems", "Vendor");
    private final static QName _BaseEvent_QNAME = new QName("urn:milestone-systems", "BaseEvent");
    private final static QName _AnalyticsEvent_QNAME = new QName("urn:milestone-systems", "AnalyticsEvent");
    private final static QName _EventData_QNAME = new QName("urn:milestone-systems", "EventData");
    private final static QName _EventSequence_QNAME = new QName("urn:milestone-systems", "EventSequence");
    private final static QName _AccessControlEvent_QNAME = new QName("urn:milestone-systems", "AccessControlEvent");
    private final static QName _ArrayOfFQID_QNAME = new QName("urn:milestone-systems", "ArrayOfFQID");
    private final static QName _ArrayOfProperty_QNAME = new QName("urn:milestone-systems", "ArrayOfProperty");
    private final static QName _Property_QNAME = new QName("urn:milestone-systems", "Property");
    private final static QName _ArrayOfBaseEvent_QNAME = new QName("urn:milestone-systems", "ArrayOfBaseEvent");
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
    private final static QName _AlarmServiceFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmServiceFault");
    private final static QName _AlarmFaultType_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmFaultType");
    private final static QName _AlarmFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmFilter");
    private final static QName _ArrayOfCondition_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfCondition");
    private final static QName _Condition_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "Condition");
    private final static QName _Operator_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "Operator");
    private final static QName _Target_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "Target");
    private final static QName _ArrayOfOrderBy_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfOrderBy");
    private final static QName _OrderBy_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "OrderBy");
    private final static QName _Order_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "Order");
    private final static QName _AlarmUpdateData_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmUpdateData");
    private final static QName _ArrayOfAlarmLine_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfAlarmLine");
    private final static QName _AlarmLine_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmLine");
    private final static QName _ArrayOfAlarmSearchCriteria_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfAlarmSearchCriteria");
    private final static QName _AlarmSearchCriteria_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmSearchCriteria");
    private final static QName _NameNumberPairValue_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "NameNumberPairValue");
    private final static QName _EventFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "EventFilter");
    private final static QName _EventUpdateData_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "EventUpdateData");
    private final static QName _ArrayOfEventLine_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfEventLine");
    private final static QName _EventLine_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "EventLine");
    private final static QName _ArrayOfStatistic_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfStatistic");
    private final static QName _Statistic_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "Statistic");
    private final static QName _StatisticType_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "StatisticType");
    private final static QName _ArrayOfAlarmHistory_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfAlarmHistory");
    private final static QName _AlarmHistory_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmHistory");
    private final static QName _ArrayOfAlarmUpdate_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfAlarmUpdate");
    private final static QName _AlarmUpdate_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "AlarmUpdate");
    private final static QName _GetEventLines2Params_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "GetEventLines2Params");
    private final static QName _SearchGroup_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "SearchGroup");
    private final static QName _LogicalOperator_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "LogicalOperator");
    private final static QName _ArrayOfSearchElement_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfSearchElement");
    private final static QName _SearchElement_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "SearchElement");
    private final static QName _SearchOperator_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "SearchOperator");
    private final static QName _ArrayOfSearchGroup_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfSearchGroup");
    private final static QName _SearchOrders_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "SearchOrders");
    private final static QName _ArrayOfSearchOrder_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "ArrayOfSearchOrder");
    private final static QName _SearchOrder_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "SearchOrder");
    private final static QName _Direction_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "Direction");
    private final static QName _GetEventLines2Resp_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "GetEventLines2Resp");
    private final static QName _GetEventCountParams_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "GetEventCountParams");
    private final static QName _GetEventCountResp_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "GetEventCountResp");
    private final static QName _GetEventsParams_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "GetEventsParams");
    private final static QName _GetEventsResp_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", "GetEventsResp");
    private final static QName _ArrayOfKeyValuePairOfstringstring_QNAME = new QName("http://schemas.datacontract.org/2004/07/System.Collections.Generic", "ArrayOfKeyValuePairOfstringstring");
    private final static QName _KeyValuePairOfstringstring_QNAME = new QName("http://schemas.datacontract.org/2004/07/System.Collections.Generic", "KeyValuePairOfstringstring");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mip.alarmService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfbase64Binary }
     * 
     */
    public ArrayOfbase64Binary createArrayOfbase64Binary() {
        return new ArrayOfbase64Binary();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }

    /**
     * Create an instance of {@link ArrayOfguid }
     * 
     */
    public ArrayOfguid createArrayOfguid() {
        return new ArrayOfguid();
    }

    /**
     * Create an instance of {@link ArrayOfanyType }
     * 
     */
    public ArrayOfanyType createArrayOfanyType() {
        return new ArrayOfanyType();
    }

    /**
     * Create an instance of {@link Alarm }
     * 
     */
    public Alarm createAlarm() {
        return new Alarm();
    }

    /**
     * Create an instance of {@link EventHeader }
     * 
     */
    public EventHeader createEventHeader() {
        return new EventHeader();
    }

    /**
     * Create an instance of {@link EventSource }
     * 
     */
    public EventSource createEventSource() {
        return new EventSource();
    }

    /**
     * Create an instance of {@link FQID }
     * 
     */
    public FQID createFQID() {
        return new FQID();
    }

    /**
     * Create an instance of {@link ServerId }
     * 
     */
    public ServerId createServerId() {
        return new ServerId();
    }

    /**
     * Create an instance of {@link RuleList }
     * 
     */
    public RuleList createRuleList() {
        return new RuleList();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link PolygonList }
     * 
     */
    public PolygonList createPolygonList() {
        return new PolygonList();
    }

    /**
     * Create an instance of {@link TPolygon }
     * 
     */
    public TPolygon createTPolygon() {
        return new TPolygon();
    }

    /**
     * Create an instance of {@link TColor }
     * 
     */
    public TColor createTColor() {
        return new TColor();
    }

    /**
     * Create an instance of {@link PointList }
     * 
     */
    public PointList createPointList() {
        return new PointList();
    }

    /**
     * Create an instance of {@link TPoint }
     * 
     */
    public TPoint createTPoint() {
        return new TPoint();
    }

    /**
     * Create an instance of {@link AnalyticsObjectList }
     * 
     */
    public AnalyticsObjectList createAnalyticsObjectList() {
        return new AnalyticsObjectList();
    }

    /**
     * Create an instance of {@link AnalyticsObject }
     * 
     */
    public AnalyticsObject createAnalyticsObject() {
        return new AnalyticsObject();
    }

    /**
     * Create an instance of {@link BoundingBox }
     * 
     */
    public BoundingBox createBoundingBox() {
        return new BoundingBox();
    }

    /**
     * Create an instance of {@link ObjectMotion }
     * 
     */
    public ObjectMotion createObjectMotion() {
        return new ObjectMotion();
    }

    /**
     * Create an instance of {@link ReferenceList }
     * 
     */
    public ReferenceList createReferenceList() {
        return new ReferenceList();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link SnapshotList }
     * 
     */
    public SnapshotList createSnapshotList() {
        return new SnapshotList();
    }

    /**
     * Create an instance of {@link Snapshot }
     * 
     */
    public Snapshot createSnapshot() {
        return new Snapshot();
    }

    /**
     * Create an instance of {@link Vendor }
     * 
     */
    public Vendor createVendor() {
        return new Vendor();
    }

    /**
     * Create an instance of {@link BaseEvent }
     * 
     */
    public BaseEvent createBaseEvent() {
        return new BaseEvent();
    }

    /**
     * Create an instance of {@link AnalyticsEvent }
     * 
     */
    public AnalyticsEvent createAnalyticsEvent() {
        return new AnalyticsEvent();
    }

    /**
     * Create an instance of {@link EventData }
     * 
     */
    public EventData createEventData() {
        return new EventData();
    }

    /**
     * Create an instance of {@link EventSequence }
     * 
     */
    public EventSequence createEventSequence() {
        return new EventSequence();
    }

    /**
     * Create an instance of {@link AccessControlEvent }
     * 
     */
    public AccessControlEvent createAccessControlEvent() {
        return new AccessControlEvent();
    }

    /**
     * Create an instance of {@link ArrayOfFQID }
     * 
     */
    public ArrayOfFQID createArrayOfFQID() {
        return new ArrayOfFQID();
    }

    /**
     * Create an instance of {@link ArrayOfProperty }
     * 
     */
    public ArrayOfProperty createArrayOfProperty() {
        return new ArrayOfProperty();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link ArrayOfBaseEvent }
     * 
     */
    public ArrayOfBaseEvent createArrayOfBaseEvent() {
        return new ArrayOfBaseEvent();
    }

    /**
     * Create an instance of {@link AlarmServiceFault }
     * 
     */
    public AlarmServiceFault createAlarmServiceFault() {
        return new AlarmServiceFault();
    }

    /**
     * Create an instance of {@link AlarmFilter }
     * 
     */
    public AlarmFilter createAlarmFilter() {
        return new AlarmFilter();
    }

    /**
     * Create an instance of {@link ArrayOfCondition }
     * 
     */
    public ArrayOfCondition createArrayOfCondition() {
        return new ArrayOfCondition();
    }

    /**
     * Create an instance of {@link Condition }
     * 
     */
    public Condition createCondition() {
        return new Condition();
    }

    /**
     * Create an instance of {@link ArrayOfOrderBy }
     * 
     */
    public ArrayOfOrderBy createArrayOfOrderBy() {
        return new ArrayOfOrderBy();
    }

    /**
     * Create an instance of {@link OrderBy }
     * 
     */
    public OrderBy createOrderBy() {
        return new OrderBy();
    }

    /**
     * Create an instance of {@link AlarmUpdateData }
     * 
     */
    public AlarmUpdateData createAlarmUpdateData() {
        return new AlarmUpdateData();
    }

    /**
     * Create an instance of {@link ArrayOfAlarmLine }
     * 
     */
    public ArrayOfAlarmLine createArrayOfAlarmLine() {
        return new ArrayOfAlarmLine();
    }

    /**
     * Create an instance of {@link AlarmLine }
     * 
     */
    public AlarmLine createAlarmLine() {
        return new AlarmLine();
    }

    /**
     * Create an instance of {@link ArrayOfAlarmSearchCriteria }
     * 
     */
    public ArrayOfAlarmSearchCriteria createArrayOfAlarmSearchCriteria() {
        return new ArrayOfAlarmSearchCriteria();
    }

    /**
     * Create an instance of {@link AlarmSearchCriteria }
     * 
     */
    public AlarmSearchCriteria createAlarmSearchCriteria() {
        return new AlarmSearchCriteria();
    }

    /**
     * Create an instance of {@link NameNumberPairValue }
     * 
     */
    public NameNumberPairValue createNameNumberPairValue() {
        return new NameNumberPairValue();
    }

    /**
     * Create an instance of {@link EventFilter }
     * 
     */
    public EventFilter createEventFilter() {
        return new EventFilter();
    }

    /**
     * Create an instance of {@link EventUpdateData }
     * 
     */
    public EventUpdateData createEventUpdateData() {
        return new EventUpdateData();
    }

    /**
     * Create an instance of {@link ArrayOfEventLine }
     * 
     */
    public ArrayOfEventLine createArrayOfEventLine() {
        return new ArrayOfEventLine();
    }

    /**
     * Create an instance of {@link EventLine }
     * 
     */
    public EventLine createEventLine() {
        return new EventLine();
    }

    /**
     * Create an instance of {@link ArrayOfStatistic }
     * 
     */
    public ArrayOfStatistic createArrayOfStatistic() {
        return new ArrayOfStatistic();
    }

    /**
     * Create an instance of {@link Statistic }
     * 
     */
    public Statistic createStatistic() {
        return new Statistic();
    }

    /**
     * Create an instance of {@link ArrayOfAlarmHistory }
     * 
     */
    public ArrayOfAlarmHistory createArrayOfAlarmHistory() {
        return new ArrayOfAlarmHistory();
    }

    /**
     * Create an instance of {@link AlarmHistory }
     * 
     */
    public AlarmHistory createAlarmHistory() {
        return new AlarmHistory();
    }

    /**
     * Create an instance of {@link ArrayOfAlarmUpdate }
     * 
     */
    public ArrayOfAlarmUpdate createArrayOfAlarmUpdate() {
        return new ArrayOfAlarmUpdate();
    }

    /**
     * Create an instance of {@link AlarmUpdate }
     * 
     */
    public AlarmUpdate createAlarmUpdate() {
        return new AlarmUpdate();
    }

    /**
     * Create an instance of {@link GetEventLines2Params }
     * 
     */
    public GetEventLines2Params createGetEventLines2Params() {
        return new GetEventLines2Params();
    }

    /**
     * Create an instance of {@link SearchGroup }
     * 
     */
    public SearchGroup createSearchGroup() {
        return new SearchGroup();
    }

    /**
     * Create an instance of {@link ArrayOfSearchElement }
     * 
     */
    public ArrayOfSearchElement createArrayOfSearchElement() {
        return new ArrayOfSearchElement();
    }

    /**
     * Create an instance of {@link SearchElement }
     * 
     */
    public SearchElement createSearchElement() {
        return new SearchElement();
    }

    /**
     * Create an instance of {@link ArrayOfSearchGroup }
     * 
     */
    public ArrayOfSearchGroup createArrayOfSearchGroup() {
        return new ArrayOfSearchGroup();
    }

    /**
     * Create an instance of {@link SearchOrders }
     * 
     */
    public SearchOrders createSearchOrders() {
        return new SearchOrders();
    }

    /**
     * Create an instance of {@link ArrayOfSearchOrder }
     * 
     */
    public ArrayOfSearchOrder createArrayOfSearchOrder() {
        return new ArrayOfSearchOrder();
    }

    /**
     * Create an instance of {@link SearchOrder }
     * 
     */
    public SearchOrder createSearchOrder() {
        return new SearchOrder();
    }

    /**
     * Create an instance of {@link GetEventLines2Resp }
     * 
     */
    public GetEventLines2Resp createGetEventLines2Resp() {
        return new GetEventLines2Resp();
    }

    /**
     * Create an instance of {@link GetEventCountParams }
     * 
     */
    public GetEventCountParams createGetEventCountParams() {
        return new GetEventCountParams();
    }

    /**
     * Create an instance of {@link GetEventCountResp }
     * 
     */
    public GetEventCountResp createGetEventCountResp() {
        return new GetEventCountResp();
    }

    /**
     * Create an instance of {@link GetEventsParams }
     * 
     */
    public GetEventsParams createGetEventsParams() {
        return new GetEventsParams();
    }

    /**
     * Create an instance of {@link GetEventsResp }
     * 
     */
    public GetEventsResp createGetEventsResp() {
        return new GetEventsResp();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValuePairOfstringstring }
     * 
     */
    public ArrayOfKeyValuePairOfstringstring createArrayOfKeyValuePairOfstringstring() {
        return new ArrayOfKeyValuePairOfstringstring();
    }

    /**
     * Create an instance of {@link KeyValuePairOfstringstring }
     * 
     */
    public KeyValuePairOfstringstring createKeyValuePairOfstringstring() {
        return new KeyValuePairOfstringstring();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link AddEvent }
     * 
     */
    public AddEvent createAddEvent() {
        return new AddEvent();
    }

    /**
     * Create an instance of {@link AddEventResponse }
     * 
     */
    public AddEventResponse createAddEventResponse() {
        return new AddEventResponse();
    }

    /**
     * Create an instance of {@link AddAsEvent }
     * 
     */
    public AddAsEvent createAddAsEvent() {
        return new AddAsEvent();
    }

    /**
     * Create an instance of {@link AddAsEventResponse }
     * 
     */
    public AddAsEventResponse createAddAsEventResponse() {
        return new AddAsEventResponse();
    }

    /**
     * Create an instance of {@link Get }
     * 
     */
    public Get createGet() {
        return new Get();
    }

    /**
     * Create an instance of {@link GetResponse }
     * 
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link GetEvent }
     * 
     */
    public GetEvent createGetEvent() {
        return new GetEvent();
    }

    /**
     * Create an instance of {@link GetEventResponse }
     * 
     */
    public GetEventResponse createGetEventResponse() {
        return new GetEventResponse();
    }

    /**
     * Create an instance of {@link GetHeader }
     * 
     */
    public GetHeader createGetHeader() {
        return new GetHeader();
    }

    /**
     * Create an instance of {@link GetHeaderResponse }
     * 
     */
    public GetHeaderResponse createGetHeaderResponse() {
        return new GetHeaderResponse();
    }

    /**
     * Create an instance of {@link GetSnapshots }
     * 
     */
    public GetSnapshots createGetSnapshots() {
        return new GetSnapshots();
    }

    /**
     * Create an instance of {@link GetSnapshotsResponse }
     * 
     */
    public GetSnapshotsResponse createGetSnapshotsResponse() {
        return new GetSnapshotsResponse();
    }

    /**
     * Create an instance of {@link AttachSnapshot }
     * 
     */
    public AttachSnapshot createAttachSnapshot() {
        return new AttachSnapshot();
    }

    /**
     * Create an instance of {@link AttachSnapshotResponse }
     * 
     */
    public AttachSnapshotResponse createAttachSnapshotResponse() {
        return new AttachSnapshotResponse();
    }

    /**
     * Create an instance of {@link UpdateAlarmValues }
     * 
     */
    public UpdateAlarmValues createUpdateAlarmValues() {
        return new UpdateAlarmValues();
    }

    /**
     * Create an instance of {@link UpdateAlarmValuesResponse }
     * 
     */
    public UpdateAlarmValuesResponse createUpdateAlarmValuesResponse() {
        return new UpdateAlarmValuesResponse();
    }

    /**
     * Create an instance of {@link UpdateAlarm }
     * 
     */
    public UpdateAlarm createUpdateAlarm() {
        return new UpdateAlarm();
    }

    /**
     * Create an instance of {@link UpdateAlarmResponse }
     * 
     */
    public UpdateAlarmResponse createUpdateAlarmResponse() {
        return new UpdateAlarmResponse();
    }

    /**
     * Create an instance of {@link Acknowledge }
     * 
     */
    public Acknowledge createAcknowledge() {
        return new Acknowledge();
    }

    /**
     * Create an instance of {@link AcknowledgeResponse }
     * 
     */
    public AcknowledgeResponse createAcknowledgeResponse() {
        return new AcknowledgeResponse();
    }

    /**
     * Create an instance of {@link UpdateMultipleAlarms }
     * 
     */
    public UpdateMultipleAlarms createUpdateMultipleAlarms() {
        return new UpdateMultipleAlarms();
    }

    /**
     * Create an instance of {@link UpdateMultipleAlarmsResponse }
     * 
     */
    public UpdateMultipleAlarmsResponse createUpdateMultipleAlarmsResponse() {
        return new UpdateMultipleAlarmsResponse();
    }

    /**
     * Create an instance of {@link AcknowledgeMultipleAlarms }
     * 
     */
    public AcknowledgeMultipleAlarms createAcknowledgeMultipleAlarms() {
        return new AcknowledgeMultipleAlarms();
    }

    /**
     * Create an instance of {@link AcknowledgeMultipleAlarmsResponse }
     * 
     */
    public AcknowledgeMultipleAlarmsResponse createAcknowledgeMultipleAlarmsResponse() {
        return new AcknowledgeMultipleAlarmsResponse();
    }

    /**
     * Create an instance of {@link StartAlarmLineSession }
     * 
     */
    public StartAlarmLineSession createStartAlarmLineSession() {
        return new StartAlarmLineSession();
    }

    /**
     * Create an instance of {@link StartAlarmLineSessionResponse }
     * 
     */
    public StartAlarmLineSessionResponse createStartAlarmLineSessionResponse() {
        return new StartAlarmLineSessionResponse();
    }

    /**
     * Create an instance of {@link StopAlarmLineSession }
     * 
     */
    public StopAlarmLineSession createStopAlarmLineSession() {
        return new StopAlarmLineSession();
    }

    /**
     * Create an instance of {@link StopAlarmLineSessionResponse }
     * 
     */
    public StopAlarmLineSessionResponse createStopAlarmLineSessionResponse() {
        return new StopAlarmLineSessionResponse();
    }

    /**
     * Create an instance of {@link GetSessionAlarmLines }
     * 
     */
    public GetSessionAlarmLines createGetSessionAlarmLines() {
        return new GetSessionAlarmLines();
    }

    /**
     * Create an instance of {@link GetSessionAlarmLinesResponse }
     * 
     */
    public GetSessionAlarmLinesResponse createGetSessionAlarmLinesResponse() {
        return new GetSessionAlarmLinesResponse();
    }

    /**
     * Create an instance of {@link GetAlarmLines }
     * 
     */
    public GetAlarmLines createGetAlarmLines() {
        return new GetAlarmLines();
    }

    /**
     * Create an instance of {@link GetAlarmLinesResponse }
     * 
     */
    public GetAlarmLinesResponse createGetAlarmLinesResponse() {
        return new GetAlarmLinesResponse();
    }

    /**
     * Create an instance of {@link GetAlarmsWithRelatedHardware }
     * 
     */
    public GetAlarmsWithRelatedHardware createGetAlarmsWithRelatedHardware() {
        return new GetAlarmsWithRelatedHardware();
    }

    /**
     * Create an instance of {@link GetAlarmsWithRelatedHardwareResponse }
     * 
     */
    public GetAlarmsWithRelatedHardwareResponse createGetAlarmsWithRelatedHardwareResponse() {
        return new GetAlarmsWithRelatedHardwareResponse();
    }

    /**
     * Create an instance of {@link StartEventLineSession }
     * 
     */
    public StartEventLineSession createStartEventLineSession() {
        return new StartEventLineSession();
    }

    /**
     * Create an instance of {@link StartEventLineSessionResponse }
     * 
     */
    public StartEventLineSessionResponse createStartEventLineSessionResponse() {
        return new StartEventLineSessionResponse();
    }

    /**
     * Create an instance of {@link StopEventLineSession }
     * 
     */
    public StopEventLineSession createStopEventLineSession() {
        return new StopEventLineSession();
    }

    /**
     * Create an instance of {@link StopEventLineSessionResponse }
     * 
     */
    public StopEventLineSessionResponse createStopEventLineSessionResponse() {
        return new StopEventLineSessionResponse();
    }

    /**
     * Create an instance of {@link GetSessionEventLines }
     * 
     */
    public GetSessionEventLines createGetSessionEventLines() {
        return new GetSessionEventLines();
    }

    /**
     * Create an instance of {@link GetSessionEventLinesResponse }
     * 
     */
    public GetSessionEventLinesResponse createGetSessionEventLinesResponse() {
        return new GetSessionEventLinesResponse();
    }

    /**
     * Create an instance of {@link GetEventLines }
     * 
     */
    public GetEventLines createGetEventLines() {
        return new GetEventLines();
    }

    /**
     * Create an instance of {@link GetEventLinesResponse }
     * 
     */
    public GetEventLinesResponse createGetEventLinesResponse() {
        return new GetEventLinesResponse();
    }

    /**
     * Create an instance of {@link GetStatistics }
     * 
     */
    public GetStatistics createGetStatistics() {
        return new GetStatistics();
    }

    /**
     * Create an instance of {@link GetStatisticsResponse }
     * 
     */
    public GetStatisticsResponse createGetStatisticsResponse() {
        return new GetStatisticsResponse();
    }

    /**
     * Create an instance of {@link GetAlarmHistory }
     * 
     */
    public GetAlarmHistory createGetAlarmHistory() {
        return new GetAlarmHistory();
    }

    /**
     * Create an instance of {@link GetAlarmHistoryResponse }
     * 
     */
    public GetAlarmHistoryResponse createGetAlarmHistoryResponse() {
        return new GetAlarmHistoryResponse();
    }

    /**
     * Create an instance of {@link GetAlarmUpdateHistory }
     * 
     */
    public GetAlarmUpdateHistory createGetAlarmUpdateHistory() {
        return new GetAlarmUpdateHistory();
    }

    /**
     * Create an instance of {@link GetAlarmUpdateHistoryResponse }
     * 
     */
    public GetAlarmUpdateHistoryResponse createGetAlarmUpdateHistoryResponse() {
        return new GetAlarmUpdateHistoryResponse();
    }

    /**
     * Create an instance of {@link GetAlarmMessages }
     * 
     */
    public GetAlarmMessages createGetAlarmMessages() {
        return new GetAlarmMessages();
    }

    /**
     * Create an instance of {@link GetAlarmMessagesResponse }
     * 
     */
    public GetAlarmMessagesResponse createGetAlarmMessagesResponse() {
        return new GetAlarmMessagesResponse();
    }

    /**
     * Create an instance of {@link GetEventLines2 }
     * 
     */
    public GetEventLines2 createGetEventLines2() {
        return new GetEventLines2();
    }

    /**
     * Create an instance of {@link GetEventLines2Response }
     * 
     */
    public GetEventLines2Response createGetEventLines2Response() {
        return new GetEventLines2Response();
    }

    /**
     * Create an instance of {@link GetEventCount }
     * 
     */
    public GetEventCount createGetEventCount() {
        return new GetEventCount();
    }

    /**
     * Create an instance of {@link GetEventCountResponse }
     * 
     */
    public GetEventCountResponse createGetEventCountResponse() {
        return new GetEventCountResponse();
    }

    /**
     * Create an instance of {@link GetEvents }
     * 
     */
    public GetEvents createGetEvents() {
        return new GetEvents();
    }

    /**
     * Create an instance of {@link GetEventsResponse }
     * 
     */
    public GetEventsResponse createGetEventsResponse() {
        return new GetEventsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfbase64Binary }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfbase64Binary }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfbase64Binary")
    public JAXBElement<ArrayOfbase64Binary> createArrayOfbase64Binary(ArrayOfbase64Binary value) {
        return new JAXBElement<ArrayOfbase64Binary>(_ArrayOfbase64Binary_QNAME, ArrayOfbase64Binary.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfanyType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfanyType }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfanyType")
    public JAXBElement<ArrayOfanyType> createArrayOfanyType(ArrayOfanyType value) {
        return new JAXBElement<ArrayOfanyType>(_ArrayOfanyType_QNAME, ArrayOfanyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Alarm }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Alarm }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "Alarm")
    public JAXBElement<Alarm> createAlarm(Alarm value) {
        return new JAXBElement<Alarm>(_Alarm_QNAME, Alarm.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventHeader }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventHeader }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "EventHeader")
    public JAXBElement<EventHeader> createEventHeader(EventHeader value) {
        return new JAXBElement<EventHeader>(_EventHeader_QNAME, EventHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventSource }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventSource }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "EventSource")
    public JAXBElement<EventSource> createEventSource(EventSource value) {
        return new JAXBElement<EventSource>(_EventSource_QNAME, EventSource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FQID }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FQID }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "FQID")
    public JAXBElement<FQID> createFQID(FQID value) {
        return new JAXBElement<FQID>(_FQID_QNAME, FQID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerId }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServerId }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "ServerId")
    public JAXBElement<ServerId> createServerId(ServerId value) {
        return new JAXBElement<ServerId>(_ServerId_QNAME, ServerId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "FolderType")
    public JAXBElement<String> createFolderType(String value) {
        return new JAXBElement<String>(_FolderType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuleList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RuleList }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "RuleList")
    public JAXBElement<RuleList> createRuleList(RuleList value) {
        return new JAXBElement<RuleList>(_RuleList_QNAME, RuleList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Rule }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Rule }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "Rule")
    public JAXBElement<Rule> createRule(Rule value) {
        return new JAXBElement<Rule>(_Rule_QNAME, Rule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PolygonList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PolygonList }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "PolygonList")
    public JAXBElement<PolygonList> createPolygonList(PolygonList value) {
        return new JAXBElement<PolygonList>(_PolygonList_QNAME, PolygonList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TPolygon }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TPolygon }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "TPolygon")
    public JAXBElement<TPolygon> createTPolygon(TPolygon value) {
        return new JAXBElement<TPolygon>(_TPolygon_QNAME, TPolygon.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TColor }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TColor }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "TColor")
    public JAXBElement<TColor> createTColor(TColor value) {
        return new JAXBElement<TColor>(_TColor_QNAME, TColor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PointList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PointList }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "PointList")
    public JAXBElement<PointList> createPointList(PointList value) {
        return new JAXBElement<PointList>(_PointList_QNAME, PointList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TPoint }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TPoint }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "TPoint")
    public JAXBElement<TPoint> createTPoint(TPoint value) {
        return new JAXBElement<TPoint>(_TPoint_QNAME, TPoint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnalyticsObjectList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AnalyticsObjectList }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "AnalyticsObjectList")
    public JAXBElement<AnalyticsObjectList> createAnalyticsObjectList(AnalyticsObjectList value) {
        return new JAXBElement<AnalyticsObjectList>(_AnalyticsObjectList_QNAME, AnalyticsObjectList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnalyticsObject }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AnalyticsObject }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "AnalyticsObject")
    public JAXBElement<AnalyticsObject> createAnalyticsObject(AnalyticsObject value) {
        return new JAXBElement<AnalyticsObject>(_AnalyticsObject_QNAME, AnalyticsObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BoundingBox }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BoundingBox }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "BoundingBox")
    public JAXBElement<BoundingBox> createBoundingBox(BoundingBox value) {
        return new JAXBElement<BoundingBox>(_BoundingBox_QNAME, BoundingBox.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectMotion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObjectMotion }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "ObjectMotion")
    public JAXBElement<ObjectMotion> createObjectMotion(ObjectMotion value) {
        return new JAXBElement<ObjectMotion>(_ObjectMotion_QNAME, ObjectMotion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReferenceList }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "ReferenceList")
    public JAXBElement<ReferenceList> createReferenceList(ReferenceList value) {
        return new JAXBElement<ReferenceList>(_ReferenceList_QNAME, ReferenceList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reference }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Reference }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "Reference")
    public JAXBElement<Reference> createReference(Reference value) {
        return new JAXBElement<Reference>(_Reference_QNAME, Reference.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SnapshotList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SnapshotList }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "SnapshotList")
    public JAXBElement<SnapshotList> createSnapshotList(SnapshotList value) {
        return new JAXBElement<SnapshotList>(_SnapshotList_QNAME, SnapshotList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Snapshot }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Snapshot }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "Snapshot")
    public JAXBElement<Snapshot> createSnapshot(Snapshot value) {
        return new JAXBElement<Snapshot>(_Snapshot_QNAME, Snapshot.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Vendor }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Vendor }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "Vendor")
    public JAXBElement<Vendor> createVendor(Vendor value) {
        return new JAXBElement<Vendor>(_Vendor_QNAME, Vendor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseEvent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BaseEvent }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "BaseEvent")
    public JAXBElement<BaseEvent> createBaseEvent(BaseEvent value) {
        return new JAXBElement<BaseEvent>(_BaseEvent_QNAME, BaseEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnalyticsEvent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AnalyticsEvent }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "AnalyticsEvent")
    public JAXBElement<AnalyticsEvent> createAnalyticsEvent(AnalyticsEvent value) {
        return new JAXBElement<AnalyticsEvent>(_AnalyticsEvent_QNAME, AnalyticsEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventData }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "EventData")
    public JAXBElement<EventData> createEventData(EventData value) {
        return new JAXBElement<EventData>(_EventData_QNAME, EventData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventSequence }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventSequence }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "EventSequence")
    public JAXBElement<EventSequence> createEventSequence(EventSequence value) {
        return new JAXBElement<EventSequence>(_EventSequence_QNAME, EventSequence.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccessControlEvent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AccessControlEvent }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "AccessControlEvent")
    public JAXBElement<AccessControlEvent> createAccessControlEvent(AccessControlEvent value) {
        return new JAXBElement<AccessControlEvent>(_AccessControlEvent_QNAME, AccessControlEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfFQID }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfFQID }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "ArrayOfFQID")
    public JAXBElement<ArrayOfFQID> createArrayOfFQID(ArrayOfFQID value) {
        return new JAXBElement<ArrayOfFQID>(_ArrayOfFQID_QNAME, ArrayOfFQID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProperty }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProperty }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "ArrayOfProperty")
    public JAXBElement<ArrayOfProperty> createArrayOfProperty(ArrayOfProperty value) {
        return new JAXBElement<ArrayOfProperty>(_ArrayOfProperty_QNAME, ArrayOfProperty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Property }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Property }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "Property")
    public JAXBElement<Property> createProperty(Property value) {
        return new JAXBElement<Property>(_Property_QNAME, Property.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBaseEvent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfBaseEvent }{@code >}
     */
    @XmlElementDecl(namespace = "urn:milestone-systems", name = "ArrayOfBaseEvent")
    public JAXBElement<ArrayOfBaseEvent> createArrayOfBaseEvent(ArrayOfBaseEvent value) {
        return new JAXBElement<ArrayOfBaseEvent>(_ArrayOfBaseEvent_QNAME, ArrayOfBaseEvent.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmServiceFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmServiceFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmServiceFault")
    public JAXBElement<AlarmServiceFault> createAlarmServiceFault(AlarmServiceFault value) {
        return new JAXBElement<AlarmServiceFault>(_AlarmServiceFault_QNAME, AlarmServiceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmFaultType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmFaultType }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmFaultType")
    public JAXBElement<AlarmFaultType> createAlarmFaultType(AlarmFaultType value) {
        return new JAXBElement<AlarmFaultType>(_AlarmFaultType_QNAME, AlarmFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmFilter")
    public JAXBElement<AlarmFilter> createAlarmFilter(AlarmFilter value) {
        return new JAXBElement<AlarmFilter>(_AlarmFilter_QNAME, AlarmFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfCondition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfCondition }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfCondition")
    public JAXBElement<ArrayOfCondition> createArrayOfCondition(ArrayOfCondition value) {
        return new JAXBElement<ArrayOfCondition>(_ArrayOfCondition_QNAME, ArrayOfCondition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Condition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Condition }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "Condition")
    public JAXBElement<Condition> createCondition(Condition value) {
        return new JAXBElement<Condition>(_Condition_QNAME, Condition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Operator }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Operator }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "Operator")
    public JAXBElement<Operator> createOperator(Operator value) {
        return new JAXBElement<Operator>(_Operator_QNAME, Operator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Target }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Target }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "Target")
    public JAXBElement<Target> createTarget(Target value) {
        return new JAXBElement<Target>(_Target_QNAME, Target.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOrderBy }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfOrderBy }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfOrderBy")
    public JAXBElement<ArrayOfOrderBy> createArrayOfOrderBy(ArrayOfOrderBy value) {
        return new JAXBElement<ArrayOfOrderBy>(_ArrayOfOrderBy_QNAME, ArrayOfOrderBy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderBy }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderBy }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "OrderBy")
    public JAXBElement<OrderBy> createOrderBy(OrderBy value) {
        return new JAXBElement<OrderBy>(_OrderBy_QNAME, OrderBy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Order }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Order }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "Order")
    public JAXBElement<Order> createOrder(Order value) {
        return new JAXBElement<Order>(_Order_QNAME, Order.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmUpdateData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmUpdateData }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmUpdateData")
    public JAXBElement<AlarmUpdateData> createAlarmUpdateData(AlarmUpdateData value) {
        return new JAXBElement<AlarmUpdateData>(_AlarmUpdateData_QNAME, AlarmUpdateData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmLine }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmLine }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfAlarmLine")
    public JAXBElement<ArrayOfAlarmLine> createArrayOfAlarmLine(ArrayOfAlarmLine value) {
        return new JAXBElement<ArrayOfAlarmLine>(_ArrayOfAlarmLine_QNAME, ArrayOfAlarmLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmLine }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmLine }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmLine")
    public JAXBElement<AlarmLine> createAlarmLine(AlarmLine value) {
        return new JAXBElement<AlarmLine>(_AlarmLine_QNAME, AlarmLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmSearchCriteria }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmSearchCriteria }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfAlarmSearchCriteria")
    public JAXBElement<ArrayOfAlarmSearchCriteria> createArrayOfAlarmSearchCriteria(ArrayOfAlarmSearchCriteria value) {
        return new JAXBElement<ArrayOfAlarmSearchCriteria>(_ArrayOfAlarmSearchCriteria_QNAME, ArrayOfAlarmSearchCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmSearchCriteria }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmSearchCriteria }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmSearchCriteria")
    public JAXBElement<AlarmSearchCriteria> createAlarmSearchCriteria(AlarmSearchCriteria value) {
        return new JAXBElement<AlarmSearchCriteria>(_AlarmSearchCriteria_QNAME, AlarmSearchCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameNumberPairValue }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NameNumberPairValue }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "NameNumberPairValue")
    public JAXBElement<NameNumberPairValue> createNameNumberPairValue(NameNumberPairValue value) {
        return new JAXBElement<NameNumberPairValue>(_NameNumberPairValue_QNAME, NameNumberPairValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "EventFilter")
    public JAXBElement<EventFilter> createEventFilter(EventFilter value) {
        return new JAXBElement<EventFilter>(_EventFilter_QNAME, EventFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventUpdateData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventUpdateData }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "EventUpdateData")
    public JAXBElement<EventUpdateData> createEventUpdateData(EventUpdateData value) {
        return new JAXBElement<EventUpdateData>(_EventUpdateData_QNAME, EventUpdateData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEventLine }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEventLine }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfEventLine")
    public JAXBElement<ArrayOfEventLine> createArrayOfEventLine(ArrayOfEventLine value) {
        return new JAXBElement<ArrayOfEventLine>(_ArrayOfEventLine_QNAME, ArrayOfEventLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventLine }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventLine }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "EventLine")
    public JAXBElement<EventLine> createEventLine(EventLine value) {
        return new JAXBElement<EventLine>(_EventLine_QNAME, EventLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfStatistic }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfStatistic }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfStatistic")
    public JAXBElement<ArrayOfStatistic> createArrayOfStatistic(ArrayOfStatistic value) {
        return new JAXBElement<ArrayOfStatistic>(_ArrayOfStatistic_QNAME, ArrayOfStatistic.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Statistic }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Statistic }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "Statistic")
    public JAXBElement<Statistic> createStatistic(Statistic value) {
        return new JAXBElement<Statistic>(_Statistic_QNAME, Statistic.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatisticType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StatisticType }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "StatisticType")
    public JAXBElement<StatisticType> createStatisticType(StatisticType value) {
        return new JAXBElement<StatisticType>(_StatisticType_QNAME, StatisticType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmHistory }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmHistory }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfAlarmHistory")
    public JAXBElement<ArrayOfAlarmHistory> createArrayOfAlarmHistory(ArrayOfAlarmHistory value) {
        return new JAXBElement<ArrayOfAlarmHistory>(_ArrayOfAlarmHistory_QNAME, ArrayOfAlarmHistory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmHistory }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmHistory }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmHistory")
    public JAXBElement<AlarmHistory> createAlarmHistory(AlarmHistory value) {
        return new JAXBElement<AlarmHistory>(_AlarmHistory_QNAME, AlarmHistory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmUpdate }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfAlarmUpdate }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfAlarmUpdate")
    public JAXBElement<ArrayOfAlarmUpdate> createArrayOfAlarmUpdate(ArrayOfAlarmUpdate value) {
        return new JAXBElement<ArrayOfAlarmUpdate>(_ArrayOfAlarmUpdate_QNAME, ArrayOfAlarmUpdate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlarmUpdate }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlarmUpdate }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "AlarmUpdate")
    public JAXBElement<AlarmUpdate> createAlarmUpdate(AlarmUpdate value) {
        return new JAXBElement<AlarmUpdate>(_AlarmUpdate_QNAME, AlarmUpdate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEventLines2Params }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetEventLines2Params }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "GetEventLines2Params")
    public JAXBElement<GetEventLines2Params> createGetEventLines2Params(GetEventLines2Params value) {
        return new JAXBElement<GetEventLines2Params>(_GetEventLines2Params_QNAME, GetEventLines2Params.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchGroup }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchGroup }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "SearchGroup")
    public JAXBElement<SearchGroup> createSearchGroup(SearchGroup value) {
        return new JAXBElement<SearchGroup>(_SearchGroup_QNAME, SearchGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogicalOperator }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LogicalOperator }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "LogicalOperator")
    public JAXBElement<LogicalOperator> createLogicalOperator(LogicalOperator value) {
        return new JAXBElement<LogicalOperator>(_LogicalOperator_QNAME, LogicalOperator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchElement }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchElement }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfSearchElement")
    public JAXBElement<ArrayOfSearchElement> createArrayOfSearchElement(ArrayOfSearchElement value) {
        return new JAXBElement<ArrayOfSearchElement>(_ArrayOfSearchElement_QNAME, ArrayOfSearchElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchElement }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchElement }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "SearchElement")
    public JAXBElement<SearchElement> createSearchElement(SearchElement value) {
        return new JAXBElement<SearchElement>(_SearchElement_QNAME, SearchElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchOperator }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchOperator }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "SearchOperator")
    public JAXBElement<SearchOperator> createSearchOperator(SearchOperator value) {
        return new JAXBElement<SearchOperator>(_SearchOperator_QNAME, SearchOperator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchGroup }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchGroup }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfSearchGroup")
    public JAXBElement<ArrayOfSearchGroup> createArrayOfSearchGroup(ArrayOfSearchGroup value) {
        return new JAXBElement<ArrayOfSearchGroup>(_ArrayOfSearchGroup_QNAME, ArrayOfSearchGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchOrders }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchOrders }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "SearchOrders")
    public JAXBElement<SearchOrders> createSearchOrders(SearchOrders value) {
        return new JAXBElement<SearchOrders>(_SearchOrders_QNAME, SearchOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfSearchOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "ArrayOfSearchOrder")
    public JAXBElement<ArrayOfSearchOrder> createArrayOfSearchOrder(ArrayOfSearchOrder value) {
        return new JAXBElement<ArrayOfSearchOrder>(_ArrayOfSearchOrder_QNAME, ArrayOfSearchOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "SearchOrder")
    public JAXBElement<SearchOrder> createSearchOrder(SearchOrder value) {
        return new JAXBElement<SearchOrder>(_SearchOrder_QNAME, SearchOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Direction }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Direction }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "Direction")
    public JAXBElement<Direction> createDirection(Direction value) {
        return new JAXBElement<Direction>(_Direction_QNAME, Direction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEventLines2Resp }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetEventLines2Resp }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "GetEventLines2Resp")
    public JAXBElement<GetEventLines2Resp> createGetEventLines2Resp(GetEventLines2Resp value) {
        return new JAXBElement<GetEventLines2Resp>(_GetEventLines2Resp_QNAME, GetEventLines2Resp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEventCountParams }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetEventCountParams }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "GetEventCountParams")
    public JAXBElement<GetEventCountParams> createGetEventCountParams(GetEventCountParams value) {
        return new JAXBElement<GetEventCountParams>(_GetEventCountParams_QNAME, GetEventCountParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEventCountResp }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetEventCountResp }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "GetEventCountResp")
    public JAXBElement<GetEventCountResp> createGetEventCountResp(GetEventCountResp value) {
        return new JAXBElement<GetEventCountResp>(_GetEventCountResp_QNAME, GetEventCountResp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEventsParams }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetEventsParams }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "GetEventsParams")
    public JAXBElement<GetEventsParams> createGetEventsParams(GetEventsParams value) {
        return new JAXBElement<GetEventsParams>(_GetEventsParams_QNAME, GetEventsParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEventsResp }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetEventsResp }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", name = "GetEventsResp")
    public JAXBElement<GetEventsResp> createGetEventsResp(GetEventsResp value) {
        return new JAXBElement<GetEventsResp>(_GetEventsResp_QNAME, GetEventsResp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValuePairOfstringstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValuePairOfstringstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System.Collections.Generic", name = "ArrayOfKeyValuePairOfstringstring")
    public JAXBElement<ArrayOfKeyValuePairOfstringstring> createArrayOfKeyValuePairOfstringstring(ArrayOfKeyValuePairOfstringstring value) {
        return new JAXBElement<ArrayOfKeyValuePairOfstringstring>(_ArrayOfKeyValuePairOfstringstring_QNAME, ArrayOfKeyValuePairOfstringstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyValuePairOfstringstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link KeyValuePairOfstringstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System.Collections.Generic", name = "KeyValuePairOfstringstring")
    public JAXBElement<KeyValuePairOfstringstring> createKeyValuePairOfstringstring(KeyValuePairOfstringstring value) {
        return new JAXBElement<KeyValuePairOfstringstring>(_KeyValuePairOfstringstring_QNAME, KeyValuePairOfstringstring.class, null, value);
    }

}
